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
public @interface CheckMongoData {

    /**
     * 查询匹配的关键字
     * @return 关键字
     */
    String primaryKey();

    /**
     * 关键字的值
     * @return 可以是字符串和Integer整数类型
     */
    String primaryVal();

    /**
     * 值类型
     * @return 分为字符串和整数
     */
    Type type();

    /**
     * 异常提示的信息
     * @return 信息
     */
    String desc() default "";

    /**
     * 查询返回数据的结果类型
     * @return  例如:Order.class
     */
    Class bean();

    /**
     * 检查的集合名词 默认可不行通过bean()获取
     * @return
     */
    String collection() default "";

    /**
     * 校验的结果
     * @return 结果值
     */
    Expected[] expected() default {};

    enum Type{
        Integer,String
    }

}
