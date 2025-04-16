package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @Override
    @Transactional
    public void saveSetmeal(SetmealDTO setmealDTO) {
        // 先插入套餐基本信息setmeal表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);

        // 再插入套餐包含的菜品setmeal_dish表
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish ->
                setmealDish.setSetmealId(setmeal.getId())
                );
        }
        // 保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> pages = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(pages.getTotal(), pages.getResult());
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public void deleteSetmeal(List<Long> ids) {
        //判断是否在售
        ids.forEach(id -> {
            if (setmealMapper.getById(id).getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        //删除套餐基本信息setmeal
       setmealMapper.deleteBatch(ids);

       //删除套餐_菜品表setmeal_dish
        setmealDishMapper.deleteBatch(ids);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    @Transactional
    public SetmealVO getById(Long id) {
        // 先查询套餐基本套餐 setmeal
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);

        // 再查询套餐关联的菜品 setmeal_dish
        List<SetmealDish> setmealDishs = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishs);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @Override
    @Transactional
    public void updateWithSetmealDish(SetmealDTO setmealDTO) {
        // 修改套餐基本内容setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        // 删除原来套餐关联菜品
        setmealDishMapper.deleteBatch(Collections.singletonList(setmealDTO.getId()));
        // 新增套餐现在关联菜品
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && setmealDishes.size() > 0) {
            setmealDishes.forEach(setmealDish ->
                setmealDish.setSetmealId(setmeal.getId())
            );
        }
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 套餐起售/限售状态
     * @param status
     * @param id
     * @return
     */
    @Override
    public void updateStatus(Integer status, Long id) {
        //select a.* from dish a left join setmeal_dish b on a.id = b.dish_id where b.setmeal_id = ?
        List<Dish> dishList = dishMapper.getBySetmealId(id);
        if(dishList != null && dishList.size() > 0){
            dishList.forEach(dish -> {
                if(StatusConstant.DISABLE == dish.getStatus()){
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            });
        }
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
        setmealMapper.update(setmeal);
    }
}
