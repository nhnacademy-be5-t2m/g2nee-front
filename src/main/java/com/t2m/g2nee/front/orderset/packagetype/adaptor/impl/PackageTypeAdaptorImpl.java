package com.t2m.g2nee.front.orderset.packagetype.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeFileHttpHeaders;
import static com.t2m.g2nee.front.utils.HttpHeadersUtil.makeHttpHeaders;

import com.t2m.g2nee.front.exception.CustomException;
import com.t2m.g2nee.front.orderset.packagetype.adaptor.PackageTypeAdaptor;
import com.t2m.g2nee.front.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PackageTypeAdaptorImpl implements PackageTypeAdaptor {

    //페이징 처리를 위한 ref
    private static final ParameterizedTypeReference<PageResponse<PackageInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;
    @Value("${gatewayToShopUrl}")
    private String gateway;
    private String baseUrl;

    public PackageTypeAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl() {
        baseUrl = gateway + "/packages";
    }

    @Override
    public PackageInfoDto requestCreatePackage(MultipartFile image, PackageSaveDto request) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", image.getResource());
        body.add("request", request);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, makeFileHttpHeaders());
        ResponseEntity<PackageInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, PackageInfoDto.class);

        return response.getBody();
    }

    @Override
    public PackageInfoDto requestUpdatePackage(Long packageId, MultipartFile image, PackageSaveDto request) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + packageId)
                .build();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", image.getResource());
        body.add("request", request);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, makeFileHttpHeaders());
        ResponseEntity<PackageInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PUT, entity, PackageInfoDto.class);

        return response.getBody();
    }

    @Override
    public PackageInfoDto getPackage(Long packageId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/" + packageId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PackageInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PackageInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<PackageInfoDto> getAllPackage(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<PackageInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public boolean requestDeletePackage(Long packageId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/delete")
                .path("/" + packageId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PATCH, entity, Boolean.class);

        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "삭제할 포장지를 찾을 수 없습니다."));
    }

    @Override
    public boolean requestActivatePackage(Long packageId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/activate")
                .path("/" + packageId)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PATCH, entity, Boolean.class);

        return Optional.ofNullable(response.getBody())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "활성화할 포장지를 찾을 수 없습니다."));
    }

    @Override
    public PageResponse<PackageInfoDto> getActivatePackage(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/activated")
                .queryParam("page", page)
                .build();

        HttpEntity<String> entity = new HttpEntity<>(makeHttpHeaders());
        ResponseEntity<PageResponse<PackageInfoDto>> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }
}
