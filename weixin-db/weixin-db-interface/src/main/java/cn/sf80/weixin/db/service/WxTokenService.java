package cn.sf80.weixin.db.service;

import cn.sf80.weixin.db.pojo.WxToken;

public interface WxTokenService {
    String WEBACCESS="web_access";
    String WEBAUTH="web_auth";
    String INTERFACEUSE="interface_use";
    void saveToken(WxToken wxToken);
    WxToken getInterfaceUseToken(String appid,String secret,Integer expires);
}
