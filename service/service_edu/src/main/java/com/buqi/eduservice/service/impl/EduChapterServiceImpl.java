package com.buqi.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduservice.entity.EduChapter;
import com.buqi.eduservice.entity.EduVideo;
import com.buqi.eduservice.entity.vo.ChapterVO;
import com.buqi.eduservice.entity.vo.VideoVO;
import com.buqi.eduservice.mapper.EduChapterMapper;
import com.buqi.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.eduservice.service.EduVideoService;
import com.buqi.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;
    @Override
    public List<ChapterVO> getAllChapterVideoList(String id) {

        //课程列表大纲
        //1.查询出课程的所有章节（一级分类）
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",id);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterQueryWrapper);

        //2.查询出课程的章节的所有小节（er级分类）
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",id);
        List<EduVideo> eduVideoList = videoService.list(videoQueryWrapper);

        //创建最终的集合类
        List<ChapterVO> list = new ArrayList<>();

        //3.编历课程的章节
        for (int i = 0; i < eduChapterList.size(); i++) {
            //将集合的值赋值给eduChapter
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVO chapterVO = new ChapterVO();
            //chapterVO只有id,title,所有的小节
            BeanUtils.copyProperties(eduChapter,chapterVO);
            list.add(chapterVO);

            //创建所有小节的集合
            List<VideoVO> videoVOList = new ArrayList<>();
            String chapterId = chapterVO.getId();
            //4.编历章节的所有小节
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if (eduVideo.getChapterId().equals(chapterId)) {
                    VideoVO videoVO = new VideoVO();
                    BeanUtils.copyProperties(eduVideo,videoVO);
                    videoVOList.add(videoVO);
                    chapterVO.setChildren(videoVOList);
                }
            }

        }
        return list;
    }

    //删除章节
    /**
     * 1.如果章节没有小节，就直接删除
     * 2。如果章节有小节，就不能直接删除
     */
    @Override
    public void deleteChapter(String id) {
        //查询出章节中是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int count = videoService.count(wrapper);
        if (count>0) {
            throw new GuliException(20001,"不能直接删除");
        }else {
            baseMapper.deleteById(id);
        }
    }

    @Override
    public void deleteById(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
