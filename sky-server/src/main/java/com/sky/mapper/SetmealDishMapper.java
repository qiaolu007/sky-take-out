package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 批量查询套餐id
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishids(List<Long> ids);
}
