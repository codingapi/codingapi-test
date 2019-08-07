package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;

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
        try {
            IMysqlRunner mysqlRunner = applicationContext.getBean(IMysqlRunner.class);
            if (mysqlRunner != null) {
                mysqlRunner.clear(applicationContext, testMethod);
            }
        }catch (NoSuchBeanDefinitionException e){
            log.warn("no mysql clean runner.");
        }

        try {
            IMongoRunner mongoRunner = applicationContext.getBean(IMongoRunner.class);
            if (mongoRunner != null) {
                mongoRunner.clear(applicationContext, testMethod);
            }
        }catch (NoSuchBeanDefinitionException e){
            log.warn("no mongo clean runner.");
        }
    }
}
