package com.glader.qrocserver.pojo.chatgpt;

// Getters and setters

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    private int index;
    private Message message;
    private String finish_reason;

    // Getters and setters
}