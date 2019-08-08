package com.codingapi.test.runner;


import com.codingapi.test.TestConfiguration;
import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultTestPrepare.class)
@ActiveProfiles("test")
public class DefaultTestPrepareTest {

    @Autowired
    private ITestPrepare testPrepare;

    @Mock
    private TestMethod testMethod;

    @Mock
    private TestContext testContext;

    @Mock
    private ApplicationContext applicationContext;

    @Before
    public void before(){
        TestConfig testConfig = new TestConfig();
        testConfig.setOutPath(System.getProperty("user.dir")+"\\xml");
        when(applicationContext.getBean(TestConfig.class)).thenReturn(testConfig);
        when(testContext.getApplicationContext()).thenReturn(applicationContext);
        when(testMethod.prepareData()).thenReturn(new String[]{"demo.xml"});
    }


    @Test
    public void prepare() throws Exception {
        testPrepare.prepare(testMethod,testContext);
    }


}
