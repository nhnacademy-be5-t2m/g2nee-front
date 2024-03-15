package com.t2m.g2nee.front;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping(value={"/index.html","/"})
    public String hello() {

        return "index/index";
    }
}
