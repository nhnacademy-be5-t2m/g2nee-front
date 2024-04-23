package com.t2m.g2nee.front.orderset.packageType.controller;

import com.t2m.g2nee.front.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packageType.dto.response.PackageInfoDto;
import com.t2m.g2nee.front.orderset.packageType.service.PackageTypeService;
import com.t2m.g2nee.front.utils.PageResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/packages")
public class PackageTypeController {

    private final PackageTypeService service;

    public PackageTypeController(PackageTypeService service) {
        this.service = service;
    }

    /**
     * 포장지 목록 보이기(메인)
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String packageList(Model model){
        model.addAttribute("packages", service.getAllPackage(1));
        return "admin/package/adminPackageList";
    }

    /**
     * 포장지 목록 보이기(페이지)
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String packagePage(@RequestParam int page, Model model){
        model.addAttribute("packages", service.getAllPackage(page));
        return "admin/package/adminPackageList";
    }

    /**
     * 배송비 정책 저장 양식 보이기
     * @return
     */
    @GetMapping("/save")
    public String packageSaveForm(){
        return "admin/package/adminPackageSave";
    }

    /**
     * 실제 배송비 정책 저장
     * @param name
     * @param price
     * @param isActivated
     * @return
     */
    @PostMapping("/save")
    public String createPackage(@RequestParam("name") String name,
                                    @RequestParam("price") int price,
                                    @RequestParam("isActivated") Boolean isActivated){
        PackageSaveDto request = new PackageSaveDto(name, price, isActivated);
        service.createPackage(request);
        return "redirect:/admin/packages/list";
    }


    @GetMapping("/modify/{packageId}")
    public String modifyPointPolicyForm(@PathVariable("packageId") Long packageId, Model model){
        model.addAttribute("package", service.getPackage(packageId));
        return "admin/package/adminPackageSave";
    }


    @PutMapping("/modify/{packageId}")
    public String modifyPointPolicy(@PathVariable("packageId") Long packageId,
                                    @RequestParam("name") String name,
                                    @RequestParam("price") int price,
                                    @RequestParam("isActivated") Boolean isActivated){
        PackageSaveDto request = new PackageSaveDto(name, price, isActivated);
        service.updatePackage(packageId, request);
        return "redirect:/admin/packages/list";
    }

    /**
     * 포장지 비활성화
     * @param packageId
     * @return
     */
    @PatchMapping("/delete/{packageId}")
    public String deletePackage(@PathVariable("packageId") Long packageId){
        service.deletePackage(packageId);
        return "redirect:/admin/packages/list";
    }

    /**
     * 포장지 활성화
     * @param packageId
     * @return
     */
    @PatchMapping("/activate/{packageId}")
    public String activatePackage(@PathVariable("packageId") Long packageId){
        service.activatePackage(packageId);
        return "redirect:/admin/packages/list";
    }
}
