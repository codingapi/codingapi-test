package com.codingapi.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@Data
@ConfigurationProperties("test")
public class TestConfig {

    /**
     * 是否开启
     */
    private boolean coverXml;

    /**
     * 输出xml的路径
     */
    private String outPath;



}
