package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.web.multipart.MultipartFile;

public interface DishService {

    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveDishWithFlavor(DishDTO dishDTO);

    /**
     *菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);
}
