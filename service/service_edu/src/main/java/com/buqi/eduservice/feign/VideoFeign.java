package com.buqi.eduservice.feign;

import com.buqi.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VideoHystrix.class)
/**
 * @PathVariable("id")      括号里面一定要写
 * List<String>     要加上范型
 * Feign:远程调用，，找到/eduvod/deleteVideo/{id}
 */
public interface VideoFeign {
    @DeleteMapping("/eduvod/deleteVideo/{id}")
    public R deleteVideo(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduvod/deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
