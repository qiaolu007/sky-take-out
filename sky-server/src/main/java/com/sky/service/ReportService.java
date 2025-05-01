package com.sky.service;

import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {

    /**
     * 营业额统计接口
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    TurnoverReportVO getTurnover(LocalDate begin, LocalDate end);

    /**
     * 用户统计接口
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);
}
