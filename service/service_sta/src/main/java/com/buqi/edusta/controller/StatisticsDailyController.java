package com.buqi.edusta.controller;


import com.buqi.edusta.service.StatisticsDailyService;
import com.buqi.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.logging.Handler;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author buqi
 * @since 2023-05-31
 */
@RestController
@RequestMapping("/edusta/statisticsdaily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    //将当天的数据进行统计
    @PostMapping("/countData/{day}")
    public R countData(@PathVariable String day){
        staService.countData(day);
        return R.ok();
    }
    
    //图表显示，将查询的统计类型和时间范围进行查询
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        //返回一个map集合，有类型数据（y轴），时间数据（x轴）
        Map<String,Object> map = staService. showData(type,begin,end);
        return R.ok().data(map);
    }
}

