package cn.sf80.weixin.manager.controller;

import cn.sf80.weixin.common.pojo.WeixinResult;
import cn.sf80.weixin.db.pojo.WxToken;
import cn.sf80.weixin.db.service.WxTokenService;
import cn.sf80.weixin.spring.pojo.WxPlatformConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController {

    @Autowired
    private WxTokenService wxTokenService;

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @RequestMapping("/token/interfaceUse")
    @ResponseBody
    public WeixinResult getInterfaceUseToken(){
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(wxPlatformConfig.getAppid(), wxPlatformConfig.getAppsecret(), 7200);
        return WeixinResult.ok(interfaceUseToken);
    }

}
