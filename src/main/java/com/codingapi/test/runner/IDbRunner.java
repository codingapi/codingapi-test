package com.codingapi.test.runner;

import com.alibaba.fastjson.JSON;
import com.codingapi.test.annotation.Expected;
import com.codingapi.test.annotation.TestMethod;
import com.codingapi.test.xml.XmlInfo;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author lorne
 * @date 2019/8/7
 * @description
 */
public interface IDbRunner {

    <T> void prepare(ApplicationContext applicationContext, XmlInfo xmlInfo) throws Exception;

    void clear(ApplicationContext applicationContext,TestMethod testMethod) throws Exception;


    void check(ApplicationContext applicationContext, TestMethod testMethod) throws Exception;

    default void checkVal(List res , Expected[] expecteds, String desc) throws IllegalAccessException {
        for(int i=0;i<expecteds.length;i++){
            Expected expected = expecteds[i];
            Object val = res.get(i);
            Map<String,Object> map = null;
            if(val instanceof Map){
                map = (Map<String,Object>)val;
            }else{
                map =  (Map<String,Object>) JSON.toJSON(val);
            }
            Object mval = map.get(expected.key());
            if(!mval.toString().equals(expected.value())){
                throw new IllegalAccessException(desc);
            }
        }
    }

}
