package com.buqi.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduservice.entity.EduCourse;
import com.buqi.eduservice.entity.EduTeacher;
import com.buqi.eduservice.service.EduCourseService;
import com.buqi.eduservice.service.EduTeacherService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class FrontIndexController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    @GetMapping("/frontcourse")
    public R frontcourse(){
        //显示前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //用于拼接SQL语句
        wrapper.last("limit 8");
        List<EduCourse> course = courseService.list(wrapper);

        //显示前4个老师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        //用于拼接SQL语句
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacher = teacherService.list(wrapperTeacher);

        return R.ok().data("course",course).data("teacher",teacher);

    }
}
