package cn.sf80.weixin.common.sql;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class SqlProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlProvider.class);

   public String insert(TableSql sqlCondition) throws Exception{
       SQL sql = new SQL();
       sql.INSERT_INTO(sqlCondition.getTableName());
       sqlCondition.getValues().forEach(sql::VALUES);
       String stringSql = sql.toString();
       LOGGER.info("insert sql:"+stringSql);
       return stringSql;
   }

    public String delete(TableSql sqlCondition) throws Exception{
        SQL sql = new SQL();
        sql.DELETE_FROM(sqlCondition.getTableName());
        groupOrAnd(sqlCondition,sql);
        String stringSql = sql.toString();
        LOGGER.info("delete sql:"+stringSql);
        return stringSql;
    }

    public String select(TableSql tableSql)throws Exception{
        SQL sql = new SQL();
        List<String> selectCols = tableSql.getSelectCols();
        selectCols.forEach(sql::SELECT);
        sql.FROM(tableSql.getTableName());
        groupOrAnd(tableSql,sql);
        Map<String, String> condition = tableSql.getCondition();
        if(condition!=null&&condition.size()>0){
            condition.forEach((k,v)->sql.WHERE(k+v));
        }
        List<String> groups = tableSql.getGroups();
        if(groups!=null) {
            groups.forEach(sql::GROUP_BY);
        }
        List<String> orders = tableSql.getOrders();
        if(orders!=null) {
            orders.forEach(sql::ORDER_BY);
        }
        Long limit = tableSql.getLimit();
        if(limit!=null){
            return sql.toString()+" limit "+limit;
        }
        String stringSql = sql.toString();
        LOGGER.info("select sql:"+stringSql);
        return stringSql;
    }


    private void groupOrAnd(TableSql sqlCondition,SQL sql) throws Exception{
        Map<String, String> and = sqlCondition.getAnd();
        Map<String, String> or = sqlCondition.getOr();
        if(and!=null&&and.size()>0) {
            and.forEach((k, v) -> sql.WHERE(k + "=" + v));
            if(or!=null&&or.size()>0){
                or.forEach((k,v)->{
                    sql.OR();
                    sql.WHERE(k+"="+v);
                });
            }
        }else{
            if(or!=null&&or.size()>0){
                int i = 0;
                for(Map.Entry<String,String> e:or.entrySet()){
                    if(i>0) {
                        sql.OR();
                    }
                    sql.WHERE(e.getKey()+"="+e.getValue());
                    i=i+1;
                }
            }
        }
    }

}
