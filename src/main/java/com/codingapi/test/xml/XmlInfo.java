package com.codingapi.test.xml;

import com.codingapi.test.annotation.DBType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class XmlInfo<T> {

    /**
     * 表或集合 名称
     */
    private String name;
    /**
     * 文件所在路径
     */
    private String path;
    /**
     * 插入数据指令
     */
    private String initCmd;

    /**
     * 清理数据指令
     */
    private String clearCmd;

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
