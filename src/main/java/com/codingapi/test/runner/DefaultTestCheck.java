package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;

/**
 * @author lorne
 * @date 2019/8/6
 *
 */
@Slf4j
public class DefaultTestCheck implements ITestCheck {

    @Override
    public <T> void check(TestMethod testMethod, TestContext testContext) throws Exception {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        try {
            IMysqlRunner mysqlRunner = applicationContext.getBean(IMysqlRunner.class);
            if (mysqlRunner != null) {
                mysqlRunner.check(applicationContext, testMethod);
            }
        }catch (NoSuchBeanDefinitionException e){
            log.warn("no mysql check runner.");
        }

        try {
            IMongoRunner mongoRunner = applicationContext.getBean(IMongoRunner.class);
            if (mongoRunner != null) {
                mongoRunner.check(applicationContext, testMethod);
            }
        }catch (NoSuchBeanDefinitionException e){
            log.warn("no mongo check runner.");
        }
    }


}
