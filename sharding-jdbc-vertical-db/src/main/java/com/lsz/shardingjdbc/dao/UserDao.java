package com.lsz.shardingjdbc.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface UserDao {

    @Insert("insert into t_user(user_id, fullname) values(#{userId}, #{fullname})")
    int insertUser(@Param("userId") Long userId, @Param("fullname") String fullname);


    @Select("<script>" +
            "select * from t_user t where t.user_id in " +
            "<foreach collection='userIds' open='(' separator=',' close=')' item='id'>" +
            "#{id} " +
            "</foreach>" +
            "</script>")
    List<Map> selectUserByIds(@Param("userIds") List<Long> ids);



}
