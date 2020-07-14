package com.zahangirbd.crossrefdoi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class DoiMetaGetService {

    private static final Logger logger = LoggerFactory.getLogger(DoiMetaGetService.class);
    
    private final RestTemplate restTemplate;
    private DoiMetaXmlParser xmlParser = new DoiMetaXmlParser();

    public DoiMetaGetService() {
    	RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.restTemplate = restTemplateBuilder.build();
        this.restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

    @Async
    public CompletableFuture<DoiItem> findJournalDoiMeta(String doiNumber) throws InterruptedException {
        logger.info("findJournalDoiMeta(doiNumber): doiNumber =  " + doiNumber);
        String url = String.format("https://api.crossref.org/v1/works/%s/transform/application/vnd.crossref.unixsd+xml", doiNumber);
        logger.info("findJournalDoiMeta(doiNumber): finalUrl =  " + url);
        String doiXmlMeta = restTemplate.getForObject(url, String.class);    
        DoiItem doiItem = xmlParser.prepareDoiItemFromXmlStr(doiXmlMeta);
        return CompletableFuture.completedFuture(doiItem);
    }
}
