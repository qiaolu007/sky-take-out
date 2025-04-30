package com.sky.task;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ProcessScheduledTasks {

    @Autowired
    private OrderMapper orderMapper;

    // 处理超时订单(支付超时15分钟)
    @Scheduled(cron = "0 * * * * ? ")
//    @Scheduled(cron = "0/5 * * * * ? ")
    public void processTimeoutOrder(){
        log.info("处理支付超时订单：{}", new Date());
        LocalDateTime deadline = LocalDateTime.now().plusMinutes(-15);
        List<Orders> ordersList = orderMapper.getTimeOutOrder(Orders.PENDING_PAYMENT, deadline);

        if (ordersList != null  && !ordersList.isEmpty()) {
            for (Orders orders : ordersList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单支付超时15分钟");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理派送中未点完成的订单
     */
    @Scheduled(cron = "0 0 1 * * ? ")
//    @Scheduled(cron = "1/5 * * * * ? ")
    public void processDeliveryOrder(){
        log.info("处理派送中未点完成的订单：{}", new Date());
        LocalDateTime time = LocalDateTime.now().plusHours(-1);
        List<Orders> ordersList = orderMapper.getDeliveryOrder(Orders.DELIVERY_IN_PROGRESS, time);
        if (ordersList != null && !ordersList.isEmpty()) {
            ordersList.forEach(orders -> {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            });
        }
    }
}
