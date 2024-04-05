package com.t2m.g2nee.front.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class CustomExceptionHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public CustomExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);


        throw new CustomException(response.getStatusCode(), objectMapper.readTree(body).get("message").asText());
    }
}
/**
 * {"code":404,"message":"카테고리가 존재하지 않습니다."}
 */