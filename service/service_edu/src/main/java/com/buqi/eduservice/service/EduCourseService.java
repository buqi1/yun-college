package com.buqi.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buqi.eduservice.entity.vo.CoursePublishVO;
import com.buqi.eduservice.entity.vo.CourseVO;
import com.buqi.eduservice.entity.vo.front.CourseFrontVo;
import com.buqi.eduservice.entity.vo.front.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourse(CourseVO courseVO);

    CourseVO getCourseDescription(String courseId);

    void updateCourseVO(CourseVO courseVO);

    CoursePublishVO getCoursePublish(String id);

    void deleteCourse(String courseId);

    Map<String, Object> getCoursePage(Page<EduCourse> courseFrontVoPage, CourseFrontVo courseFrontVo);

    //查询基本信息
    CourseWebVo getCourseFrontInfo(String courseId);
}
