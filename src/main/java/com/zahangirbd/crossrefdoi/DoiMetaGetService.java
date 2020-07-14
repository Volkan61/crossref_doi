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

    public DoiMetaGetService() {
    	RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<String> findUser(String doiNumber) throws InterruptedException {
        logger.info("Looking up " + doiNumber);
        String url = "https://api.crossref.org/v1/works/10.5555/12345678/transform/application/vnd.crossref.unixsd+xml";
        String results = restTemplate.getForObject(url, String.class);
        return CompletableFuture.completedFuture(results);
    }

}