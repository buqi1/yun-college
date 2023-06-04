package com.buqi.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduorder.entity.TOrder;
import com.buqi.eduorder.service.TOrderService;
import com.buqi.util.JwtUtils;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    //生成订单
    //拿到用户id，课程id,对信息进行一个整合，在将信息存入到订单中
    @PostMapping("saveOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        //登录的用户信息在token中
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //返回一个订单编号
        String orderNo = orderService.saveOrder(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    //查询订单信息
    @GetMapping("/getOrderInfo/{id}")
    public R getOrderInfo(@PathVariable String id){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",id);
        TOrder orderInfo = orderService.getOne(queryWrapper);
        return R.ok().data("item",orderInfo);
    }

    //根据课程id和用户id查询订单是否支付
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("status",1);
        int count = orderService.count(queryWrapper);
        if (count > 0) {//说明已支付
            return true;
        }else {
            return false;
        }
    }
}

