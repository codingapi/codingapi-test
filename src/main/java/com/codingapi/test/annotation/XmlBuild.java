package com.codingapi.test.annotation;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lorne
 * @date 2019/8/1
 * @description xml创建注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@IndexAnnotated
public @interface XmlBuild {

    /**
     * 插入执行指令
     * mongodb下不需要 mysql会自动生成所有的自动的完整sql
     * @return insert 语句
     */
    String initCmd() default "";


    /**
     * 数据库类型 目前支持关系数据库和mongo
     * @return 关系数据库 or mongo
     */
    DBType dbType() default DBType.RELATIONAL;


    /**
     * table或collection名称
     * @return demo
     */
    String name();

    /**
     * 插入语句字段格式类型
     * @return 字段格式类型
     */
    ColType colType() default ColType.UNDERLINE;


    enum ColType {

        /**
         * 驼峰
         * helloWorld
         */
        CAMEL,
        /**
         * hello_world
         * 下划线
         */
        UNDERLINE

    }




}
