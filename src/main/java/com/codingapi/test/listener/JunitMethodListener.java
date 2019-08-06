package com.codingapi.test.listener;


import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.runner.ITestCheck;
import com.codingapi.test.runner.ITestClear;
import com.codingapi.test.runner.ITestPrepare;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;

/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@Slf4j
public class JunitMethodListener extends AbstractTestExecutionListener {


    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        ApplicationContext applicationContext =  testContext.getApplicationContext();
        Method jdkMethod = testContext.getTestMethod();
        if (jdkMethod == null) {
            return;
        }

        TestMethod testMethod = jdkMethod.getAnnotation(TestMethod.class);
        if (testMethod == null) {
            return;
        }
        if (testMethod.enablePrepare()) {
            ITestPrepare testPrepare =  applicationContext.getBean(ITestPrepare.class);
            testPrepare.prepare(testMethod,testContext);
        }
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        ApplicationContext applicationContext =  testContext.getApplicationContext();
        boolean hasException = (testContext.getTestException() != null) ? true : false;
        Method jdkMethod = testContext.getTestMethod();
        if (jdkMethod == null) {
            return;
        }
        TestMethod testMethod = jdkMethod.getAnnotation(TestMethod.class);
        if (testMethod == null) {
            return;
        }

        if(testMethod.enableCheck()&&!hasException){
            ITestCheck testCheck =  applicationContext.getBean(ITestCheck.class);
            testCheck.check(testMethod,testContext);
        }

        if (testMethod.enableClear()) {
            ITestClear testClear =  applicationContext.getBean(ITestClear.class);
            testClear.clean(testMethod,testContext);
        }
    }

}
