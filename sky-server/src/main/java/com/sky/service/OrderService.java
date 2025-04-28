package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.*;

public interface OrderService {

    /**
     * 用户下单
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * 历史订单查询
     * @param historyOrdersPageQueryDTO
     * @return
     */
    PageResult historyOrdersPageQuery(HistoryOrdersPageQueryDTO historyOrdersPageQueryDTO);


    /**
     * 查询订单详情
     * @param id 订单id
     * @return
     */
    OrderVO getOrderDetailById(Long id);

    /**
     * 取消订单
     * @param id 订单id
     * @return
     */
    void cancelOrderById(Long id) throws Exception;

    /**
     * 再来一单
     * @param id 订单id
     * @return
     */
    void repeatOrderById(Long id);

    /**
     * 订单搜索
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult ordersPageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO getStatistics();

    /**
     * 商家接单
     * @param ordersConfirmDTO
     * @return
     */
    void confirmOrder(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 商家拒单
     * @param ordersRejectionDTO
     * @return
     */
    void rejectOrder(OrdersRejectionDTO ordersRejectionDTO);
}
