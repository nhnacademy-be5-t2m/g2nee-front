package com.t2m.g2nee.front.policyset.pointPolicy.controller;

import com.t2m.g2nee.front.policyset.pointPolicy.service.PointPolicyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PointPolicyController {

    private final PointPolicyService service;


    public PointPolicyController(PointPolicyService service) {
        this.service = service;
    }


    public String pointPolicyList(@RequestParam int page, Model model){
        model.addAttribute("point", service.getAllPointPolicies(page));
        return "admin/pointPolicy/pointPolicyList";
    }
}
