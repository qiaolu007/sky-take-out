package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    public ReportServiceImpl(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    /**
     * 根据时间区间统计营业额接口
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    @Override
    public TurnoverReportVO getTurnover( LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        while(!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }

        List<BigDecimal> turnoverStatisticsList = new ArrayList<>();
        dateList.forEach(localDate -> {
            LocalDateTime beginStart = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endFinish = LocalDateTime.of(localDate, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("status", Orders.COMPLETED);
            map.put("begin",beginStart);
            map.put("end", endFinish);

            BigDecimal turnoverStatistics = orderMapper.getTurnover(map); // 查询符合条件（时间。状态）的总营业额
            turnoverStatistics = turnoverStatistics == null ? BigDecimal.valueOf(0.0) : turnoverStatistics; // 当天营业额为空，则设为0
            turnoverStatisticsList.add(turnoverStatistics);
        });

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ",")) // org.apache.commons.lang.StringUtils;
                .turnoverList(StringUtils.join(turnoverStatisticsList, ","))
                .build();
    }

    /**
     * 用户统计接口
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    @Override
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        BigDecimal totalUser = userMapper.getTotalUserByTime(beginTime); // 开始日期0点之前总的用户人数

        List<LocalDate> dateList = new ArrayList<>();
        while(!begin.isAfter(end)) {
            dateList.add(begin);
            begin = begin.plusDays(1);
        }


        List<BigDecimal> totalUserList = new ArrayList<>();
        List<BigDecimal> newUserList = new ArrayList<>();

        for (LocalDate localDate : dateList) {
            LocalDateTime beginStart = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endFinish = LocalDateTime.of(localDate, LocalTime.MAX);


            BigDecimal newUser = userMapper.getNewUser(beginStart, endFinish); // 查询符合条件（时间。状态）的总营业额
            newUser = newUser == null ? BigDecimal.valueOf(0.0) : newUser; // 当天营业额为空，则设为0
            totalUser = totalUser.add(newUser);

            newUserList.add(newUser);
            totalUserList.add(totalUser);
        }


        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .build();
    }
}
