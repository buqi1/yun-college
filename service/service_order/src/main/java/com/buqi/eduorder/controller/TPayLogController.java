package com.buqi.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.eduorder.entity.TOrder;
import com.buqi.eduorder.service.TOrderService;
import com.buqi.eduorder.service.TPayLogService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-05-30
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    //生成二维码
    @PostMapping("/saveWxCode/{orderNo}")
    public R  saveWxCode(@PathVariable String orderNo){
        Map map = payLogService.saveWxCode(orderNo);
        return R.ok().data(map);
    }

    //查看订单状态，修改订单
    @GetMapping("/getPayStatus/{orderNo}")
    public R getPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.getPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付失败");
        }
        //通过map获取订单的状态
        if(map.get("trade_state").equals("SUCCESS")) {//支付成功
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }

}

