package com.huang.enterpriseai.ai.chat.service.impl;

import com.huang.enterpriseai.ai.chat.service.AiChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:47
 **/
@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;

    public AiChatServiceImpl(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @Override
    public String chat(String message) {
        return chatClient
                .prompt()
                .user(message)
                .call()
                .content();
    }
}
