package cn.sf80.weixin.common.sql;

import cn.sf80.weixin.common.utils.PojoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlCondition implements TableSql {
    private final static Logger LOGGER = LoggerFactory.getLogger(SqlCondition.class);
    private List<String> orders;
    private List<String> groups;
    private String tableName;
    private Long limit;
    private List<String> selectCols;
    private Map<String,String> or;

    private Map<String, String> and;

    private Map<String, String> condition;

    private Map<String, String> values;

    public List<String> getOrders() {
        return orders;
    }

    public List<String> getGroups() {
        return groups;
    }

    @Override
    public Long getLimit() {
        return limit;
    }

    public List<String> getSelectCols() {
        if(selectCols==null||selectCols.size()==0){
            selectCols=new ArrayList<>();
            selectCols.add("*");
        }
        return selectCols;
    }

    public Map<String, String> getAnd() {
        return and;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public Map<String,String> getOr() {
        return or;
    }
    public Map<String,String> addValue(String col,String colValue,boolean quote) {
        if(values==null){
            values=new HashMap<>();
        }
        if(quote) {
            values.put(col, "'" + colValue + "'");
        }else{
            values.put(col,colValue);
        }
        return values;
    }

    public Map<String,String> addOr(String col,String colValue,boolean quote) {
       if(or==null){
           or=new HashMap<>();
       }
       if(quote) {
           or.put(col, "'" + colValue + "'");
       }else{
           or.put(col,colValue);
       }
       return or;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Map<String,String> addAnd(String col, String colValue, boolean quote) {
        if(and ==null){
            and =new HashMap<>();
        }
        if(quote) {
            and.put(col, "'" + colValue + "'");
        }else{
            and.put(col,colValue);

        }
        return and;
    }

    @Override
    public List<String> addGroupCol(String col) throws Exception {
        if(!StringUtils.isEmpty(groups)){
            groups=new ArrayList<>();
        }
        groups.add(col);
        return groups;
    }

    @Override
    public List<String> addOrderCol(String col) {
        if(StringUtils.isEmpty(orders)){
            orders=new ArrayList<>();
        }
        orders.add(col);
        return orders;
    }

    @Override
    public List<String> addSelectCol(String col) throws Exception {
        if(StringUtils.isEmpty(selectCols)){
            selectCols =new ArrayList<>();
        }
        selectCols.add(col);
        return selectCols;
    }

    public Map<String, String> getCondition() {
        return condition;
    }

    public void addCondition(String col, String colValue, String sym,boolean quote) {
        if(quote){
            colValue = "'"+colValue+"'";
        }
        if(condition==null){
            condition=new HashMap<>();
        }
        condition.put(col,sym+colValue);
    }

    public SqlCondition(Object obj){
        try {
            Map<String, String> result = PojoUtils.transObj2Map(obj);
            String[] classes = result.get("class").split("\\.");
            String className = classes[classes.length - 1];
            tableName = transObjField2tableRow(className).substring(1);
            result.remove("class");
            and=new HashMap<>();
            result.forEach((k, v)->{
                if(v!=null) {
                    and.put(transObjField2tableRow(k), v);
                }
            });
            values = and;
            LOGGER.debug("初始化describe:"+ and);
        }catch (Exception e){
            LOGGER.debug("初始化SqlCondition出错了!",e);
        }
    }

    public SqlCondition(String tableName){
        this.tableName=tableName;
    }

    @Override
    public String getTableName() throws Exception{
        return tableName;
    }

    /**
     * 将字符串中的大写字母改成“_+小写”
     * @param className
     * @return
     */
    private static String transObjField2tableRow(String className){
        StringBuilder tableName = new StringBuilder();
        char[] ss = className.toCharArray();
        for(char s:ss){
            if(Character.isLowerCase(s)||Character.isDigit(s))tableName.append(String.valueOf(s));
            else tableName.append("_"+String.valueOf(s).toLowerCase());

        }
        return tableName.toString();
    }
}
