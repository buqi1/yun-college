package com.buqi.eduorder.service;

import com.buqi.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-05-30
 */
public interface TOrderService extends IService<TOrder> {

    //生成订单
    String saveOrder(String courseId, String memberId);
}
