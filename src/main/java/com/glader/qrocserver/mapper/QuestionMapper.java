package com.glader.qrocserver.mapper;

import com.glader.qrocserver.pojo.Option;
import com.glader.qrocserver.pojo.Problem;
import com.glader.qrocserver.pojo.Questionnaire;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface QuestionMapper {
    @Insert("insert into questionnaire(title, create_time, update_time, username) values (#{title},#{createTime},#{updateTime},#{username})")
    @Options(useGeneratedKeys = true,keyProperty = "questionnaireId")
    public void insertQuestionnaire(Questionnaire questionnaire);

    @Insert("insert into problem(questionnaire_id, num, content, is_multiple_choice) values (#{questionnaireId},#{num},#{content},#{isMultipleChoice})")
    @Options(useGeneratedKeys = true,keyProperty = "problemId")
    public void insertProblem(Problem problem);

    @Insert("insert into my_option(OPTION_NUM, CONTENT, PROBLEM_ID) values (#{optionNum},#{content},#{problemId})")
    @Options(useGeneratedKeys = true,keyProperty = "optionId")
    public void insertOption(Option option);
}
