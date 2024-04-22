package com.t2m.g2nee.front.orderset.packageType.service.impl;

import com.t2m.g2nee.front.orderset.packageType.adaptor.PackageTypeAdaptor;
import com.t2m.g2nee.front.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.orderset.packageType.service.PackageTypeService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class PackageTypePolicyServiceImpl implements PackageTypeService {

    private final PackageTypeAdaptor adaptor;

    public PackageTypePolicyServiceImpl(PackageTypeAdaptor adaptor) {
        this.adaptor = adaptor;
    }

    @Override
    public void createPackage(PackageSaveDto request) {
        adaptor.requestCreatePackage(request);
    }

    @Override
    public void updatePackage(Long packageId, PackageSaveDto request) {
        adaptor.requestUpdatePackage(packageId, request);
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
}
