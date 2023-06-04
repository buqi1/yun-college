package com.buqi.edusta.feign;

import com.buqi.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface MemberFeign {

    //查询当天的注册人数进统计
    @PostMapping("/edumember/member/registerCount/{day}")
    public R registerCount(@PathVariable("day") String day);
}
