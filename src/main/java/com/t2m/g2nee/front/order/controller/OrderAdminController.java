package com.t2m.g2nee.front.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderAdminController {
    @GetMapping("/admin/orders")
    public String orderList(){

        return "admin/adminOrder";
    }
    @GetMapping("/shop/order")
    public String order(){
        return "orders/order";
    }
    @GetMapping("/order-testing")
    public String ordertest(){
        return "orders/order-test";
    }
    @GetMapping("/order/{orderId}")
    public String orderInfo(){
        return "orders/order";
    }

}
