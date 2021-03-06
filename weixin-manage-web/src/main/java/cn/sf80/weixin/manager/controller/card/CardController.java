package cn.sf80.weixin.manager.controller.card;

import cn.sf80.weixin.common.pojo.WeixinResult;
import cn.sf80.weixin.common.utils.HttpsUtil;
import cn.sf80.weixin.db.pojo.WxCardinfo;
import cn.sf80.weixin.db.pojo.WxToken;
import cn.sf80.weixin.db.pojo.card.WxCard;
import cn.sf80.weixin.db.service.WxCardinfoService;
import cn.sf80.weixin.db.service.WxTokenService;
import cn.sf80.weixin.manager.pojo.WxUrlCard;
import cn.sf80.weixin.spring.pojo.WxPlatformConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private WxCardinfoService wxCardinfoService;

    @Autowired
    private WxUrlCard wxUrlCard;

    @Autowired
    private WxTokenService wxTokenService;

    @Autowired
    private WxPlatformConfig wxPlatformConfig;

    @GetMapping("/card/card")
    public String card() {
        return "card/card";
    }

    @GetMapping("/card/card-add")
    public String cardAddPage() {
        return "card/card-add";
    }

    /**
     * 生成卡券，将卡券信息存入数据库
     *
     * @param card_type      卡券的类型
     * @param wxCard         卡券的基本信息
     * @param deal_detail    交易详情 在团购券中有
     * @param least_cost     消费门槛 （代金券）
     * @param reduce_cost    扣减金额 （代金券）
     * @param discount       折扣 （折扣券）
     * @param gift           礼品详情 （礼品券）
     * @param default_detail 优惠详情 （优惠券）
     * @return
     */
    @PostMapping("/card/card-add")
    @ResponseBody
    public WeixinResult cardAdd(String card_type, WxCard wxCard, String deal_detail, Integer least_cost, Integer reduce_cost, Integer discount, String gift, String default_detail) {
        HashMap<String, Object> card = new HashMap<>();
        card.put("card_type", card_type);
        HashMap<String, Object> card_typeObj = new HashMap<>();
        card.put(card_type.toLowerCase(), card_typeObj);
        card_typeObj.put("base_info", wxCard);
        if (!StringUtils.isEmpty("deal_detail")) {
            card_typeObj.put("deal_detail", deal_detail);
        }
        if (!StringUtils.isEmpty(least_cost)) {
            card_typeObj.put("least_cost", least_cost * 100);
        }
        if (!StringUtils.isEmpty(reduce_cost)) {
            card_typeObj.put("reduce_cost", reduce_cost * 100);
        }

        if (!StringUtils.isEmpty(discount)) {
            card_typeObj.put("discount", discount);
        }
        if (!StringUtils.isEmpty(default_detail)) {
            card_typeObj.put("default_detail", default_detail);
        }
        if (!StringUtils.isEmpty(gift)) {
            card_typeObj.put("gift", gift);
        }
        HashMap<String, Object> createCardRequest = new HashMap<>();
        createCardRequest.put("card", card);

        String appid = wxPlatformConfig.getAppid();
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(appid, wxPlatformConfig.getAppsecret(), 7200);
        String url = wxUrlCard.getCreate() + "?access_token=" + interfaceUseToken.getAccessToken();
        String post = HttpsUtil.httpsRequestToString(url, "POST", JSON.toJSONString(createCardRequest));
        LOGGER.info(post);
        //将卡券信息存入数据库
        JSONObject jsonObject = JSON.parseObject(post);
        int errcode = jsonObject.getInteger("errcode");
        if (errcode != 0) {
            throw new RuntimeException(jsonObject.getString("errmsg"));
        }
        String card_id = jsonObject.getString("card_id");
        WxCardinfo wxCardinfo = new WxCardinfo();
        wxCardinfo.setCardId(card_id);
        Date date = new Date();
        wxCardinfo.setCreatedTime(date);
        wxCardinfo.setUpdatedTime(date);
        wxCardinfo.setTitle(wxCard.getTitle());
        wxCardinfo.setBrandName(wxCard.getBrand_name());
        String desc = "卡券类型：" + card_type + "|" + "所属门店：" + wxCard.getLocation_id_list() + "客服电话：" + wxCard.getService_phone();
        wxCardinfo.setDescription(desc);
        wxCardinfoService.save(wxCardinfo);
        return WeixinResult.ok();
    }

    @GetMapping("/card/card-list")
    @ResponseBody
    public WeixinResult getCards(Integer offset, Integer count, WxCardinfo wxCardinfo) {
        List<WxCardinfo> cards = wxCardinfoService.getCards(wxCardinfo, offset, count);
        return WeixinResult.ok(cards);
    }

    @GetMapping("/card/qrcode")
    public String qrcode() {
        return "card/card-qrcode";
    }

    /**
     * 生成微信卡券的二维码，将二维码信息存入数据库
     *
     * @param action_name
     * @param expire_seconds
     * @param card_id
     * @param is_unique_code
     * @return
     */
    @PostMapping("/card/qrcode")
    @ResponseBody
    public WeixinResult getqrcode(String action_name, Integer expire_seconds, String card_id, boolean is_unique_code) {
        if (StringUtils.isEmpty(action_name)) {
            action_name = "QR_CARD";
        }
        Map<String, Object> qrcodeRequest = new HashMap<>();
        qrcodeRequest.put("action_name", action_name);
        qrcodeRequest.put("expire_seconds", expire_seconds);
        Map<String, Object> action_info = new HashMap<>();
        qrcodeRequest.put("action_info", action_info);
        Map<String, Object> card = new HashMap<>();
        action_info.put("card", card);
        card.put("card_id", card_id);
        card.put("is_unique_code", is_unique_code);
        String data = JSON.toJSONString(qrcodeRequest);
        LOGGER.info("create qrcode request :" + data);

        String appid = wxPlatformConfig.getAppid();
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(appid, wxPlatformConfig.getAppsecret(), 7200);
        String url = wxUrlCard.getQrcode() + "?access_token=" + interfaceUseToken.getAccessToken();
        String post = HttpsUtil.httpsRequestToString(url, "POST", JSON.toJSONString(qrcodeRequest));
        LOGGER.info("create qrcode response :" + post);
        JSONObject jsonObject = JSON.parseObject(post);
        int errcode = jsonObject.getInteger("errcode");
        if (errcode != 0) {
            String errmsg = jsonObject.getString("errmsg");
            LOGGER.error(jsonObject.getString("errmsg"));
            return WeixinResult.build(errcode,errmsg);
        }
        //将卡券二维码信息存入数据库
        WxCardinfo wxCardinfo = new WxCardinfo();
        wxCardinfo.setCardId(card_id);
        wxCardinfo.setTicket(jsonObject.getString("ticket"));
        wxCardinfo.setUrl(jsonObject.getString("url"));
        wxCardinfo.setShowQrcodeUrl(jsonObject.getString("show_qrcode_url"));
        wxCardinfo.setUpdatedTime(new Date());
        wxCardinfoService.update(wxCardinfo);
        return WeixinResult.ok();
    }

    /**
     * 查询卡券的详细信息
     *
     * @param card_id 卡券的id
     * @return
     */
    @GetMapping("/card/card-get")
    @ResponseBody
    public WeixinResult getCardInfo(String card_id) {
        Map<String, Object> request = new HashMap<>();
        request.put("card_id", card_id);
        String appid = wxPlatformConfig.getAppid();
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(appid, wxPlatformConfig.getAppsecret(), 7200);
        String url = wxUrlCard.getGet() + "?access_token=" + interfaceUseToken.getAccessToken();
        String data = JSON.toJSONString(request);
        LOGGER.info("card-get: ==>" + data);
        String post = HttpsUtil.httpsRequestToString(url, "POST", data);
        return WeixinResult.ok(JSON.parseObject(post));
    }

    /**
     * 查询核销卡券
     * 1、查看卡券有效性
     * 2、核销卡券
     * @param code
     * @param check_consume
     * @return
     */
    @PostMapping("card/code/consume")
    @ResponseBody
    public WeixinResult codeConsume(String code, @RequestParam(value = "check_consume", defaultValue = "true") boolean check_consume) {

        Map<String, Object> request = new HashMap<>();
        request.put("code", code);
        request.put("check_consume", check_consume);
        String data = JSON.toJSONString(request);
        LOGGER.info("codeConsume request:" + data);

        String appid = wxPlatformConfig.getAppid();
        WxToken interfaceUseToken = wxTokenService.getInterfaceUseToken(appid, wxPlatformConfig.getAppsecret(), 7200);
        String url = wxUrlCard.getGetCode() + "?access_token=" + interfaceUseToken.getAccessToken();
        String post = HttpsUtil.httpsRequestToString(url, "POST", data);
        LOGGER.info("codeConsume response:" + post);

        JSONObject jsonObject = JSON.parseObject(post);
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            Boolean can_consume = jsonObject.getBoolean("can_consume");
            if (!can_consume) {
                return WeixinResult.build(203, jsonObject.getString("user_card_status"));
            }
        } else {
            return WeixinResult.build(errcode, jsonObject.getString("errmsg"));
        }

        Map<String, Object> consumeRequest = new HashMap<>();
        consumeRequest.put("code", code);
        String consumeUrl = wxUrlCard.getConsume() + "?access_token=" + interfaceUseToken.getAccessToken();
        String consumeResponse = HttpsUtil.httpsRequestToString(consumeUrl, "POST", JSON.toJSONString(consumeRequest));
        LOGGER.info("consumeResponse:" + consumeResponse);
        JSONObject consumeResponseObject = JSON.parseObject(consumeResponse);
        int consumeErrcode = consumeResponseObject.getInteger("errcode");
        if (consumeErrcode != 0) {
            return WeixinResult.build(consumeErrcode, jsonObject.getString("errmsg"));
        }
        String card_id = consumeResponseObject.getJSONObject("card").getString("card_id");
        String title = wxCardinfoService.getTitleByCardId(card_id);
        consumeResponseObject.put("title",title);
        return WeixinResult.ok(consumeResponseObject);
    }
}
