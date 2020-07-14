package com.zahangirbd.server;


import java.util.concurrent.Executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.zahangirbd.crossrefdoi.DoiMetaGetService;

@SpringBootApplication
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("CrossrefDOI-");
        executor.initialize();
        return executor;
    }
   
    @Bean
    public DoiMetaGetService createDoiMetaGetService() {
    	DoiMetaGetService doiMetaGetService = new DoiMetaGetService();
    	return doiMetaGetService;
    }
   
}