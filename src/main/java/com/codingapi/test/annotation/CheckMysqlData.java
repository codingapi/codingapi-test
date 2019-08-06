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
public @interface CheckMysqlData {

    /**
     * 检查的sql 语句 例如 select money form user where id = 10
     * @return sql
     */
    String sql();

    /**
     * 异常提示
     * @return 金额不正确
     */
    String desc() default "";

    /**
     * 校验数据
     * @return 校验书
     */
    Expected[] expected() default {};


}
