package cn.sf80.weixin.db.dao;

import cn.sf80.weixin.common.sql.SqlProvider;
import cn.sf80.weixin.common.sql.TableSql;
import cn.sf80.weixin.db.pojo.WxCardinfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WxCardinfoDao {

    @InsertProvider(type = SqlProvider.class,method = "insert")
    void save(TableSql tableSql);

    @DeleteProvider(type = SqlProvider.class,method = "delete")
    void del(TableSql tableSql);

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<WxCardinfo> get(TableSql tableSql);

    @UpdateProvider(type = SqlProvider.class,method = "update")
    void update(TableSql tableSql);

}
