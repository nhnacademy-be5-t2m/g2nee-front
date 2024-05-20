package com.t2m.g2nee.front.shoppingcart.adaptor.Impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.shoppingcart.adaptor.ShoppingCartAdaptor;
import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class ShoppingCartAdaptorImpl implements ShoppingCartAdaptor {

    private final RestTemplate restTemplate;

    @Value("${g2nee.gateway}")
    private String gatewayUrl;


    @Override
    public ShoppingCartDto.Response getBookForCart(String bookId, int quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(makeHttpHeaders());

        String url = gatewayUrl + "/carts/book/" + bookId + "?quantity=" + quantity;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<ShoppingCartDto.Response>() {
                }
        ).getBody();
    }

    @Override
    public void migrateCartRedisToDB(String memberId, List<ShoppingCartDto.Request> requestList) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ShoppingCartDto.Request>> requestEntity = new HttpEntity<>(requestList, headers);

        String url = gatewayUrl + "/carts/migrate/member/" + memberId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );
    }
}
