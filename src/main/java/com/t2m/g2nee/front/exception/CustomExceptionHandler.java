package com.t2m.g2nee.front.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

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
        HttpStatus status = response.getStatusCode();
        JsonNode body = objectMapper.readTree(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
        if(Objects.isNull(body.get("message"))){
            throw new CustomException(status, body.get("error").asText());
        }

        throw new CustomException(status, body.get("message").asText());
    }
}
/**
 * {"code":404,"message":"카테고리가 존재하지 않습니다."}
 */