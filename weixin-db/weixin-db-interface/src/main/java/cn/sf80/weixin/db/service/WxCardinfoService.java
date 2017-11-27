package cn.sf80.weixin.db.service;

import cn.sf80.weixin.db.pojo.WxCardinfo;

import java.util.List;

public interface WxCardinfoService {

    void save(WxCardinfo wxCardinfo);
    List<WxCardinfo> getCards(WxCardinfo wxCardinfo,Integer page,Integer rows);
    void del(WxCardinfo wxCardinfo);
    void update(WxCardinfo wxCardinfo);

}
