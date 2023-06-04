package com.buqi.msm.controller;

import com.buqi.msm.service.MsmService;
import com.buqi.msm.utils.RandomUtil;
import com.buqi.util.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送验证码
    @GetMapping("send/{phone}")
    public R send(@PathVariable String phone){

        //设置五分钟的有效时间
        //从redis中获取验证码，，获取到了直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        //如果没有就通过阿里云发送
        //随机生成，传递给阿里云登录
        code = RandomUtil.getFourBitRandom();
        Map<String,String> param = new HashMap<>();
        param.put("code",code);
        //调用方法进行发送
        boolean isSend = msmService.send(param,phone);
        if (isSend) {
            //设置五分钟的有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }

    }
}
