package com.buqi.eduservice.controller;


import com.buqi.eduservice.entity.EduSubject;
import com.buqi.eduservice.entity.subject.OneSubject;
import com.buqi.eduservice.service.EduSubjectService;
import com.buqi.util.R;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-04-28
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        eduSubjectService.addSubject(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAll();
        return R.ok().data("list",list);
    }
}

