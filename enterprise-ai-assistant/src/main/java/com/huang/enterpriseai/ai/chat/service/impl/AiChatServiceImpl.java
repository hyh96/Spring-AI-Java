package com.huang.enterpriseai.ai.chat.service.impl;

import com.huang.enterpriseai.ai.chat.dto.QuestionAnalysisDto;
import com.huang.enterpriseai.ai.chat.service.AiChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:47
 **/
@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;

    public AiChatServiceImpl(ChatClient chatClient) {
        this.chatClient =chatClient;
    }

    //注册资源文件
    @Value("classpath:prompts/enterprise-assistant-system.st")
    private Resource systemPromptResource;

    /**
    * @Author: huang
    * @Description: system：定义AI是谁，遵守什么规则。  user：用户具体问题
    * @DateTime: 11:24 2026/8/12
    * @Params: [message]
    * @Return java.lang.String
    */
    @Override
    public String chat(String message) {
   
        return chatClient
                .prompt()
                .system("""
                        你是企业内部AI助手。
                        回答要求：
                           1.使用中文回答
                           2.回答简洁、准确
                           3.不确定的信息明确说明不确定
                        """)
                .user(message)
                .call()
                .content();
    }

    /**
    * @Author: huang
    * @Description: 自定义模板，通用，不需要每个chatClient都维护一个system
    * @DateTime: 11:34 2026/8/12
    * @Params: [message]
    * @Return java.lang.String
    */
    @Override
    public String templateChat(String message) {
        return chatClient.prompt()
                .system(system->system.text(systemPromptResource)
                        .param("role","通用问答助手"))
                .user(message)
                .call()
                .content();
    }

    @Override
    public QuestionAnalysisDto analyze(String message) {

        return chatClient
                .prompt()
                .system("""
                    你是一个企业AI助手中的问题分析器。

                    请分析用户的问题，并提取：
                    1. category：问题分类
                    2. summary：问题摘要
                    3. intent：用户意图
                    """)
                .user(message)
                .call()
                .entity(QuestionAnalysisDto.class);
    }
}
