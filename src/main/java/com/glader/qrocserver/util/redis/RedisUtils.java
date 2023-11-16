package com.glader.qrocserver.util.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
    //定义连接池对象
    private static JedisPool pool = null;
    //创建连接池
    public static JedisPool open(String host,int port){
        if(pool == null){
            //使用JedisPool
            JedisPoolConfig config = new JedisPoolConfig();
            //最大的Jedis实例数，默认8
            config.setMaxTotal(10);
            //最大的空闲实例数
            config.setMaxIdle(3);
            //提前检查Jedis对象，true可用
            config.setTestOnBorrow(true);
            //创建Jedis连接池,连接超时时间6s
            pool = new JedisPool(config,host,port,6000);
            //带密码
            //pool = new JedisPool(config,host,port,6 * 1000,"123456");
        }
        return pool;
    }
    public static void close(){
        if(pool != null){
            pool.close();
        }
    }
}
