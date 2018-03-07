package com.example.demo.mapper;


import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    @Results({
            @Result(property = "sex", column = "user_sex"),
            @Result(property = "nickName", column = "nick_name")
    })
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "sex", column = "user_sex"),
            @Result(property = "nickName", column = "nick_name")
    })
    User getOne(String id);

    @Insert("INSERT INTO users(id ,user_sex,nick_name) VALUES(#{id},#{sex}, #{nickName})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    void insert(User user);

    @Update("UPDATE users SET user_sex=#{sex},nick_name=#{nickName},age=#{age} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);


}
