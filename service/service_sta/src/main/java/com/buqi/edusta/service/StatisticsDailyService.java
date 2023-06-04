package com.buqi.edusta.service;

import com.buqi.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-05-31
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //对当天的数据进行统计
    void countData(String day);

    //图表显示，将查询的统计类型和时间范围进行查询
    Map<String, Object> showData(String type, String begin, String end);
}
