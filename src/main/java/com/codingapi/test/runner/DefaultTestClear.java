package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;

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
        String[] tableNames =  testMethod.clearTableNames();
        if(tableNames!=null) {
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            QueryRunner queryRunner = new QueryRunner();
            for (String tableName : tableNames) {
                String sql = SqlUtils.createClearSql(tableName);
                queryRunner.execute(dataSource.getConnection(), sql);
                log.info("mysql truncate sql ->{}", sql);
            }
        }

        String[] collectionNames =  testMethod.clearCollectionNames();
        if(collectionNames!=null) {
            MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);
            for (String collectionName : collectionNames) {
                mongoTemplate.dropCollection(collectionName);
                log.info("mysql drop collection->{}", collectionName);
            }
        }
    }
}
