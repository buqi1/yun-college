package com.buqi.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.eduservice.entity.EduCourse;
import com.buqi.eduservice.entity.EduCourseDescription;
import com.buqi.eduservice.entity.vo.CoursePublishVO;
import com.buqi.eduservice.entity.vo.CourseVO;
import com.buqi.eduservice.entity.vo.front.CourseFrontVo;
import com.buqi.eduservice.entity.vo.front.CourseWebVo;
import com.buqi.eduservice.feign.VideoFeign;
import com.buqi.eduservice.mapper.EduCourseMapper;
import com.buqi.eduservice.service.EduChapterService;
import com.buqi.eduservice.service.EduCourseDescriptionService;
import com.buqi.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.eduservice.service.EduVideoService;
import com.buqi.handler.GuliException;
import org.springframework.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private VideoFeign videoFeign;

    @Override
    public String saveCourse(CourseVO courseVO) {
        //CourseVO包含了两个表的内容，EduCourse和EduCourseDescription
        //添加到EduCourse中
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVO,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001,"添加数据失败");
        }
        //获取添加之后的id,因为两个表的id应保持一致
        String cid = eduCourse.getId();

        //添加到EduCourseDescription，但此时是在EduCourseServiceImpl中，不能使用EduCourseDescriptionService，手动注入进来
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseVO.getDescription());
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    @Override
    public CourseVO getCourseDescription(String courseId) {
        //查询出所有的课程
        CourseVO courseVO = new CourseVO();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseVO);
        //查询课程简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseVO);

        return courseVO;
    }

    //2.上面将查到的信息放在courseVO，现在对其进行修改
    @Override
    public void updateCourseVO(CourseVO courseVO) {
        //修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseVO,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i == 0) {
            throw new GuliException(20001,"修改课程失败");
        }
        //修改课程简介信息
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseVO,courseDescription);
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVO getCoursePublish(String id) {
        CoursePublishVO coursePublish = baseMapper.getCoursePublish(id);
        return coursePublish;
    }

    //删除课程(多表数据的删除)
    @Override
    public void deleteCourse(String courseId) {
        //1.删除课程
        baseMapper.deleteById(courseId);
        //2.删除章节
        chapterService.deleteById(courseId);
        //3.删除小节
        videoService.deleteById(courseId);
        //4.删除描述
        courseDescriptionService.deleteById(courseId);

    }

    //带条件查询课程的分页信息（在前台）
    @Override
    public Map<String, Object> getCoursePage(Page<EduCourse> courseFrontVoPage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空则动态拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(courseFrontVoPage,wrapper);


        Map<String,Object> courseMap = new HashMap<>();

        courseMap.put("records",courseFrontVoPage.getRecords());
        courseMap.put("current",courseFrontVoPage.getCurrent());
        courseMap.put("pages",courseFrontVoPage.getPages());
        courseMap.put("size",courseFrontVoPage.getSize());
        courseMap.put("total",courseFrontVoPage.getTotal());
        courseMap.put("hasNext",courseFrontVoPage.hasNext());//下一页
        courseMap.put("hasPrevious",courseFrontVoPage.hasPrevious());//上一页
        return courseMap;
    }

    //查询课程的基本信息
    @Override
    public CourseWebVo getCourseFrontInfo(String courseId) {

        return baseMapper.getCourseFrontInfo(courseId);
    }
}
