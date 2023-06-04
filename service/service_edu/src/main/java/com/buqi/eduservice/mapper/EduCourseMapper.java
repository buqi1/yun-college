package com.buqi.eduservice.mapper;

import com.buqi.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buqi.eduservice.entity.vo.CoursePublishVO;
import com.buqi.eduservice.entity.vo.front.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVO getCoursePublish(String id);

    CourseWebVo getCourseFrontInfo(String courseId);
}
