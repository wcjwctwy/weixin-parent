package cn.sf80.weixin.spring.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wx")
public class WxPlatformConfig {
    private String appid;
    private String appsecret;
    private String accessToken;
    private String authSuccessUrl;
    private String domain;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getAuthSuccessUrl() {
        return authSuccessUrl;
    }

    public void setAuthSuccessUrl(String authSuccessUrl) {
        this.authSuccessUrl = authSuccessUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "WxPlatformConfig{" +
                "appid='" + appid + '\'' +
                ", appsecret='" + appsecret + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
