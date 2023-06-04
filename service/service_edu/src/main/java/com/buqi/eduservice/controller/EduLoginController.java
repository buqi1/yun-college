package com.buqi.eduservice.controller;

import com.buqi.util.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
/**
 * 跨域的两种解决方法
 * 1.加上注释@CrossOrigin
 * 2.在网关中进行设置
 * 两者不能同时设置
 */
@CrossOrigin(allowCredentials = "true")
public class EduLoginController {

    @PostMapping("/getLogin")
    public R getLogin(){
        return R.ok().data("token","admin");
    }

    @GetMapping("/getInfo")
    public R getInfo(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
