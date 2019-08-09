package com.codingapi.test.utils;

import com.alibaba.fastjson.JSONObject;
import com.codingapi.test.annotation.XmlBuild;
import com.google.common.base.CaseFormat;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lorne 2019-08-07
 */
public class SqlUtils {

    public static SqlParam parser(String initCmd, Object object) {
        Map<String,Object> map = (Map) JSONObject.toJSON(object);
        List<Object> params = new ArrayList<>();
        SqlParam sqlParam = new SqlParam();

        String regex = "\\#\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(initCmd);
        while (matcher.find()){
            String key = matcher.group(1);
            Object val = map.get(key);
            params.add(val);
            initCmd = initCmd.replace("#{" + key + "}", "?");
        }
        sqlParam.setSql(initCmd);
        sqlParam.setParams(params.toArray());
        return sqlParam;
    }

    public static String createInsertSql(String name, XmlBuild.ColType colType, Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(name);
        sb.append("(");
        Field[] fields =  clazz.getDeclaredFields();
        int filedLength = fields.length;
        for (int i=0;i<filedLength;i++){
            Field field = fields[i];
            if(i==filedLength-1){
                sb.append(getColumn(field.getName(),colType));
            }else {
                sb.append(getColumn(field.getName(),colType)+",");
            }
        }
        sb.append(")");
        sb.append(" values(");
        for (int i=0;i<filedLength;i++){
            Field field = fields[i];
            if(i==filedLength-1){
                sb.append("#{"+field.getName()+"}");
            }else {
                sb.append("#{"+field.getName()+"},");
            }
        }
        sb.append(")");
        return sb.toString();
    }


    private static String getColumn(String name, XmlBuild.ColType colType){
        if(colType.equals(XmlBuild.ColType.UNDERLINE)){
            return  CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
        }
        if(colType.equals(XmlBuild.ColType.CAMEL)){
            return  CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name);
        }
        return null;
    }


    public static String createClearSql(String name) {
        return "truncate "+name;
    }

    @Data
    public static class SqlParam{
        String sql;
        Object[] params;
    }

}
