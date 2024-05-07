package com.t2m.g2nee.front.orderset.packagetype.adaptor;

import com.t2m.g2nee.front.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 백엔드에서 포장지와 관련된 값들을 가져옴
 *
 * @author : 김수빈
 * @since : 1.0
 */
public interface PackageTypeAdaptor {

    /**
     * 포장지를 저장함
     *
     * @param request
     * @return
     */
    PackageInfoDto requestCreatePackage(MultipartFile image, PackageSaveDto request);


    /**
     * 포장지를 수정함
     *
     * @param packageId
     * @param request
     * @return
     */
    PackageInfoDto requestUpdatePackage(Long packageId, MultipartFile image, PackageSaveDto request);


    /**
     * 특정 포장지 하나를 얻음
     *
     * @param packageId
     * @return
     */
    PackageInfoDto getPackage(Long packageId);

    /**
     * 모든 포장지를 페이징하여 반환
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
    boolean requestDeletePackage(Long packageId);

    /**
     * 비활성화된 포장지를 활성화 합니다.
     *
     * @param packageId
     * @return
     */
    boolean requestActivatePackage(Long packageId);

    /**
     * 활성화된 모든 포장지를 페이징하여 반환
     *
     * @param page
     * @return
     */
    PageResponse<PackageInfoDto> getActivatePackage(int page);

}
