package com.buqi.edumember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buqi.edumember.entity.UcenterMember;
import com.buqi.edumember.entity.vo.RegisterVo;
import com.buqi.edumember.mapper.UcenterMemberMapper;
import com.buqi.edumember.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buqi.edumember.utils.MD5;
import com.buqi.handler.GuliException;
import com.buqi.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author buqi
 * @since 2023-05-14
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"登录失败");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        //如果手机号码存在，就这一行的数据全部放入ucenterMember
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        if (ucenterMember == null) {
            throw new GuliException(20001,"手机号不正确");
        }
        //因为上面的password是用户输入的密码，而数据库中的密码是经过加密处理的
        //用MD5（只能加密，不能解密）将password进行加密
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())) {
            throw new GuliException(20001,"密码不正确");
        }
        if (ucenterMember.getIsDisabled()) {
            throw new GuliException(20001,"该账号被禁用");
        }

        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;

    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //将registerVo数据取出
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(code) ||StringUtils.isEmpty(password) ) {
            throw new GuliException(20001,"注册失败");
        }

        //取出redis的验证码与输入的验证码进行比较
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!redisCode.equals(code)) {
            throw new GuliException(20001,"注册失败");
        }

        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {//说明手机号存在
            throw new GuliException(20001,"注册失败");
        }

        //将数据存入数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//对密码进行加密
        member.setAvatar("https://edu-buqi.oss-cn-beijing.aliyuncs.com/2023/04/28/8f704de0-a054-4a4c-b1ce-1033676746eefile.png");
        member.setIsDisabled(false);
        baseMapper.insert(member);
    }

    //判断扫码人的信息是否存在
    @Override
    public UcenterMember getMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = new UcenterMember();
        member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer registerCount(String day) {
        return baseMapper.registerCount(day);
    }

}
