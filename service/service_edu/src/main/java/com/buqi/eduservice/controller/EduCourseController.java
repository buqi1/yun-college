package com.buqi.eduservice.controller;


import com.buqi.eduservice.entity.EduCourse;
import com.buqi.eduservice.entity.vo.CoursePublishVO;
import com.buqi.eduservice.entity.vo.CourseVO;
import com.buqi.eduservice.service.EduCourseService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-04-29
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseVO courseVO){
        String id = courseService.saveCourse(courseVO);
        return R.ok().data("courseId",id);
    }

    /**
     * 数据的回显
     */
    //1.根据courseId查询出所有的课程和简介表的信息
    @GetMapping("/getCourseVO/{courseId}")
    public R getCourseVO(@PathVariable String courseId){
        CourseVO courseVO = courseService.getCourseDescription(courseId);
        return R.ok().data("courseVO",courseVO);
    }

    //2.上面将查到的信息放在courseVO，现在对其进行修改
    @PostMapping("/updateCourseVO")
    public R updateCourseVO(@RequestBody CourseVO courseVO){
        courseService.updateCourseVO(courseVO);
        return R.ok();
    }

    //最终发布的数据显示
    //多表的数据进行显示
    @GetMapping("/getCoursePublish/{id}")
    public R getCoursePublish(@PathVariable String id){
        CoursePublishVO coursePublishVO = courseService.getCoursePublish(id);
        return R.ok().data("coursePublishVO",coursePublishVO);
    }

    //课程最终发布,status的变化
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布的状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    @GetMapping("/getList")
    public R getList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //删除课程，章节，小节，视频
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.deleteCourse(courseId);
        return R.ok();
    }
}

