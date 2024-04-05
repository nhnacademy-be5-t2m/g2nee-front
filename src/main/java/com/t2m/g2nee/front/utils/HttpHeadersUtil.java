package com.t2m.g2nee.front.utils;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpHeadersUtil {


    public static HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}
