package com.buqi.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.edusta.entity.StatisticsDaily;
import com.buqi.edusta.feign.MemberFeign;
import com.buqi.edusta.mapper.StatisticsDailyMapper;
import com.buqi.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.util.R;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-05-31
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private MemberFeign memberFeign;
    //对当天的数据进行统计
    @Override
    public void countData(String day) {
        //对同一天的数据再次进行统计时，删除第一个数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //远程调用得到当天注册的人数
        R register = memberFeign.registerCount(day);
        Integer registerCount = (Integer) register.getData().get("RegisterCount");

        //随机生成其他要统计的数据
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(registerCount);
        sta.setDateCalculated(day);

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);
    }

    //图表显示，将查询的统计类型和时间范围进行查询
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        //前端进行显示的是json类型的数据，后端的表现形式为list集合
        //两个集合，类型集合，时间集合
        //再将其存入到map中
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //时间集合
        ArrayList<String> timeData = new ArrayList<>();
        //统计类型集合
        ArrayList<Integer> typeData = new ArrayList<>();
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily staData = staList.get(i);
            timeData.add(staData.getDateCalculated());
            switch (type){
                case "register_num":
                    typeData.add(staData.getRegisterNum());
                    break;
                case "login_num":
                    typeData.add(staData.getLoginNum());
                    break;
                case "video_view_num":
                    typeData.add(staData.getVideoViewNum());
                    break;
                case "course_num":
                    typeData.add(staData.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("timeData",timeData);
        map.put("typeData",typeData);
        return map;
    }
}
