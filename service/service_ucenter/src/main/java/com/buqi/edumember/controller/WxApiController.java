package com.buqi.edumember.controller;

import com.buqi.edumember.entity.UcenterMember;
import com.buqi.edumember.service.UcenterMemberService;
import com.buqi.edumember.utils.ConstantWxUtils;
import com.buqi.edumember.utils.HttpClientUtils;
import com.buqi.handler.GuliException;
import com.buqi.util.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //获取扫描人的信息
    @GetMapping("/callback")
    public String callback(String code,String state){

        try {
            //2 拿着code请求 微信固定的地址，得到两个值 accsess_token 和 openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //accessTokenUrl,在里面将accessToken,openid拿到，将字符串进行转换
            Gson gson = new Gson();
            HashMap fromJson = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)fromJson.get("access_token");
            String openid = (String)fromJson.get("openid");
            //如果数据库中有数据，就不进行添加
            UcenterMember member = memberService.getMember(openid);
            if (member == null) {
                //拿到之后，再去微信的固定地址去访问，获取扫描人的信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接信息
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname");//昵称
                String headimgurl = (String)userInfoMap.get("headimgurl");//头像

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                member.setOpenid(openid);
                memberService.save(member);
            }
            //将信息在页面进行显示，之前登录时，数据是在cookie中拿到
            //cookie存在跨域的问题，所以用地址后带有token进行信息的传递
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+token;

        }catch (Exception e){
            throw new GuliException(20001,"登录失败");
        }

    }

    //生成微信二维码
    @GetMapping("/login")
    public String getWxCode(){

        //对地址进行拼接
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect进行EnCode编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try{
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }


        //设置baseUrl中%s的值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "buqi"
        );
        return "redirect:"+url;
    }
}
