package cn.sf80.weixin.db.service.impl;

import cn.sf80.weixin.common.sql.SqlCondition;
import cn.sf80.weixin.common.utils.WxInterfaceAccessTokenUtils;
import cn.sf80.weixin.db.dao.WxTokenDao;
import cn.sf80.weixin.db.pojo.WxToken;
import cn.sf80.weixin.db.service.WxTokenService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WxTokenServiceImpl implements WxTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxTokenServiceImpl.class);

    @Autowired
    private WxTokenDao wxTokenDao;


    public void saveToken(WxToken wxToken) {
        SqlCondition sqlCondition = new SqlCondition(wxToken);
        wxTokenDao.saveToken(sqlCondition);
    }




    public WxToken getInterfaceUseToken(String appid,String secret,Integer expires) {
        //先查询数据库是否有token
        WxToken wxToken = new WxToken();
        //组装查询条件
        wxToken.setAppid(appid);
        Date date = DateUtils.addSeconds(new Date(), expires);
        wxToken.setExpTime(date);
        wxToken.setTokenType(INTERFACEUSE);
        //查询数据库
        SqlCondition sqlCondition = new SqlCondition("wx_token");
        sqlCondition.addCondition("appid",wxToken.getAppid(),"=",true);
        String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        sqlCondition.addCondition("exp_time",format,">",true);
        sqlCondition.addCondition("token_type",wxToken.getTokenType(),"=",true);
        List<WxToken> wxTokens = wxTokenDao.getWxToken(sqlCondition);
        if(wxTokens==null||wxTokens.size()==0){
            //没有则想平台获取token
            JSONObject interfaceAccessToken = WxInterfaceAccessTokenUtils.getInterfaceAccessToken(appid, secret);
            String access_token = interfaceAccessToken.getString("access_token");
            wxToken.setAccessToken(access_token);
            saveToken(wxToken);
            return wxToken;
        }else{
            return wxTokens.get(0);
        }

    }
}
