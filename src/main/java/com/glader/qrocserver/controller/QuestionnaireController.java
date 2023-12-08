package com.glader.qrocserver.controller;

import com.glader.qrocserver.pojo.Questionnaire;
import com.glader.qrocserver.pojo.User;
import com.glader.qrocserver.service.QuestionnaireService;
import com.glader.qrocserver.util.chatgpt.ChatUtils;
import com.glader.qrocserver.util.json.JsonUtils;
import com.glader.qrocserver.util.jwt.JwtUtils;
import com.glader.qrocserver.util.result.Result;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionnaireController {
    /**
     * 保存问卷
     */
    @Autowired
    QuestionnaireService questionnaireService;

    @RequestMapping("saveQuestionnaire")
    public Result saveQuestionnaire(@RequestBody Questionnaire questionnaire) {
        String username = questionnaire.getUsername();
        try {
            Claims claims = JwtUtils.parseJWT(username);
            username = (String) claims.get("username");
            questionnaire.setUsername(username);
            questionnaireService.saveQuestionnaire(questionnaire);
            //用全局异常处理器监听，错误直接返回错误
            return Result.success();
        } catch (Exception e) {
            return Result.error("执行失败，请联系管理员！");
        }
    }

    /**
     * 修改更新问卷
     */
    @RequestMapping("updateQuestionnaire")
    public Result updateQuestionnaire(@RequestBody Questionnaire questionnaire) {
        String username = questionnaire.getUsername();
        try {
            Claims claims = JwtUtils.parseJWT(username);
            username = (String) claims.get("username");
            questionnaire.setUsername(username);
            questionnaireService.updateQuestionnaire(questionnaire);
            //用全局异常处理器监听，错误直接返回错误
            return Result.success();
        } catch (Exception e) {
            return Result.error("执行失败，请联系管理员！");
        }
    }


    /**
     * 删除问卷
     */
    @RequestMapping("deleteQuestionnaire")
    public Result deleteQuestionnaire(@RequestBody Questionnaire questionnaire) {
        String username = questionnaire.getUsername();
        try {
            Claims claims = JwtUtils.parseJWT(username);
            username = (String) claims.get("username");
            questionnaire.setUsername(username);
            questionnaireService.deleteQuestionnaire(questionnaire);
            return Result.success();
        } catch (Exception e) {
            return Result.error("执行失败，请联系管理员！");
        }

    }

    /**
     * 查询问卷
     */
    @RequestMapping("searchQuestionnaire")
    public Result searchQuestionnaire(@RequestBody Questionnaire questionnaire){
        Questionnaire res = questionnaireService.searchQuestionnaire(questionnaire);
        if (res == null){
            return Result.error("该问卷不存在!");
        }
        return Result.success(res);
    }

    @RequestMapping("submitQuestionnaire")
    public Result submitQuestionnaire(@RequestBody ArrayList<String> arrayList){
        questionnaireService.addCountForOptions(arrayList);
        return Result.success("提交成功！");
    }

    @RequestMapping("getQuestionnaires")
    public Result getQuestionnaires(@RequestBody User user){
        String username = user.getUsername();
        try {
            Claims claims = JwtUtils.parseJWT(username);
            username = (String) claims.get("username");
            user.setUsername(username);
            return Result.success(questionnaireService.getQuestionnaires(user));
        } catch (Exception e) {
            return Result.error("执行失败，请联系管理员！");
        }

    }

    @RequestMapping("chat")
    public Result chat(@RequestBody Result result){
        String s ="请你生成一个关于"+(String)result.getData()+"的调查问卷，只生成单选题或多选题，至少包含5个题目，以JSON形式返回给我，其中title表示问卷标题，people表示目前已经填写问卷的人数，你默认填0就行，这两个相同然后problems表示问题集合，在每个问题中，num表示题号，按照题目顺序依次编号，每个问题中还有一个content表示问题的内容，isMultipleChoice表示是否多选，如果是1，表示是多选，如果是0表示单选，每个问题里面包含一个options集合，表示选项集合，optionNum表示选项号包含一个字母和一个点，从A开始标，最多26个选项，options中的content表示选项的内容，每个option中还有一个count，你默认填0即可。你只需要输出json字符串即可，不需要输出多余的东西。";
        try {
            String text = ChatUtils.chat(s);
            if(text.startsWith("```json")){
                text = text.substring(7);
            }
            Questionnaire questionnaire = JsonUtils.jsonToQuestionnaire(text);
            return Result.success(questionnaire);
        } catch (IOException e) {
            return Result.error("问卷生成错误，请重试！");
        }
    }

}
