package com.brilliant.lf.bean;

import lombok.Data;

/**
 * 端口类
 *
 * @Author zxl on 2019/8/12
 */
@Data
public class Link {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 端口号
     */
    private String port;

    /**
     * 对应主题
     */
    private String topic;

    /**
     * 订阅主题
     */
    private String subscribe;

    /**
     * 数据格式
     */
    private String dataflag;
}