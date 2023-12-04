package com.glader.qrocserver.service;

import com.glader.qrocserver.mapper.QuestionMapper;
import com.glader.qrocserver.pojo.Option;
import com.glader.qrocserver.pojo.Problem;
import com.glader.qrocserver.pojo.Questionnaire;
import com.glader.qrocserver.util.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionMapper questionMapper;
    //保存问卷
    public void saveQuestionnaire(Questionnaire questionnaire){
        questionMapper.insertQuestionnaire(questionnaire);
        ArrayList<Problem> problems = questionnaire.getProblems();
        problems.stream().forEach(problem -> {
            problem.setQuestionnaireId(questionnaire.getQuestionnaireId());
            questionMapper.insertProblem(problem);
            ArrayList<Option> options = problem.getOptions();
            options.stream().forEach(option->{
                option.setProblemId(problem.getProblemId());
                questionMapper.insertOption(option);
            });
        });
    }

    //删除问卷
    public void deleteQuestionnaire(Questionnaire questionnaire){
        questionMapper.deleteOptionByQuestionnaireId(questionnaire);
        questionMapper.deleteProblemByQuestionnaireId(questionnaire);
        questionMapper.deleteQuestionByQuestionnaireId(questionnaire);
    }

    //修改问卷
    public void updateQuestionnaire(Questionnaire questionnaire){
        deleteQuestionnaire(questionnaire);
        saveQuestionnaire(questionnaire);
    }
    //查询问卷
    public Questionnaire searchQuestionnaire(Questionnaire questionnaire){
        List<Questionnaire> questionnaires = questionMapper.selectQuestionnaire(questionnaire);
        if(questionnaires.size() == 0) return null;
        Questionnaire res = questionnaires.get(0);
        System.out.println(res);
        List<Problem> problems = questionMapper.selectProblem(questionnaire);
        problems.stream().forEach(problem -> {
            List<Option> options = questionMapper.selectOption(problem);
            for(Option option : options){
                problem.addOption(option);
            }
            res.addProblem(problem);
        });
        return res;
    }
}
