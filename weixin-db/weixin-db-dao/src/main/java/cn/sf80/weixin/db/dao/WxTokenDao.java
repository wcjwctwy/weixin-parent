package cn.sf80.weixin.db.dao;

import cn.sf80.weixin.common.sql.SqlProvider;
import cn.sf80.weixin.common.sql.TableSql;
import cn.sf80.weixin.db.pojo.WxToken;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface WxTokenDao {
    @InsertProvider(type = SqlProvider.class,method = "insert")
    void saveToken(TableSql tableSql);

    @SelectProvider(type = SqlProvider.class,method = "select")
    List<WxToken> getWxToken(TableSql tableSql);
}
