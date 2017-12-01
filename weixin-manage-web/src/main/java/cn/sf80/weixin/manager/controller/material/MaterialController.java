package cn.sf80.weixin.manager.controller.material;

import cn.sf80.weixin.common.pojo.WeixinResult;
import cn.sf80.weixin.common.utils.HttpsUtil;
import cn.sf80.weixin.db.pojo.WxMaterial;
import cn.sf80.weixin.db.pojo.WxToken;
import cn.sf80.weixin.db.service.WxMaterialService;
import cn.sf80.weixin.db.service.WxTokenService;
import cn.sf80.weixin.manager.pojo.WxUrlMaterial;
import cn.sf80.weixin.spring.pojo.WxPlatformConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private WxMaterialService wxMaterialService;

    @GetMapping("material/material")
    public String material() {
        return "material/material";
    }

    @GetMapping("material/material-add")
    public String materialAdd() {
        return "material/material-add";
    }

    @GetMapping("material/material-add-news")
    public String materialAddNews() {
        return "material/material-add-news";
    }

    @PostMapping("material/material-add")
    @ResponseBody
    public WeixinResult materialUpload(String urlType, String type, MultipartFile media) {
        String appid = wxPlatformConfig.getAppid();
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(appid, wxPlatformConfig.getAppsecret(), 7200);
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
            //存入项目数据库
            WxMaterial wxMaterial = new WxMaterial();
            wxMaterial.setAppid(appid);
            wxMaterial.setClassify(urlType);
            wxMaterial.setType(type);
            Date date = new Date();
            wxMaterial.setCreatedTime(date);
            wxMaterial.setUpdatedTime(date);
            //将微信返回的media信息加到对象中
            JSONObject jsonObject = JSON.parseObject(post);
            wxMaterial.setMediaId(jsonObject.getString("media_id"));
            wxMaterial.setUrl(jsonObject.getString("url"));
            wxMaterialService.saveMaterial(wxMaterial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WeixinResult.build(200, post);
    }

    @GetMapping("material/material-list")
    @ResponseBody
    public WeixinResult materialList(Integer offset, Integer count, WxMaterial wxMaterial) {
        String appid = wxPlatformConfig.getAppid();
        wxMaterial.setAppid(appid);
        List<WxMaterial> wxMaterials = wxMaterialService.getMaterial(wxMaterial, offset, count);
        return WeixinResult.ok(wxMaterials);
    }
}
