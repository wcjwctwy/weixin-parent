package cn.sf80.weixin.db.service.impl;

import cn.sf80.weixin.common.sql.SqlCondition;
import cn.sf80.weixin.db.dao.WxMaterialDao;
import cn.sf80.weixin.db.pojo.WxMaterial;
import cn.sf80.weixin.db.service.WxMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxMaterialServiceImpl implements WxMaterialService {

    @Autowired
    private WxMaterialDao wxMaterialDao;

    @Override
    public void saveMaterial(WxMaterial wxMaterial) {
        SqlCondition sqlCondition = new SqlCondition(wxMaterial);
        wxMaterialDao.save(sqlCondition);

    }

    @Override
    public List<WxMaterial> getMaterial(WxMaterial wxMaterial, Integer page, Integer rows) {
        SqlCondition sqlCondition = new SqlCondition(wxMaterial);
        List<WxMaterial> wxMaterials = wxMaterialDao.get(sqlCondition);
        return wxMaterials;
    }

    @Override
    public void delMaterialById(Long id) {
        SqlCondition sqlCondition = new SqlCondition("wx_material");
        sqlCondition.addCondition("id",id+"","=",false);
        wxMaterialDao.del(sqlCondition);
    }
}
