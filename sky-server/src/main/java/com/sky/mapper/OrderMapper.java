package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.HistoryOrdersPageQueryDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order
     */
    void insert(Orders order);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 历史订单查询
     * @param historyOrdersPageQueryDTO
     * @return
     */
    Page<OrderVO> historyOrdersPageQuery(HistoryOrdersPageQueryDTO historyOrdersPageQueryDTO);

    /**
     * 查询订单详情
     * @param id 订单id
     * @return
     */
    OrderVO getOrderDetailById(Long id);

    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 订单搜索
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 各个状态的订单数量统计
     * @return
     */
    OrderStatisticsVO getStatistics();

    /**
     * 查询支付后超时订单
     * @param status 待支付
     * @param deadline 当前时间向前15分钟
     */
    @Select("select * from orders where status = #{status} and order_time < #{deadline}")
    List<Orders> getTimeOutOrder(Integer status, LocalDateTime deadline);

    /**
     * 查询派送中的订单(昨日订单）
     * @param status 派送中
     * @param time 昨日24点
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{time}")
    List<Orders> getDeliveryOrder(Integer status, LocalDateTime time);

    /**
     * 查询符合条件的营业额
     * @param map
     * @return
     */
    BigDecimal getTurnover(Map<String, Object> map);

    /**
     * 指定时间订单统计（总订单、其他状态订单数）
     * @param map
     * @return
     */
    Integer getOrderCount(Map<String, Object> map);
}
