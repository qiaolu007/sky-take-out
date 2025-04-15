package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 插入风味数据
     * @param dishFlavors
     */
    void insertDishFlavor(List<DishFlavor> dishFlavors);

    /**
     * 批量删除风味数据
     * @param ids
     */
    void deleteFlavorByIds(List<Long> ids);

    /**
     * 根据dishId查询风味数据
     * @param dishId
     * @return
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
