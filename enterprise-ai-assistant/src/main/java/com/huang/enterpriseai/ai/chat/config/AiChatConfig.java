package com.huang.enterpriseai.ai.chat.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: huang
 * @Description: 两个都是ChatClient，根据用途来决定使用哪个，只用注解 @Qualifier("xxx")
 * @DateTime: 2026/8/12 11:28
 **/
@Configuration
public class AiChatConfig {


    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public ChatClient ragChatClient(ChatClient.Builder builder, VectorStore vectorStore) {
        return builder.defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(
                                        SearchRequest.builder()
                                                .topK(5)
                                                .similarityThreshold(0.5)
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    //定义对话记忆组件
    //ChatMemoryRepository 只负责存取，ChatMemory 决定哪些消息保留、什么时候删除
    /*
    * JdbcChatMemoryRepository
        怎么存
        PostgreSQL
      MessageWindowChatMemory
        存哪些、淘汰哪些
        最近 N 条窗口策略
      ChatMemory
        上层统一抽象
    *
    * */
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(20)
                .build();
    }

    /*
     * 构建MemoryAdvisor
     *
     * */
    @Bean
    public ChatClient chatMemoryRagClient(ChatClient.Builder chatClient, VectorStore vectorStore, ChatMemory chatMemory) {
        return chatClient.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest
                                        .builder()
                                        .topK(5)
                                        .similarityThreshold(0.5)
                                        .build()).build())
                .build();
    }

    /*
     * 构建toolAdvisor
     *
     * */
    @Bean
    public ChatClient chatToolMemoryClient(ChatClient.Builder builder, ChatMemory chatMemory) {
        return builder.defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(chatMemory)
                        .build())
                .build();
    }
}
