package com.example.demo.dao.mapper;


import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author chenglong
 * @date 2018.3.19
 */
public interface UserMapper {

    @Select("SELECT * FROM users")
    @Results(id = "userMap", value = {
            @Result(property = "sex", column = "user_sex"),
            @Result(property = "nickName", column = "nick_name")
    })
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @ResultMap("userMap") // 共用id为userMap的@Results
    User getOne(String id);

    @Insert("INSERT INTO users(id ,user_sex,nick_name) VALUES(#{id},#{sex}, #{nickName})")
    @SelectKey(keyProperty = "id", resultType = String.class, before = true, statement = "select replace(uuid(), '-', '') as id from dual")
    void insert(User user);

    @Update("UPDATE users SET user_sex=#{sex},nick_name=#{nickName},age=#{age} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id =#{id}")
    void delete(Long id);


}
