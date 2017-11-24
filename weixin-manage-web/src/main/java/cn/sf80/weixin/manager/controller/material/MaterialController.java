package cn.sf80.weixin.manager.controller.material;

import cn.sf80.weixin.common.pojo.WeixinResult;
import cn.sf80.weixin.common.utils.HttpsUtil;
import cn.sf80.weixin.db.pojo.WxToken;
import cn.sf80.weixin.db.service.WxTokenService;
import cn.sf80.weixin.manager.pojo.WxUrlMaterial;
import cn.sf80.weixin.spring.pojo.WxPlatformConfig;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MaterialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private WxUrlMaterial wxUrlMaterial;

    @Autowired
    private WxTokenService wxTokenService;

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @GetMapping("material/material")
    public String material() {
        return "material/material";
    }

    @GetMapping("material/material-add")
    public String materialAdd() {
        return "material/material-add";
    }

    @PostMapping("material/material-add")
    @ResponseBody
    public WeixinResult materialUpload(String urlType, String type, MultipartFile media) {
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(wxPlatformConfig.getAppid(), wxPlatformConfig.getAppsecret(), 7200);
        String wxUrl;
        switch (urlType) {
            case "tmp":
                wxUrl = wxUrlMaterial.getTmp();
                break;
            case "newsEver":
                wxUrl = wxUrlMaterial.getNewsEver();
                break;
            case "imageEver":
                wxUrl = wxUrlMaterial.getImageEver();
                break;
            default:
                wxUrl = wxUrlMaterial.getOthersEver();

        }
        String url = wxUrl + "?access_token=" + interfaceUseToken.getAccessToken()
                + "&type=" + type;
        String post = null;
        try {
            post = HttpsUtil.upLoadFile(url, media);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WeixinResult.build(200, post);
    }

    @GetMapping("material/material-list")
    @ResponseBody
    public WeixinResult materialList(Integer offset, Integer count, String type) {
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(wxPlatformConfig.getAppid(), wxPlatformConfig.getAppsecret(), 7200);
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + interfaceUseToken.getAccessToken();
        Map<String, Object> data = new HashMap<>();
        data.put("offset", offset);
        data.put("count", count);
        data.put("type", type);
        String body = JSON.toJSONString(data);
        LOGGER.info("body==>" + body);
        String post = HttpsUtil.httpsRequestToString(url, "POST", body);
        return WeixinResult.build(200, post);
    }
}
