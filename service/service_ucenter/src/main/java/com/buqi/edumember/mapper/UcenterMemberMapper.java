package com.buqi.edumember.mapper;

import com.buqi.edumember.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author buqi
 * @since 2023-05-14
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //根据时间查询当天的注册人数
    Integer registerCount(String day);
}
