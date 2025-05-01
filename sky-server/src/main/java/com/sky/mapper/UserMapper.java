package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper
public interface UserMapper {
    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入数据
     * @param user
     */
    void insert(User user);

    /**
     * 根据用户id查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where id = #{userId}")
    User getById(Long userId);

    /**
     * 根据日期查询总用户量
     * @param beginTime
     * @return
     */
    @Select("select count(*) from user where create_time < #{beginTime}")
    BigDecimal getTotalUserByTime(LocalDateTime beginTime);

    /**
     * 查询当天时间内新增用户量
     * @param beginStart
     * @param endFinish
     * @return
     */
    @Select("select count(*) from user where create_time between #{beginStart} and #{endFinish}")
    BigDecimal getNewUser(LocalDateTime beginStart, LocalDateTime endFinish);
}
