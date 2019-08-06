package com.codingapi.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @author lorne
 * @date 2019/8/1
 * @description
 */
@Data
@ConfigurationProperties("codingapi.test")
public class TestConfig {

    /**
     * 开启模式
     */
    private Mode mode;

    /**
     * 输出xml的路径
     */
    private String outPath;


    public enum Mode{

        /**
         * 创建并覆盖
         */
        CreateAndCover,

        /**
         * 增量
         */
        Addition,

        /**
         * 不开启
         */
        OFF,

    }

}
