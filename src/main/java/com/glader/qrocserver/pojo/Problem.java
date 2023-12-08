package com.glader.qrocserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
@Data
@AllArgsConstructor
public class Problem implements Serializable {
    //问题ID
    private Long problemId;
    //所属问卷ID
    private Long questionnaireId;
    //问题序号
    private Long num;

    //问题内容
    private String content;
    //是否是多选 1是，0否
    private int isMultipleChoice;
    //选项集合
    public Problem(){
        options = new ArrayList<>();
    }
    private ArrayList<Option> options;
    public void addOption(Option option){
        options.add(option);
    }
}
