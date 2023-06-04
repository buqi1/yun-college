package com.buqi.eduservice.controller;


import com.buqi.eduservice.entity.EduVideo;
import com.buqi.eduservice.feign.VideoFeign;
import com.buqi.eduservice.service.EduVideoService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VideoFeign videoFeign;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok().data("video",eduVideo);
    }

    //删除小节
    //还需完善。。。。。删除视频
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable String id){
        //id是小节的id,先得到视频id再使用feign调用方法进行删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //删除视频
        videoFeign.deleteVideo(videoSourceId);
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }

    //根据id查询小节信息
    @GetMapping("/getVideo/{id}")
    public R getVideo(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return R.ok().data("video",video);
    }

    //编辑小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

