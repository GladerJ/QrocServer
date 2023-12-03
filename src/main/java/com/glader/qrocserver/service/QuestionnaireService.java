package com.glader.qrocserver.service;

import com.glader.qrocserver.mapper.QuestionMapper;
import com.glader.qrocserver.pojo.Option;
import com.glader.qrocserver.pojo.Problem;
import com.glader.qrocserver.pojo.Questionnaire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionMapper questionMapper;
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
}
