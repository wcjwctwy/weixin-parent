package cn.sf80.weixin.spring.config;

import cn.sf80.weixin.spring.pojo.WxPlatformConfig;

public class WxSpringServer {

    private WxPlatformConfig wxPlatformConfig;

    public WxPlatformConfig getWxPlatformConfig() {
        return wxPlatformConfig;
    }

    public void setWxPlatformConfig(WxPlatformConfig wxPlatformConfig) {
        this.wxPlatformConfig = wxPlatformConfig;
    }
}
