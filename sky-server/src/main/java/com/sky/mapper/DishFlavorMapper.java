package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

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
}
