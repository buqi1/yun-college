package com.buqi.edumember.service;

import com.buqi.edumember.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.buqi.edumember.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author buqi
 * @since 2023-05-14
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    //注册
    void register(RegisterVo registerVo);

    //查看扫码人的信息是否存在
    UcenterMember getMember(String openid);

    //根据时间查询注册人数，远程调用到service_sta中，进行数据统计
    Integer registerCount(String day);


}
