package com.codingapi.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lorne
 * @date 2019/8/6
 * @description 检验的数据都是一行的记录，多行数据检查暂不支持
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Expected {

    /**
     * 校验的字段
     * @return  字段名称
     */
    String key();

    /**
     * 校验值
     * @return 值
     */
    String value() default "";
}
