package com.sky.service;

import com.sky.vo.SalesTop10ReportVO;

import java.time.LocalDate;

public interface OrderDetailService {
    /**
     * 查询销量排名top10接口
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getTop10(LocalDate begin, LocalDate end);
}
