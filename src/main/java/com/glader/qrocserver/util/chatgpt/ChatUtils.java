package com.glader.qrocserver.util.chatgpt;

import com.glader.qrocserver.pojo.chatgpt.ChatCompletion;
import com.glader.qrocserver.util.json.JsonUtils;
import okhttp3.*;
import org.json.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChatUtils {
    private static final String api_key = "sk-bLDACgF5fn1vsr1b52B792C1Aa18472a9dC735A8EcC381E3";
    public static String chat(String input) throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/json");
        input = "\"" + input + "\"";
        String content = "{\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"messages\": [\n" +
                "      {\n" +
                "        \"role\": \"system\",\n" +
                "        \"content\": \"You are a helpful assistant.\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"role\": \"user\",\n" +
                "        \"content\": "+
                input +
                "      }\n" +
                "    ]\n" +
                "  }";
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://gpt.wf/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + api_key)  // 添加 API 密钥到请求头部
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS) // 设置连接超时时间为10秒
                .readTimeout(60, TimeUnit.SECONDS).build();// 设置读取超时时间为10秒
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        ChatCompletion chatCompletion = JsonUtils.jsonToChatCompletion(responseData);
        return chatCompletion.getChoices().get(0).getMessage().getContent();
    }
}