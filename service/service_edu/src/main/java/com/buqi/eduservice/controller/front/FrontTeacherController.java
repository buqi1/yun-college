package com.buqi.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.eduservice.entity.EduCourse;
import com.buqi.eduservice.entity.EduTeacher;
import com.buqi.eduservice.service.EduCourseService;
import com.buqi.eduservice.service.EduTeacherService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/frontTeacher")
public class FrontTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询
    @PostMapping("/pageteacher/{page}/{limit}")
    public R pageteacher(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        teacherService.page(teacherPage, wrapper);
        Map<String,Object> teacherMap = new HashMap<>();
        teacherMap.put("records",teacherPage.getRecords());
        teacherMap.put("current",teacherPage.getCurrent());
        teacherMap.put("pages",teacherPage.getPages());
        teacherMap.put("size",teacherPage.getSize());
        teacherMap.put("total",teacherPage.getTotal());
        teacherMap.put("hasNext",teacherPage.hasNext());//下一页
        teacherMap.put("hasPrevious",teacherPage.hasPrevious());//上一页
        return R.ok().data(teacherMap);
    }

    //讲师详情
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){
        //获取讲师信息
        EduTeacher teacherInfo = teacherService.getById(teacherId);
        //根据讲师id获取讲师所讲的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseInfo = courseService.list(wrapper);
        return R.ok().data("teacherInfo",teacherInfo).data("courseInfo",courseInfo);
    }
}
