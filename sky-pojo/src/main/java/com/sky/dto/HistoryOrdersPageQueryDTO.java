package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HistoryOrdersPageQueryDTO implements Serializable {
    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

    //订单状态
    private Integer status;

    // 用户id
    private Long userId;
}
