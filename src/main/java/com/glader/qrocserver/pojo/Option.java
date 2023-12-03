package com.glader.qrocserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    //选项ID
    private Integer optionId;
    //选项号，如ABCD...
    private String optionNum;
    //选项内容
    private String content;
    //所属问题ID
    private Integer problemId;

}
