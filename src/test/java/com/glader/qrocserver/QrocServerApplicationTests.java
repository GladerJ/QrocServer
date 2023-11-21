package com.glader.qrocserver;

import com.glader.qrocserver.mapper.UserUtils;
import com.glader.qrocserver.pojo.User;
import com.glader.qrocserver.util.jwt.JwtUtils;
import com.glader.qrocserver.util.redis.RedisUtils;
import com.glader.qrocserver.util.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class QrocServerApplicationTests {
    @Autowired
    private UserUtils userUtils;


    @Test
    void testRedis(){
        JedisPool jedisPool = RedisUtils.open("127.0.0.1",6379);
        Jedis jedis = jedisPool.getResource();
       // jedis.set("ct","nb",new SetParams().px(60000));
        System.out.println(jedis.get("ct"));
        if(jedis != null){
            jedis.close();
        }
    }

    @Test
    void testLogin(){
        User user = new User();
        user.setUsername("lpl");
        user.setPassword("jiejie");
        List<User> list = userUtils.login(user);
        if(list.size() != 0){
            Map<String,Object> map = new HashMap<>();
            map.put("username",list.get(0).getUsername());
            map.put("password",list.get(0).getPassword());
            String token = JwtUtils.generateJwt(map);
            System.out.println(token);
        }
        //return Result.error("用户名或密码错误!");
    }

    @Test
    void testToken(){
        String s = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6ImppZWppZSIsInVzZXJuYW1lIjoibHBsIiwiZXhwIjoxNzAwNTY2MjI5fQ.qXKTzxYbsPW7uhWH0n8-sE2w-zRizktFSZzfGZ3rS2o";
        System.out.println(JwtUtils.parseJWT(s));
    }


}
