package com.glader.qrocserver.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option implements Serializable {
    //选项ID
    private Long optionId;
    //选项号，如ABCD...
    private String optionNum;
    //选项内容
    private String content;
    //所属问题ID
    private Long problemId;
    //有多少人选过这个选项
    private Long count;

}
