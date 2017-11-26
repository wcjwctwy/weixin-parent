package cn.sf80.weixin.db.dao;

import cn.sf80.weixin.common.sql.SqlProvider;
import cn.sf80.weixin.common.sql.TableSql;
import cn.sf80.weixin.db.pojo.WxMaterial;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WxMaterialDao {

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void save(TableSql tableSql);

    @DeleteProvider(type = SqlProvider.class,method = "delete")
    void del(TableSql tableSql);

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<WxMaterial> get(TableSql tableSql);

}
