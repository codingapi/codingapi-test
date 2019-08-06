package com.codingapi.test.runner;

import com.codingapi.test.annotation.DBType;
import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.config.TestConfig;
import com.codingapi.test.xml.XmlInfo;
import com.codingapi.test.xml.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestContext;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.io.File;

/**
 * @author lorne
 * @date 2019/8/6
 * @description
 */
@Slf4j
public class DefaultTestClear implements ITestClear {

    @Override
    public <T> void clean(TestMethod testMethod, TestContext testContext) throws Exception {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        TestConfig testConfig = applicationContext.getBean(TestConfig.class);
        String path =  testConfig.getOutPath();
        if(testMethod.prepareData().length>0){
            for(String xmlFile:testMethod.prepareData()){
                String xml = FileUtils.readFileToString(new File(path+"/"+xmlFile));
                XmlInfo<T> xmlInfo = XmlUtils.parser(xml);

                if(xmlInfo.getDbType().equals(DBType.Mysql)&& !StringUtils.isEmpty(xmlInfo.getClearCmd())){
                    DataSource dataSource = applicationContext.getBean(DataSource.class);
                    QueryRunner queryRunner = new QueryRunner();
                    queryRunner.execute(dataSource.getConnection(),xmlInfo.getClearCmd());
                    log.info("mysql clear->{}",xmlInfo.getClearCmd());

                }

                if(xmlInfo.getDbType().equals(DBType.Mongo)){
                    MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
                    mongoTemplate.dropCollection(xmlInfo.getName());

                    log.info("mongo clear->{}",xmlInfo.getName());
                }

            }
        }
    }
}
