package com.glader.qrocserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@AllArgsConstructor


public class Questionnaire implements Serializable {
    //问卷ID
    private Long questionnaireId;
    //问卷标题
    private String title;

    //创建时间
    private String createTime;
    //更新修改时间
    private String updateTime;
    //所属用户
    private String username;
    //问题集合
    private ArrayList<Problem> problems;

    //有多少人填过这个问卷
    private Long people;
    public Questionnaire(){
        problems = new ArrayList<>();
    }


    public void addProblem(Problem problem){
        problems.add(problem);
    }
}
