package com.buqi.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.buqi.handler.GuliException;
import com.buqi.util.R;
import com.buqi.vod.service.VodService;
import com.buqi.vod.utils.InitVodClient;
import com.buqi.vod.utils.VodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/eduvod")
/**
 * nacos的启动： startup.cmd -m standalone
 */
public class VodController {

    @Autowired
    private VodService vodService;
    //上传文件
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file){
        String id = vodService.uploadVideo(file);
        return R.ok().data("id",id);
    }

    //删除视频
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.KEY_ID, VodUtils.KEY_SECRET);
            //创建request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置id值
            request.setVideoIds(id);
            //调用client方法删除视频
            client.getAcsResponse(request);

            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    //删除多个视频
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList) {

        vodService.deleteBatch(videoList);
        return R.ok();
    }
    //点击小节可以播放视频,获取播放凭证
    @GetMapping("/getPlayAuto/{videoId}")
    public R getPlayAuto(@PathVariable String videoId){
        try{
            //创建并初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.KEY_ID, VodUtils.KEY_SECRET);
            //初始化request,response
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            //调用方法获取凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e){
            throw new GuliException(20001,"获取凭证失败");
        }


    }
}
