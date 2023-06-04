package com.buqi.eduservice.service.impl;

import com.buqi.eduservice.entity.EduCourseDescription;
import com.buqi.eduservice.mapper.EduCourseDescriptionMapper;
import com.buqi.eduservice.service.EduCourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    @Override
    public void deleteById(String courseId) {
        baseMapper.deleteById(courseId);
    }
}
