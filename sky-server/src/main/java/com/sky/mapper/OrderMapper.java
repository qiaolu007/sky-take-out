package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.HistoryOrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.HistoryOrdersVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    Page<HistoryOrdersVO> historyOrdersPageQuery(HistoryOrdersPageQueryDTO historyOrdersPageQueryDTO);

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    HistoryOrdersVO getOrderDetailById(Long id);

    /**
     * 根据id查询订单
     * @param id
     */
    @Select("select * from orders where id=#{id}")
    Orders getById(Long id);

    /**
     * 取消订单（订单id）
     * @param id
     */
    @Update("update orders set status = 6 where id = #{id}")
    void updateOrderStatus(Long id);
}
