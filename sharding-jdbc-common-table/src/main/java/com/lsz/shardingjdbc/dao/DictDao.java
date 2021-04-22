package com.lsz.shardingjdbc.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface DictDao {

    @Insert("insert into t_dict(dict_id, type, code, value) value(#{dictId}, #{type}, #{code}, #{value})")
    int insertDict(@Param("dictId") Long dictId, @Param("type") String type, @Param("code") String code, @Param("value") String value);

    @Delete("delete from t_dict where dict_id = #{dictId}")
    int delete(@Param("dictId") Long dictId);


}
