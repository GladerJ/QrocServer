package com.glader.qrocserver.mapper;

import com.glader.qrocserver.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserUtils {
    @Insert("insert into user values(#{id},#{username},#{password},#{email})")
    public void insert(User user);

    @Select("select * from user where username = #{username}")
    public List<User> selectByUsername(User user);

    @Select("select * from user where email = #{email}")
    public List<User> selectByEmail(User user);

    @Select("select * from user where username = #{username} and password = #{password}")
    public List<User> login(User user);

}
