package com.buqi.eduorder.service;

import com.buqi.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-05-30
 */
public interface TPayLogService extends IService<TPayLog> {

    //生成二维码
    Map saveWxCode(String orderNo);

    //查看订单状态，修改订单
    Map<String, String> getPayStatus(String orderNo);

    //通过map获取订单的状态
    void updateOrdersStatus(Map<String, String> map);
}
