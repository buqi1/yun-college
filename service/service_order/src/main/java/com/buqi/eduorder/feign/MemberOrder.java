package com.buqi.eduorder.feign;

import com.buqi.util.order.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
//这里写的是nacos里面的名字
@FeignClient("service-ucenter")
public interface MemberOrder {

    //根据用户id查询用户信息
    @PostMapping("/edumember/member/getMemberInfo/{id}}")
    public UcenterMemberOrder getMemberInfo(@PathVariable("id") String id);
}
