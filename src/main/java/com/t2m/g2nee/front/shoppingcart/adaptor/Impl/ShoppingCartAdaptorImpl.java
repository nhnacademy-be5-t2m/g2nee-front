package com.t2m.g2nee.front.shoppingcart.adaptor.Impl;

import com.t2m.g2nee.front.bookset.book.dto.BookDto;
import com.t2m.g2nee.front.shoppingcart.adaptor.ShoppingCartAdaptor;
import com.t2m.g2nee.front.shoppingcart.dto.ShoppingCartDto;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class ShoppingCartAdaptorImpl implements ShoppingCartAdaptor {

    private final RestTemplate restTemplate;

    @Value("${g2nee.gateway}")
    private String gatewayUrl;

    @Override
    public ShoppingCartDto.Response addBookInCart(ShoppingCartDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ShoppingCartDto.Request> requestEntity = new HttpEntity<>(request,headers);

        String url = gatewayUrl + "/carts";

        return restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<ShoppingCartDto.Response>() {
                }
        ).getBody();


    }

    @Override
    public List<ShoppingCartDto.Response> getCartByMember(String customerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/carts/member/" + customerId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<ShoppingCartDto.Response>>() {
                }
        ).getBody();
    }

    @Override
    public ShoppingCartDto.Response getBookForCart(String bookId, int quantity) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/carts/book/" +  bookId + "?quantity=" + quantity;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<ShoppingCartDto.Response>() {
                }
        ).getBody();
    }

}
