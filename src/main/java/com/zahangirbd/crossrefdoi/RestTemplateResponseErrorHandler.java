package com.zahangirbd.crossrefdoi;

import java.io.IOException;

import com.zahangirbd.server.excep.DoiItemNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorHandler
    implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
    	System.out.println("hasError(): httpResponse = " + httpResponse);
        return (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
    	System.out.println("handleError(): httpResponse = " + httpResponse);
        if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
            //Handle SERVER_ERROR
            throw new HttpClientErrorException(httpResponse.getStatusCode());
        } else if (httpResponse
          .getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
            //Handle CLIENT_ERROR
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new DoiItemNotFoundException("DOI Item Not Found in Crossref");
            }
        }
    }
}