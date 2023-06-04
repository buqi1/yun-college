package com.buqi.eduservice.feign;

import com.buqi.util.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VideoHystrix implements VideoFeign{

    //VideoFeign请求失败，执行下面的方法
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除视频失败。。。");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("删除多个视频失败。。。");
    }
}
