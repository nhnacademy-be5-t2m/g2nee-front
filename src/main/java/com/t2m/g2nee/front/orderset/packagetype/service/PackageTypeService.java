package com.t2m.g2nee.front.orderset.packagetype.service;

import com.t2m.g2nee.front.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 포장지에 관련된 서비스입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PackageTypeService {

    /**
     * 포장지를 저장합니다.
     *
     * @param request
     * @return
     */
    void createPackage(MultipartFile image, PackageSaveDto request);


    /**
     * 포장지를 수정합니다.
     *
     * @param packageId
     * @param request
     * @return
     */
    void updatePackage(Long packageId, MultipartFile image, PackageSaveDto request);


    /**
     * 특정 포장지 하나를 반환합니다.
     *
     * @param packageId
     * @return
     */
    PackageInfoDto getPackage(Long packageId);

    /**
     * 모든 포장지를 페이징하여 반환합니다.
     *
     * @param page
     * @return
     */
    PageResponse<PackageInfoDto> getAllPackage(int page);

    /**
     * 포장지를 soft delete합니다.
     *
     * @param packageId
     * @return
     */
    void deletePackage(Long packageId);

    /**
     * 비활성화된 포장지를 활성화 합니다.
     *
     * @param packageId
     * @return
     */
    void activatePackage(Long packageId);

    /**
     * 활성화된 모든 포장지를 페이징하여 반환합니다.
     *
     * @param page
     * @return
     */
    PageResponse<PackageInfoDto> getActivatedPackage(int page);
}
