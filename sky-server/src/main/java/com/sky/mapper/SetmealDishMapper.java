package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 批量查询套餐id
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishids(List<Long> ids);

    /**
     * 插入套餐包含的菜品
     * @param setmealDish
     */
    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) values (#{setmealId}, #{dishId}, #{name}, #{price}, #{copies})")
    void insert(SetmealDish setmealDish);

    /**
     * 批量删除套餐内的菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据套餐id查询关联菜品
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
