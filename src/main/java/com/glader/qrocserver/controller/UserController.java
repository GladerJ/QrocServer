package com.glader.qrocserver.controller;

import com.glader.qrocserver.mapper.UserUtils;
import com.glader.qrocserver.pojo.Mail;
import com.glader.qrocserver.pojo.User;
import com.glader.qrocserver.util.jwt.JwtUtils;
import com.glader.qrocserver.util.mail.MailUtils;
import com.glader.qrocserver.util.redis.RedisUtils;
import com.glader.qrocserver.util.result.Result;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//有些接口需要加锁，后期再加
@RestController
public class UserController {

    @Autowired
    private UserUtils userUtils;

    /**
     * 验证密钥是否有效
     */

    @RequestMapping("verifyToken")
    public Result verifyToken(@RequestBody Result result){
        try{
            Claims claims = JwtUtils.parseJWT((String)result.getData());
            return Result.success();
        }catch (Exception e){
            return Result.error("密钥无效!");
        }

    }


    /**
     * 用于查询该用户账号密码是否正确
     */


    @RequestMapping("login")
    public Result login(@RequestBody User user){
        List<User> list = userUtils.login(user);
        if(list.size() != 0){
            Map<String,Object> map = new HashMap<>();
            map.put("username",list.get(0).getUsername());
            map.put("password",list.get(0).getPassword());
            String token = JwtUtils.generateJwt(map);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误!");
    }

    /**
     * 用于验证该用户名是否已经存在
     */

    @RequestMapping("existUsername")
    public Result existUsername(@RequestBody User user){
        synchronized (userUtils){
            if(userUtils.selectByUsername(user).size() == 0){
                return Result.success();
            }
            return Result.error("该用户已存在!");
        }
    }

    /**
     * 用于验证该邮箱是否已经存在
     */

    @RequestMapping("existEmail")
    public Result existEmail(@RequestBody User user){
        synchronized (userUtils){
            if(userUtils.selectByEmail(user).size() == 0){
                return Result.success();
            }
            return Result.error("该邮箱已存在!");
        }
    }




    /**
     * 用于发送验证码
     *
     * @param mail
     * @return
     */
    @RequestMapping("sendVerifyCode")
    public Result sendVerifyCode(@RequestBody Mail mail) {
        System.out.println(mail);
        String code = createCode();
        String status = MailUtils.sendMail(mail.getEmail(), code);
        JedisPool jedisPool = RedisUtils.open("127.0.0.1", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set(mail.getEmail(), code, new SetParams().px(120000));
        if (jedis != null) {
            jedis.close();
        }
        if ("success".equals(status)) {
            return Result.success();
        } else {
            return Result.error("邮件发送失败!");
        }
    }

    /**
     * 用于验证验证码是否正确
     *
     * @param mail
     * @return
     */
    @RequestMapping("verify")
    public Result verify(@RequestBody Mail mail) {
        Jedis jedis = RedisUtils.open("127.0.0.1", 6379).getResource();
        String realCode = jedis.get(mail.getEmail());
        if (realCode == null) {
            return Result.error("验证码不存在!");
        }
        if (!realCode.equals(mail.getCode())) {
            return Result.error("验证码错误!");
        }
        return Result.success();
    }


    /**
     * 用于注册新用户
     *
     * @param user
     * @return
     */
    @RequestMapping("register")
    public Result register(@RequestBody User user) {
        try {
            userUtils.insert(user);
            JedisPool jedisPool = RedisUtils.open("127.0.0.1", 6379);
            Jedis jedis = jedisPool.getResource();
            jedis.del(user.getEmail());
            return Result.success();
        } catch (Exception e) {
            return Result.error("用户创建失败!");
        }
    }

    private String createCode() {
        Random r = new Random();
        String s = "";
        for (int i = 0; i < 6; i++) {
            s += r.nextInt(10);
        }
        return s;
    }
}
