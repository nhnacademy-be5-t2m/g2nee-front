package com.t2m.g2nee.front.ErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t2m.g2nee.front.ErrorHandler.response.ErrorResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    private final ObjectMapper mapper;

    public RestTemplateResponseErrorHandler() {
        mapper = new ObjectMapper();
    }


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status = response.getStatusCode();

        if (status.equals(HttpStatus.CONFLICT)) {
            InputStream body = response.getBody();
            String jsonBody = StreamUtils.copyToString(body, StandardCharsets.UTF_8);
            ErrorResponse error = mapper.readValue(jsonBody, ErrorResponse.class);
        }
    }
}