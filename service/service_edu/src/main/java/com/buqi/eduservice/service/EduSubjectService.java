package com.buqi.eduservice.service;

import com.buqi.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buqi.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-04-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAll();
}
