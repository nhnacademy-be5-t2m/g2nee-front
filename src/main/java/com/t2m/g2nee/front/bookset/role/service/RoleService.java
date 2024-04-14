package com.t2m.g2nee.front.bookset.role.service;

import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.bookset.tag.dto.TagDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    public List<RoleDto.Response> getAllRole(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "shop/roles/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<RoleDto.Response>>() {
                }
        ).getBody();
    }
}