package com.codingapi.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestMethod {

    /**
     * 是否需要导入数据
     * @return false 不需要
     */
    boolean enablePrepare() default true;

    /**
     * 导入数据的xml文件名称
     * @return Demo.xml
     */
    String[] prepareData() default {};

    /**
     * 是否开启检查
     * @return false不开启
     */
    boolean enableCheck() default false;

    /**
     * mysql 检查业务
     * @return mysql
     */
    CheckMysqlData[] checkMysqlData() default {};

    /**
     * mongo 检查业务
     * @return mongo
     */
    CheckMongoData[] checkMongoData() default {};

    /**
     * 是否清理数据
     * @return false 不开启
     */
    boolean enableClear() default true;

}
