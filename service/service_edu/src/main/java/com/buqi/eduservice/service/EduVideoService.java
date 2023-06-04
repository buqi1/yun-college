package com.buqi.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteById(String courseId);

}
