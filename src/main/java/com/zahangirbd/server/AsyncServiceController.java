package com.zahangirbd.server;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zahangirbd.crossrefdoi.DoiMetaGetService;

@RestController
public class AsyncServiceController {
	private static final Logger logger = LoggerFactory.getLogger(AsyncServiceController.class);
	   
    
    @Autowired
    private DoiMetaGetService doiMetaGetService;
    
    @RequestMapping("/asyncService")
    public String greeting() {
    	
    	String rslt = "";
		try {
			rslt = doExecute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return rslt;
    }
    
    public String doExecute() throws Exception {
        // Start the clock
        long start = System.currentTimeMillis();

        // Kick of multiple, asynchronous lookups
        CompletableFuture<String> doiXml = doiMetaGetService.findUser("10.5555/12345678");
  
        // Wait until they are all done
        CompletableFuture.allOf(doiXml).join();

        // Print results, including elapsed time
        logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
        String rslt = doiXml.get();
        logger.info("--> " + rslt);
        
        return rslt;
    }
    
}
