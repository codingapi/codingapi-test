package com.codingapi.test.runner;

import com.codingapi.test.annotation.TestMethod;
import org.springframework.test.context.TestContext;

/**
 * @author lorne 2019/8/6
 */
public interface ITestCheck {

    <T> void check(TestMethod testMethod, TestContext testContext) throws Exception;
}
