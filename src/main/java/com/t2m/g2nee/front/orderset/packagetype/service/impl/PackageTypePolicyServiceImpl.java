package com.t2m.g2nee.front.orderset.packagetype.service.impl;

import com.t2m.g2nee.front.orderset.packagetype.adaptor.PackageTypeAdaptor;
import com.t2m.g2nee.front.orderset.packagetype.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packagetype.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.orderset.packagetype.service.PackageTypeService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * PackageTypeService의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
public class PackageTypePolicyServiceImpl implements PackageTypeService {

    private final PackageTypeAdaptor adaptor;

    public PackageTypePolicyServiceImpl(PackageTypeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public void createPackage(MultipartFile image, PackageSaveDto request) {
        adaptor.requestCreatePackage(image, request);
    }

    @Override
    public void updatePackage(Long packageId, MultipartFile image, PackageSaveDto request) {
        adaptor.requestUpdatePackage(packageId, image, request);
    }

    @Override
    public PackageInfoDto getPackage(Long packageId) {
        return adaptor.getPackage(packageId);
    }

    @Override
    public PageResponse<PackageInfoDto> getAllPackage(int page) {
        return adaptor.getAllPackage(page);
    }

    @Override
    public void deletePackage(Long packageId) {
        adaptor.requestDeletePackage(packageId);
    }

    @Override
    public void activatePackage(Long packageId) {
        adaptor.requestActivatePackage(packageId);
    }

    @Override
    public PageResponse<PackageInfoDto> getActivatedPackage(int page) {
        return adaptor.getActivatePackage(page);
    }
}
