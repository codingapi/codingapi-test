package com.codingapi.test.xml;

import com.codingapi.test.annotation.DBType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lorne 2019-08-07
 * @param <T> bean泛型
 */
@Data
public class XmlInfo<T> {

    /**
     * 表或集合 名称
     */
    private String name;

    /**
     * 插入数据指令
     */
    private String initCmd;

    /**
     * 数据库类型
     */
    private DBType dbType;
    /**
     * 类路径
     */
    private String className;

    /**
     * 数据 items
     */
    private List<T> list;

    public XmlInfo() {
        list = new ArrayList<>();
    }
}
