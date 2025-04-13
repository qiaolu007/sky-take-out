package com.sky.service;

import com.sky.dto.DishDTO;
import org.springframework.web.multipart.MultipartFile;

public interface DishService {

    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveDishWithFlavor(DishDTO dishDTO);
}
