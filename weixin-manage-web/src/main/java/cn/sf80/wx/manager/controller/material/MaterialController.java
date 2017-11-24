package cn.sf80.wx.manager.controller.material;

import cn.sf80.weixin.common.pojo.WeixinResult;
import cn.sf80.weixin.common.utils.HttpsUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MaterialController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialController.class);
    @GetMapping("material/material")
    public String material(){
        return "material/material";
    }

    @GetMapping("material/material-add")
    public String materialAdd(){
        return "material/material-add";
    }

    @PostMapping("material/material-add")
    @ResponseBody
    public WeixinResult materialUpload(String access_token,String type,Object media){
        Map<String,Object> data = new HashMap<>();
        data.put("access_token",access_token);
        data.put("type",type);
        data.put("media",media);
        String body = JSON.toJSONString(data);
        LOGGER.info("body==>"+body);
        String post = HttpsUtil.httpsRequestToString("https://api.weixin.qq.com/cgi-bin/media/upload", "post", body);
        return WeixinResult.build(200,post);
    }

    @GetMapping("material/material-list")
    @ResponseBody
    public WeixinResult materialList(String access_token,Integer offset,Integer count,String type){
        String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+access_token;
        Map<String,Object> data = new HashMap<>();
        data.put("offset",offset);
        data.put("count",count);
        data.put("type",type);
        String body = JSON.toJSONString(data);
        LOGGER.info("body==>"+body);
        String post = HttpsUtil.httpsRequestToString(url, "POST", body);
        return WeixinResult.build(200,post);
    }
}
