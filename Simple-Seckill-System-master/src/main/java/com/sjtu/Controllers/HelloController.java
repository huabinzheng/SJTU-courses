package com.sjtu.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhenghb on 2017/7/8.
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String say() {
        return "Hello Seckill Project";
    }
}