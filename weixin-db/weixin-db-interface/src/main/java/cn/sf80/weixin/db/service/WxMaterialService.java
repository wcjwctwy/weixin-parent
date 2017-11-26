package cn.sf80.weixin.db.service;

import cn.sf80.weixin.db.pojo.WxMaterial;

import java.util.List;

public interface WxMaterialService {

    void saveMaterial(WxMaterial wxMaterial);
    void delMaterialById(Long Id);
    List<WxMaterial> getMaterial(WxMaterial wxMaterial,Integer page,Integer rows);
}
