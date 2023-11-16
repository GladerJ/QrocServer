package com.glader.qrocserver;

import com.glader.qrocserver.mapper.UserUtils;
import com.glader.qrocserver.pojo.User;
import com.glader.qrocserver.util.redis.RedisUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

@SpringBootTest
class QrocServerApplicationTests {
    @Autowired
    private UserUtils tool;
    @Test
    void testInsert(){
        User user = new User();
        user.setUsername("glader244d3");
        user.setPassword("hello1");
        user.setEmail("5@glader.com");
        tool.insert(user);
    }
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


}
