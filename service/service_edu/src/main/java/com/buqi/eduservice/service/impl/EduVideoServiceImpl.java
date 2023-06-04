package com.buqi.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduservice.entity.EduVideo;
import com.buqi.eduservice.feign.VideoFeign;
import com.buqi.eduservice.mapper.EduVideoMapper;
import com.buqi.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.handler.GuliException;
import com.buqi.util.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VideoFeign videoFeign;
    //删除小节包括视频
    @Override
    public void deleteById(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        //只搜集视频id
        wrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(wrapper);

        //换成1,2,3，。。的形式
        List<String> list = new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            EduVideo video = videoList.get(i);
            String ids = video.getVideoSourceId();
            if (!StringUtils.isEmpty(ids)){
                list.add(ids);
            }
        }

        if (list.size()>0) {
            R result = videoFeign.deleteBatch(list);
            if (result.getCode() == 20001) {
                throw new GuliException(20001,"删除视频失败。。。。buqi");
            }
        }

        //这里删除只是在数据库中删除视频，但aliyun还是存在
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        baseMapper.delete(wrapper1);
    }



}
