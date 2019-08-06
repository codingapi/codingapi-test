package com.codingapi.test;

import com.codingapi.test.config.TestConfig;
import com.codingapi.test.runner.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@ComponentScan
@Configuration
public class TestConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public TestConfig testConfig(){
        return new TestConfig();
    }


    @Bean
    @ConditionalOnMissingBean
    public ITestPrepare testPrepare(){
        return new DefaultTestPrepare();
    }

    @Bean
    @ConditionalOnMissingBean
    public ITestClear testClear(){
        return new DefaultTestClear();
    }


    @Bean
    @ConditionalOnMissingBean
    public ITestCheck testCheck(){
        return new DefaultTestCheck();
    }

}
