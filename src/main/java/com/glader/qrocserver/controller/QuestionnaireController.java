package com.glader.qrocserver.controller;

import com.glader.qrocserver.pojo.Questionnaire;
import com.glader.qrocserver.service.QuestionnaireService;
import com.glader.qrocserver.util.jwt.JwtUtils;
import com.glader.qrocserver.util.result.Result;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
