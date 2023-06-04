package com.buqi.eduservice.service;

import com.buqi.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void deleteById(String courseId);
}
