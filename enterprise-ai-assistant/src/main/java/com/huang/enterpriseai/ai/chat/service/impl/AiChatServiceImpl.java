package com.huang.enterpriseai.ai.chat.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.stream.CollectorUtil;
import com.huang.enterpriseai.ai.chat.dto.QuestionAnalysisDto;
import com.huang.enterpriseai.ai.chat.service.AiChatService;
import com.huang.enterpriseai.model.KnowledgeBaseEntity;
import com.huang.enterpriseai.repository.KnowledgeBaseDao;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:47
 **/
@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final KnowledgeBaseDao knowledgeBaseDao;

    public AiChatServiceImpl(ChatClient chatClient,VectorStore vectorStore,KnowledgeBaseDao knowledgeBaseDao) {
        this.chatClient =chatClient;
        this.vectorStore=vectorStore;
        this.knowledgeBaseDao=knowledgeBaseDao;

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

    @Override
    public String ragChat(String knowledgeBaseId, String question) {

        //1.检验知识库
        KnowledgeBaseEntity knowledgeBase = knowledgeBaseDao.selectById(knowledgeBaseId);

        if(knowledgeBase==null){
            throw new NoSuchElementException("知识库不存在");
        }

        //2.只检索当前知识库的向量数据
        SearchRequest searchRequest = SearchRequest.builder().query(question)
                .topK(5)
                .similarityThreshold(0.5)
                .filterExpression(
                        "knowledgeBaseId=='" + knowledgeBaseId + "'"
                ).build();

        //3.查询向量库   相似度检索
        List<Document> documentList = vectorStore.similaritySearch(searchRequest);
        if(CollectionUtil.isEmpty(documentList)){
            return "当前知识库中没有检索到与该问题相关的内容";
        }
        //4.拼接检索出来的上下文
        String context= documentList.stream().map(Document::getText).collect(Collectors.joining("\n\n"));

        //5.交给大模型生成最终答案
        String content = chatClient.prompt().system("""
                   你是企业知识库问答助手。
                   请严格根据提供的知识库上下文回答用户问题。
                   规则：
                    1.优先依据知识库上下文回答。
                    2.不允许编造知识库中不存在的信息。
                    3.如果上下文不足以回答，明确告诉用户知识库中没有足够信息。
                    4.使用中文回答。
                """).user(user -> user.text("""
                用户问题：{question}
                知识库上下文：{context}
                """).param("question", question).param("context", context)).call().content();
        return content;
    }
}
