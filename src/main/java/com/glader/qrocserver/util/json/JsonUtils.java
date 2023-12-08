package com.glader.qrocserver.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.glader.qrocserver.pojo.Questionnaire;
import com.glader.qrocserver.pojo.chatgpt.ChatCompletion;
import com.glader.qrocserver.util.result.Result;

import java.util.List;

public class JsonUtils {
    public static String objectToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static Result jsonToResult(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Result.class);
    }

    public static Questionnaire jsonToQuestionnaire(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, Questionnaire.class);
    }

    public static List<Questionnaire> jsonToArrayQuestionnaire(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Result result = objectMapper.readValue(json, Result.class);
        List<Questionnaire> questionnaireList = objectMapper.convertValue(result.getData(), new TypeReference<List<Questionnaire>>() {
        });
        return questionnaireList;
    }

    public static ChatCompletion jsonToChatCompletion(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatCompletion chatCompletion = objectMapper.readValue(json,ChatCompletion.class);
        return chatCompletion;
    }
}