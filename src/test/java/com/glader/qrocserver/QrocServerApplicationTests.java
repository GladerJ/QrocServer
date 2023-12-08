package com.glader.qrocserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glader.qrocserver.mapper.UserUtils;
import com.glader.qrocserver.pojo.Option;
import com.glader.qrocserver.pojo.Problem;
import com.glader.qrocserver.pojo.Questionnaire;
import com.glader.qrocserver.pojo.User;
import com.glader.qrocserver.util.chatgpt.ChatUtils;
import com.glader.qrocserver.util.json.JsonUtils;
import com.glader.qrocserver.util.jwt.JwtUtils;
import com.glader.qrocserver.util.redis.RedisUtils;
import com.glader.qrocserver.util.result.Result;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class QrocServerApplicationTests {
    @Autowired
    private UserUtils userUtils;
//
//
//    @Test
//    void testRedis(){
//        JedisPool jedisPool = RedisUtils.open("127.0.0.1",6379);
//        Jedis jedis = jedisPool.getResource();
//       // jedis.set("ct","nb",new SetParams().px(60000));
//        System.out.println(jedis.get("ct"));
//        if(jedis != null){
//            jedis.close();
//        }
//    }
//
//    @Test
//    void testLogin(){
//        User user = new User();
//        user.setUsername("lpl");
//        user.setPassword("jiejie");
//        List<User> list = userUtils.login(user);
//        if(list.size() != 0){
//            Map<String,Object> map = new HashMap<>();
//            map.put("username",list.get(0).getUsername());
//            map.put("password",list.get(0).getPassword());
//            String token = JwtUtils.generateJwt(map);
//            System.out.println(token);
//        }
//        //return Result.error("用户名或密码错误!");
//    }
//
//    @Test
//    void testToken(){
//        String s = "eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6ImppZWppZSIsInVzZXJuYW1lIjoibHBsIiwiZXhwIjoxNzAwNTY2MjI5fQ.qXKTzxYbsPW7uhWH0n8-sE2w-zRizktFSZzfGZ3rS2o";
//        System.out.println(JwtUtils.parseJWT(s));
//    }
//
//    @Test
//    void testSave() throws JsonProcessingException {
//        Questionnaire questionnaire = new Questionnaire();
//        questionnaire.setTitle("人工智能如何发展?");
//        for(int i=1;i<=4;i++){
//            Problem problem = new Problem();
//            problem.setNum((long)i);
//            problem.setContent("问题随机" + new Random().nextInt(100));
//            for(int j=1;j<=4;j++){
//                Option option = new Option();
//                option.setOptionNum((char)('A' + j - 1)+"");
//                option.setContent("选项随机" + new Random().nextInt(100));
//                problem.addOption(option);
//            }
//            questionnaire.addProblem(problem);
//        }
//        System.out.println(JsonUtils.objectToJson(questionnaire));
//    }


    @Test
    void testChat() throws JSONException, IOException {
        System.out.println(ChatUtils.chat("请你生成一个关于的调查问卷，只生成单选题或多选题，至少包含5个题目，以JSON形式返回给我，其中title表示问卷标题，people表示目前已经填写问卷的人数，你默认填0就行，这两个相同然后problems表示问题集合，在每个问题中，num表示题号，按照题目顺序依次编号，每个问题中还有一个content表示问题的内容，isMultipleChoice表示是否多选，如果是1，表示是多选，如果是0表示单选，每个问题里面包含一个options集合，表示选项集合，optionNum表示选项号包含一个字母和一个点，从A开始标，最多26个选项，options中的content表示选项的内容，每个option中还有一个count，你默认填0即可。"));
    }






}
