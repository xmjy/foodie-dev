package indi.xm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: foodie-dev
 * @Package: indi.xm.controller
 * @ClassName: HelloController
 * @Author: albert.fang
 * @Description:
 * @Date: 2021/9/13 15:39
 */
@RestController
public class HelloController {

    @GetMapping("helloWorld")
    public String helloWorld(){
        return "hello world";
    }
}