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
            return Result.error("登录失效，请重新登录！");
        }

    }

    /**
     * 删除问卷
     */
    @RequestMapping("deleteQuestionnaire")
    public Result deleteQuestionnaire(@RequestBody Questionnaire questionnaire) {
        questionnaireService.deleteQuestionnaire(questionnaire);
        return Result.success();
    }
}
