package com.codingapi.test.runner;

import com.codingapi.test.annotation.CheckMongoData;
import com.codingapi.test.annotation.Expected;
import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.xml.XmlInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author lorne
 * @date 2019/8/7
 * @description
 */
@Slf4j
public class DefaultMongoRunner implements IMongoRunner {


    @Override
    public <T> void prepare(ApplicationContext applicationContext, XmlInfo xmlInfo){
        MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
        for(Object object : xmlInfo.getList()) {
            Object res = mongoTemplate.save(object,xmlInfo.getName());
            log.info("mongodb->,rows:{}",res);
        }
    }

    @Override
    public void clear(ApplicationContext applicationContext,TestMethod testMethod) throws Exception{
        String[] collectionNames =  testMethod.clearCollectionNames();
        if(collectionNames!=null) {
            MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
            for (String collectionName : collectionNames) {
                mongoTemplate.dropCollection(collectionName);
                log.info("mysql drop collection->{}", collectionName);
            }
        }
    }

    @Override
    public void check(ApplicationContext applicationContext, TestMethod testMethod) throws Exception{
        for(CheckMongoData checkMongoData :testMethod.checkMongoData()){
            String key = checkMongoData.primaryKey();
            String val = checkMongoData.primaryVal();
            Object pkval = val;
            if(checkMongoData.type().equals(CheckMongoData.Type.INTEGER)){
                pkval = Integer.parseInt(val);
            }

            if(checkMongoData.type().equals(CheckMongoData.Type.LONG)){
                pkval = Long.parseLong(val);
            }

            MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
            Query query = Query.query(Criteria.where(key).is(pkval));
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
}
