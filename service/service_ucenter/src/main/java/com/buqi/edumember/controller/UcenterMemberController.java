package com.buqi.edumember.controller;


import com.buqi.edumember.entity.UcenterMember;
import com.buqi.edumember.entity.vo.RegisterVo;
import com.buqi.edumember.service.UcenterMemberService;
import com.buqi.util.JwtUtils;
import com.buqi.util.R;
import com.buqi.util.order.UcenterMemberOrder;
import org.apache.poi.ss.usermodel.IconMultiStateFormatting;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-05-14
 */
@RestController
@RequestMapping("/edumember/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //登录
    @PostMapping("/login")
    public R login(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //通过JWT得到用户信息，并将信息进行返回
    //根据token字符串获取用户id
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    //根据用户id查询用户信息
    @PostMapping("getMemberInfo/{id}}")
    public UcenterMemberOrder getMemberInfo(@PathVariable("id") String id){
        UcenterMember memberInfo = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(memberInfo,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //根据时间查询注册人数，远程调用到service_sta中，进行数据统计
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day){
        Integer count = memberService.registerCount(day);
        return R.ok().data("RegisterCount",count);
    }
}

