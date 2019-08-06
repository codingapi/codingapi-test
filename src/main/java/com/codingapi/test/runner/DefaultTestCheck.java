package com.codingapi.test.runner;

import com.alibaba.fastjson.JSON;
import com.codingapi.test.annotation.CheckMongoData;
import com.codingapi.test.annotation.CheckMysqlData;
import com.codingapi.test.annotation.Expected;
import com.codingapi.test.annotation.TestMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestContext;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author lorne
 * @date 2019/8/6
 * @description
 */
@Slf4j
public class DefaultTestCheck implements ITestCheck {

    @Override
    public <T> void check(TestMethod testMethod, TestContext testContext) throws Exception {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        for(CheckMysqlData checkMysqlData :testMethod.checkMysqlData()){
            String sql = checkMysqlData.sql();
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            QueryRunner queryRunner = new QueryRunner();
            List<Map<String,Object>> res =  queryRunner.query(dataSource.getConnection(),sql,new MapListHandler());
            log.info("mysql - check=> sql:{},res:{}",sql,res);
            Expected expecteds []= checkMysqlData.expected();
            checkVal(res,expecteds,checkMysqlData.desc());
        }

        for(CheckMongoData checkMongoData :testMethod.checkMongoData()){
            String key = checkMongoData.primaryKey();
            String val = checkMongoData.primaryVal();
            Object pkval = checkMongoData.type().equals(CheckMongoData.Type.Integer)?Integer.parseInt(val):val;
            MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
            Query query = Query.query(
                    Criteria.where(key).is(pkval));
            List<Object> res = null;

            if(StringUtils.isEmpty(checkMongoData.collection())){
                res= mongoTemplate.find(query,checkMongoData.bean());
            }else {
                res= mongoTemplate.find(query,checkMongoData.bean(),checkMongoData.collection());
            }

            log.info("mongo - check=> sql:{},res:{}",query,res);
            Expected expecteds[]= checkMongoData.expected();
            checkVal(res,expecteds,checkMongoData.desc());
        }
    }

    private void checkVal(List res , Expected[] expecteds, String desc) throws IllegalAccessException {
        for(int i=0;i<expecteds.length;i++){
            Expected expected = expecteds[i];
            Object val = res.get(i);
            Map<String,Object>  map = null;
            if(val instanceof Map){
                map = (Map<String,Object>)val;
            }else{
                map =  (Map<String,Object>) JSON.toJSON(val);
            }
            Object mval = map.get(expected.key());
            if(expected.type().equals(Expected.Type.Int)){
                Integer _val = (Integer)mval;
                if(Integer.parseInt(expected.value()) != _val){
                    throw new IllegalAccessException(desc);
                }
            }

            if(expected.type().equals(Expected.Type.String)){
                String _val = (String)mval;
                if(!expected.value().equals(_val)){
                    throw new IllegalAccessException(desc);
                }
            }
        }
    }
}
