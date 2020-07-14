package com.zahangirbd.server;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zahangirbd.crossrefdoi.DoiItem;
import com.zahangirbd.crossrefdoi.DoiMetaGetService;
import com.zahangirbd.server.excep.DoiItemInputErrorException;
import com.zahangirbd.server.excep.DoiItemNotFoundException;
import com.zahangirbd.server.excep.InternalErrorException;
import com.zahangirbd.util.RegexUtil;

@RestController
public class AsyncServiceController {
	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceController.class);
	   
    
    @Autowired
    private DoiMetaGetService doiMetaGetService;
    
    @RequestMapping(path="/works/{prefix}/{postfix}")
    public DoiItem getDoiItem(@PathVariable("prefix") String prefix,  @PathVariable("postfix") String postfix) {
    	System.out.println("getDoiItem(prefix,postfix): DOI ID - prefix=" + prefix + ";postfix=" + postfix);
    	
    	//preparing id from the params
    	String doiItemId = (prefix==null? "" : prefix.trim()) + "/" + (postfix==null? "" : postfix.trim());
    	boolean isValid = RegexUtil.isValidDoiId(doiItemId);
    	System.out.println("getDoiItem(prefix,postfix): doiItemId=" + doiItemId + ",isValid=" + isValid);	
    	
    	//if invalid we need to return as an error
    	if(!isValid) throw new DoiItemInputErrorException("Invalid DOI Id");
    	
    	DoiItem rslt = null;
		try {
			rslt = doExecute(doiItemId);
		} catch (DoiItemNotFoundException e) {
			logger.info("getDoiItem(prefix,postfix): DoiItemNotFoundException=", e);
			throw e;		
		} catch (Exception e) {
			logger.info("getDoiItem(prefix,postfix): Exception=", e);
			throw new InternalErrorException("Internal procesing error - " + e.getMessage());
		}
        return rslt;
    }
    
    public DoiItem doExecute(String doiItemId) throws Exception {
    	logger.info("doExecute(doiItemId): posting job.");
        // Start the clock
        long start = System.currentTimeMillis();

        try {
	        // Kick of multiple, asynchronous lookups
	        CompletableFuture<DoiItem> doiItemReq = doiMetaGetService.findJournalDoiMeta(doiItemId);
	        // Wait until they are all done
	        CompletableFuture.allOf(doiItemReq).join();
	        // Print results, including elapsed time
	        logger.info("doExecute(doiItemId): Elapsed time: " + (System.currentTimeMillis() - start));
	        return doiItemReq.get();
        } catch (CompletionException e) {
        	logger.info("doExecute(doiItemId): CompletionException =  ", e);
        	if(e.getCause() instanceof DoiItemNotFoundException) {
        		throw (DoiItemNotFoundException)e.getCause();
        	} else {
        		throw e;
        	}
        }
    }    
}
