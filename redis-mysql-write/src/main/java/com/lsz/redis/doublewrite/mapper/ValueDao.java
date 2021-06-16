package com.lsz.redis.doublewrite.mapper;

import com.lsz.redis.doublewrite.entity.ValueEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ValueDao {


    @Update("update t_write set value = #{value} where id = #{id}")
    int updateValue(@Param("id") Long id, @Param("value") Long value);

    @Update("update t_write set value = #{value} where id = #{id} and value = #{preValue}")
    int casUpdateValue(@Param("id") Long id, @Param("value") Long value, @Param("preValue") Long preValue);


    @Select("select * from t_write where id = #{id}")
    ValueEntity selectValueById(@Param("id") Long id);

}
