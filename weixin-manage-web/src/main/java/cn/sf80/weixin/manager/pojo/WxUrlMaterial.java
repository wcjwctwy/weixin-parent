package cn.sf80.weixin.manager.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wx.url.material")
public class WxUrlMaterial {
private String tmp;
private String newsEver;
private String imageEver;
private String othersEver;

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getNewsEver() {
        return newsEver;
    }

    public void setNewsEver(String newsEver) {
        this.newsEver = newsEver;
    }

    public String getImageEver() {
        return imageEver;
    }

    public void setImageEver(String imageEver) {
        this.imageEver = imageEver;
    }

    public String getOthersEver() {
        return othersEver;
    }

    public void setOthersEver(String othersEver) {
        this.othersEver = othersEver;
    }
}
