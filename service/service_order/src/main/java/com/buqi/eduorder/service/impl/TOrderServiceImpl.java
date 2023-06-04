package com.buqi.eduorder.service.impl;

import com.buqi.eduorder.entity.TOrder;
import com.buqi.eduorder.feign.CourseOrder;
import com.buqi.eduorder.feign.MemberOrder;
import com.buqi.eduorder.mapper.TOrderMapper;
import com.buqi.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.eduorder.utils.OrderNoUtil;
import com.buqi.util.order.CourseWebVoOrder;
import com.buqi.util.order.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-05-30
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private CourseOrder courseOrder;

    @Autowired
    private MemberOrder memberOrder;
    //生成订单
    @Override
    public String saveOrder(String courseId, String memberId) {
        //课程信息（远程调用）
        CourseWebVoOrder courseOrderInfo = courseOrder.getCourseOrderInfo(courseId);
        //用户信息（远程调用）
        UcenterMemberOrder memberInfo = memberOrder.getMemberInfo(memberId);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseOrderInfo.getTitle());
        order.setCourseCover(courseOrderInfo.getCover());
        order.setTeacherName(courseOrderInfo.getTeacherName());
        order.setTotalFee(courseOrderInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());

        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1

        //将信息添加到订单表中
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
