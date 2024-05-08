package com.t2m.g2nee.front.orderset.packagetype.controller;

import com.t2m.g2nee.front.orderset.packagetype.service.PackageTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/orders/packages")
public class PackageTypeController {

    private final PackageTypeService service;

    public PackageTypeController(PackageTypeService service) {
        this.service = service;
    }


    /**
     * 활성화된 포장지를 페이징처리하여 보여줍니다.
     *
     * @param page
     * @param model
     * @return
     */
    @GetMapping
    public String packageActivatedPage(@RequestParam(defaultValue = "1") int page, Model model) {
        model.addAttribute("packages", service.getActivatedPackage(page));
        return "order/packageList";
    }
}
