package cn.sf80.weixin.db.service.impl;

import cn.sf80.weixin.common.sql.SqlCondition;
import cn.sf80.weixin.db.dao.WxCardinfoDao;
import cn.sf80.weixin.db.pojo.WxCardinfo;
import cn.sf80.weixin.db.service.WxCardinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxCardinfoServiceImpl implements WxCardinfoService {

    @Autowired
    private WxCardinfoDao wxCardinfoDao;

    public void save(WxCardinfo wxCardinfo) {
        SqlCondition sqlCondition = new SqlCondition(wxCardinfo);
        wxCardinfoDao.save(sqlCondition);
    }

    public List<WxCardinfo> getCards(WxCardinfo wxCardinfo,Integer page,Integer rows) {
        SqlCondition sqlCondition = new SqlCondition(wxCardinfo);
        sqlCondition.addOrderCol("updated_time desc");
        List<WxCardinfo> wxCardinfos = wxCardinfoDao.get(sqlCondition);
        return wxCardinfos;
    }

    public void del(WxCardinfo wxCardinfo) {
        SqlCondition sqlCondition = new SqlCondition(wxCardinfo);
        wxCardinfoDao.del(sqlCondition);
    }

    public void update(WxCardinfo wxCardinfo) {
        SqlCondition sqlCondition = new SqlCondition(wxCardinfo);
        sqlCondition.addCondition("card_id",wxCardinfo.getCardId()+"","=",true);
        wxCardinfoDao.update(sqlCondition);
    }

    @Override
    public String getTitleByCardId(String cardId) {
        SqlCondition sqlCondition = new SqlCondition("wx_cardinfo");
        sqlCondition.addCondition("card_id",cardId,"=",true);
        List<WxCardinfo> wxCardinfos = wxCardinfoDao.get(sqlCondition);
        if(wxCardinfos!=null&&wxCardinfos.size()>0) {
            WxCardinfo wxCardinfo = wxCardinfos.get(0);
            return wxCardinfo.getTitle();
        }
        return null;
    }
}
