package com.buqi.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buqi.eduservice.entity.EduCourse;
import com.buqi.eduservice.entity.vo.ChapterVO;
import com.buqi.eduservice.entity.vo.front.CourseFrontVo;
import com.buqi.eduservice.entity.vo.front.CourseWebVo;
import com.buqi.eduservice.client.OrderFeign;
import com.buqi.eduservice.service.EduChapterService;
import com.buqi.eduservice.service.EduCourseService;
import com.buqi.util.JwtUtils;
import com.buqi.util.R;
import com.buqi.util.order.CourseWebVoOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/frontCourse")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderFeign orderFeign;

    //分页查询课程
    @PostMapping("/coursePage/{page}/{limit}")
    //@RequestBody(required = false)后面的courseFrontVo可以为空
    public R coursePage(@PathVariable long page, @PathVariable long limit,
                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        //将查询到的信息放入map集合中
        Page<EduCourse> courseFrontVoPage = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCoursePage(courseFrontVoPage,courseFrontVo);
        return R.ok().data(map);
    }

    //课程详情
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId, HttpServletRequest request){
        //查询基本信息
        CourseWebVo courseWebVo = courseService.getCourseFrontInfo(courseId);
        //查询章节，小节，视频
        List<ChapterVO> chapterVideoList = chapterService.getAllChapterVideoList(courseId);
        //查询订单是否已支付
        boolean buyCourse = orderFeign.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy",buyCourse);
    }

    //根据courId查询信息
    @PostMapping("getCourseOrderInfo/{id}")
    public CourseWebVoOrder getCourseOrderInfo(@PathVariable("id") String id){
        EduCourse courseInfo = courseService.getById(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }

}
