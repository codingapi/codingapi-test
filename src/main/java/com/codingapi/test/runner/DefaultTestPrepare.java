package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.config.TestConfig;
import com.codingapi.test.xml.XmlInfo;
import com.codingapi.test.xml.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;

import java.io.File;

/**
 * @author lorne
 * @date 2019/8/6
 * @description
 */
@Slf4j
public class DefaultTestPrepare implements ITestPrepare {


    @Override
    public <T> void prepare(TestMethod testMethod, TestContext testContext) throws Exception {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        TestConfig testConfig = applicationContext.getBean(TestConfig.class);
        String path =  testConfig.getOutPath();
        if(testMethod.prepareData().length>0){
            for(String xmlFile:testMethod.prepareData()){
                String xml = FileUtils.readFileToString(new File(path+"/"+xmlFile));
                XmlInfo<T> xmlInfo = XmlUtils.parser(xml);

                try {
                    IMysqlRunner mysqlRunner = applicationContext.getBean(IMysqlRunner.class);
                    if (mysqlRunner != null) {
                        mysqlRunner.prepare(applicationContext, xmlInfo);
                    }
                }catch (NoSuchBeanDefinitionException e){
                    log.warn("no mysql prepare runner ");
                }

                try {
                    IMongoRunner mongoRunner = applicationContext.getBean(IMongoRunner.class);
                    if (mongoRunner != null) {
                        mongoRunner.prepare(applicationContext, xmlInfo);
                    }
                }catch (NoSuchBeanDefinitionException e){
                    log.warn("no mongo prepare runner ");
                }

            }
        }
    }
}
