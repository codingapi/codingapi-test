package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import org.springframework.test.context.TestContext;

/**
 * @author lorne
 * @date 2019/8/6
 * @description
 */
public interface ITestPrepare {

     void prepare(TestMethod testMethod, TestContext testContext) throws Exception;

}
