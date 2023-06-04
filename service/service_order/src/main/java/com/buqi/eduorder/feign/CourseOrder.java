package com.buqi.eduorder.feign;

import com.buqi.util.order.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-edu")
public interface CourseOrder {
    //根据courId查询信息
    @PostMapping("/eduservice/frontCourse/getCourseOrderInfo/{id}")
    public CourseWebVoOrder getCourseOrderInfo(@PathVariable("id") String id);
}
