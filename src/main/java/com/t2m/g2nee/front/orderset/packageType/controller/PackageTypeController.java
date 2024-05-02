package com.t2m.g2nee.front.orderset.packageType.controller;

import com.t2m.g2nee.front.orderset.packageType.dto.request.PackageSaveDto;
import com.t2m.g2nee.front.orderset.packageType.service.PackageTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 관리자가 포장지를 관리하기 위한 것과 관련된 controller입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Controller
@RequestMapping("/admin/packages")
public class PackageTypeController {

    private final PackageTypeService service;

    public PackageTypeController(PackageTypeService service) {
        this.service = service;
    }

    /**
     * 포장지 관리 메인 페이지로, 1페이지 포장지를 보여줍니다.
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String packageList(Model model) {
        model.addAttribute("packages", service.getAllPackage(1));
        return "admin/package/adminPackageList";
    }

    /**
     * 모든 포장지를 페이징처리하여 보여줍니다.
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String packagePage(@RequestParam int page, Model model) {
        model.addAttribute("packages", service.getAllPackage(page));
        return "admin/package/adminPackageList";
    }

    /**
     * 포장지를 저장하기 위한 form을 보여줍니다.
     *
     * @return
     */
    @GetMapping("/save")
    public String packageSaveForm() {
        return "admin/package/adminPackageSave";
    }

    /**
     * 실제 포장지를 저장합니다.
     *
     * @param name
     * @param price
     * @param isActivated
     * @return
     */
    @PostMapping("/save")
    public String createPackage(@RequestParam("name") String name,
                                @RequestParam("price") int price,
                                @RequestParam("isActivated") Boolean isActivated) {
        PackageSaveDto request = new PackageSaveDto(name, price, isActivated);
        service.createPackage(request);
        return "redirect:/admin/packages/list";
    }

    /**
     * 포장지를 수정하기 위한 form을 보여줍니다.
     *
     * @param packageId
     * @param model
     * @return
     */
    @GetMapping("/modify/{packageId}")
    public String modifyPointPolicyForm(@PathVariable("packageId") Long packageId, Model model) {
        model.addAttribute("package", service.getPackage(packageId));
        return "admin/package/adminPackageSave";
    }

    /**
     * 포장지를 수정합니다.
     *
     * @param packageId
     * @param name
     * @param price
     * @param isActivated
     * @return
     */

    @PutMapping("/modify/{packageId}")
    public String modifyPointPolicy(@PathVariable("packageId") Long packageId,
                                    @RequestParam("name") String name,
                                    @RequestParam("price") int price,
                                    @RequestParam("isActivated") Boolean isActivated) {
        PackageSaveDto request = new PackageSaveDto(name, price, isActivated);
        service.updatePackage(packageId, request);
        return "redirect:/admin/packages/list";
    }

    /**
     * 포장지 비활성화합니다.
     *
     * @param packageId
     * @return
     */
    @PatchMapping("/delete/{packageId}")
    public String deletePackage(@PathVariable("packageId") Long packageId) {
        service.deletePackage(packageId);
        return "redirect:/admin/packages/list";
    }

    /**
     * 포장지 활성화합니다.
     *
     * @param packageId
     * @return
     */
    @PatchMapping("/activate/{packageId}")
    public String activatePackage(@PathVariable("packageId") Long packageId) {
        service.activatePackage(packageId);
        return "redirect:/admin/packages/list";
    }
}
