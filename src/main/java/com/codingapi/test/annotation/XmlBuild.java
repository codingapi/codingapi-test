package com.codingapi.test.annotation;

import org.atteo.classindex.IndexAnnotated;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lorne
 * @date 2019/8/1
 * @description
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
     * 清理数据指令
     * mongodb下不需要 mysql会使用 truncate @name()
     * @return truncate table or drop table
     */
    String clearCmd() default "";

    /**
     * 数据库类型 目前支持mysql和mongo
     * @return mysql or mongo
     */
    DBType dbType() default DBType.Mysql;


    /**
     * table或collection名称
     * @return demo
     */
    String name();




}
