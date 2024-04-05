package com.t2m.g2nee.front.category.adaptor.impl;

import static com.t2m.g2nee.front.utils.HttpHeadersUtil.getHttpHeaders;

import com.t2m.g2nee.front.category.adaptor.CategoryAdaptor;
import com.t2m.g2nee.front.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.front.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CategoryAdaptorImpl implements CategoryAdaptor {

    private static final ParameterizedTypeReference<List<CategoryInfoDto>> LIST_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };
    private static final ParameterizedTypeReference<PageResponse<CategoryInfoDto>> PAGE_TYPE_REF
            = new ParameterizedTypeReference<>() {
    };
    private final RestTemplate restTemplate;


    @Value("${g2nee-gateway}")
    private String gateway;
    
    private String baseUrl;

    public CategoryAdaptorImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void initUrl(){
        baseUrl = gateway + "/shop/categories";
    }

    @Override
    public List<CategoryInfoDto> getRootCategories() {
        HttpEntity<CategoryInfoDto> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<List<CategoryInfoDto>> response = restTemplate
                .exchange(baseUrl, HttpMethod.GET, entity, LIST_TYPE_REF);

        return response.getBody();
    }

    @Override
    public List<CategoryInfoDto> getSubCategories(Long categoryId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+categoryId)
                .path("/sub")
                .build();

        HttpEntity<CategoryInfoDto> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<List<CategoryInfoDto>> response = restTemplate
                .exchange(url.toUriString(), HttpMethod.GET, entity, LIST_TYPE_REF);

        return response.getBody();
    }

    @Override
    public PageResponse<CategoryInfoDto> getAllCategories(int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/all")
                .queryParam("page", page)
                .build();

        HttpEntity<CategoryInfoDto> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<PageResponse<CategoryInfoDto>> response = restTemplate
                .exchange(url.toUriString(), HttpMethod.GET, entity, PAGE_TYPE_REF);

        return response.getBody();
    }

    @Override
    public CategoryInfoDto getCategory(Long categoryId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+categoryId)
                .build();

        HttpEntity<CategoryInfoDto> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<CategoryInfoDto> response = restTemplate
                .exchange(url.toUriString(), HttpMethod.GET,
                        entity, CategoryInfoDto.class);

        return response.getBody();
    }

    @Override
    public PageResponse<CategoryInfoDto> getCategoriesByName(String categoryName, int page) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/search")
                .queryParam("name", categoryName)
                .queryParam("page", page)
                .build();

        HttpEntity<CategoryInfoDto> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<PageResponse<CategoryInfoDto>> response = restTemplate
                .exchange(url.toUriString(), HttpMethod.GET,
                        entity, new ParameterizedTypeReference<>() {
                        });

        return response.getBody();
    }

    @Override
    public CategoryInfoDto requestCreatCategory(CategorySaveDto request) {
        HttpEntity<CategorySaveDto> entity = new HttpEntity<>(request, getHttpHeaders());
        ResponseEntity<CategoryInfoDto> response =
                restTemplate.exchange(baseUrl, HttpMethod.POST, entity, CategoryInfoDto.class);

        return response.getBody();
    }

    @Override
    public CategoryInfoDto requestModifyCategory(Long categoryId, CategorySaveDto request) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+categoryId)
                .build();

        HttpEntity<CategorySaveDto> entity = new HttpEntity<>(request, getHttpHeaders());
        ResponseEntity<CategoryInfoDto> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PUT, entity, CategoryInfoDto.class);

        return response.getBody();
    }

    @Override
    public Boolean requestDeleteCategory(Long categoryId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+categoryId)
                .build();

        HttpEntity<Boolean> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.DELETE, entity, Boolean.class);

        return response.getBody();
    }

    @Override
    public Boolean requestActiveCategory(Long categoryId) {
        UriComponents url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/"+categoryId)
                .build();

        HttpEntity<Boolean> entity = new HttpEntity<>(getHttpHeaders());
        ResponseEntity<Boolean> response =
                restTemplate.exchange(url.toUriString(), HttpMethod.PATCH, entity, Boolean.class);

        return response.getBody();
    }
}
