package com.t2m.g2nee.front.bookset.role.adaptor.impl;

import com.t2m.g2nee.front.bookset.role.adaptor.RoleAdaptor;
import com.t2m.g2nee.front.bookset.role.dto.RoleDto;
import com.t2m.g2nee.front.utils.PageResponse;
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


/**
 * 역할 adaptor 구현 클래스
 *
 * @author : 신동민
 * @since : 1.0
 */
@RequiredArgsConstructor
@Component
public class RoleAdaptorImpl implements RoleAdaptor {

    private final RestTemplate restTemplate;
    @Value("${g2nee.gateway}")
    String gatewayUrl;

    @Override
    public List<RoleDto.Response> getAllRole() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/roles/list";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<List<RoleDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 역할 등록 메서드
     *
     * @param request 등록할 정보가 있는 객체
     */
    @Override
    public void registerRole(RoleDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RoleDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/roles";

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );


    }

    /**
     * 역할 조회 메서드
     *
     * @param page 페이지 번호
     * @return PageResponse<RoleDto.Response>
     */
    @Override
    public PageResponse<RoleDto.Response> getAllRole(int page) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/roles?page=" + page;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<PageResponse<RoleDto.Response>>() {
                }
        ).getBody();
    }

    /**
     * 역할 수정 메서드
     *
     * @param roleId  역할 아이디
     * @param request 수정할 정보가 담긴 객체
     */
    @Override
    public void updateRole(Long roleId, RoleDto.Request request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RoleDto.Request> requestEntity = new HttpEntity<>(request, headers);

        String url = gatewayUrl + "/shop/roles/" + roleId;

        restTemplate.exchange(
                url,
                HttpMethod.PATCH,
                requestEntity,
                String.class
        );

    }

    /**
     * 역할 삭제 메서드
     *
     * @param roleId 역할 아이디
     */
    @Override
    public void deleteRole(Long roleId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        String url = gatewayUrl + "/shop/roles/" + roleId;

        restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                String.class
        );

    }
}
