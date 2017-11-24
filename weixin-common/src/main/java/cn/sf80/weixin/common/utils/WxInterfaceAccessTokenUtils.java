package cn.sf80.weixin.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxInterfaceAccessTokenUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxInterfaceAccessTokenUtils.class);
    //https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx8e2c0babe1547b09&secret=054e5deec01991933740a64a5a722175
    public static JSONObject getInterfaceAccessToken(String appid,String secret){
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
                +"appid="+appid
                +"&secret="+secret;
        String response = HttpsUtil.httpsRequestToString(url, "GET", null);
        JSONObject jsonObject = JSON.parseObject(response);
        LOGGER.info("getInterfaceAccessToken response"+response);
        return jsonObject;
    }
}
