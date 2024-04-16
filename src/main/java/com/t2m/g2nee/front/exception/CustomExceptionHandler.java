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

/**
 * 예외처리를 위한 핸들러
 *
 * @author : 김수빈
 * @since : 1.0
 */
public class CustomExceptionHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public CustomExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        //백엔드에서 오는 400에러와 500에러를 받음
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        //에러 코드를 받음
        HttpStatus status = response.getStatusCode();

        //message를 받음
        JsonNode body = objectMapper.readTree(StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
        if(Objects.isNull(body.get("message"))){
            //error의 경우, 서버에서 보낸, CustomException이 아닌 에러들
            throw new CustomException(status, body.get("error").asText());
        }

        //설정한 custom error형식으로 그대로 객체를 생성하여 던짐
        throw new CustomException(status, body.get("message").asText());
    }
}