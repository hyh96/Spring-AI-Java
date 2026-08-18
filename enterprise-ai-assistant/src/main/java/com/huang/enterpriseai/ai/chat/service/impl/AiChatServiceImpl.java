package com.huang.enterpriseai.ai.chat.service.impl;

import com.huang.enterpriseai.ai.chat.dto.QuestionAnalysisDto;
import com.huang.enterpriseai.ai.chat.service.AiChatService;
import com.huang.enterpriseai.model.ChatConversationEntity;
import com.huang.enterpriseai.model.KnowledgeBaseEntity;
import com.huang.enterpriseai.ai.chat.dto.MemoryRagChatDto;
import com.huang.enterpriseai.repository.ChatConversationDao;
import com.huang.enterpriseai.repository.KnowledgeBaseDao;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:47
 **/
@Service
public class AiChatServiceImpl implements AiChatService {

    private final ChatClient chatClient;
    private final ChatClient ragChatClient;
//    private final QuestionAnswerAdvisor questionAnswerAdvisor;
    private final ChatClient chatRagMemoryClient;
    private final KnowledgeBaseDao knowledgeBaseDao;
    private final ChatConversationDao chatConversationDao;

//    public AiChatServiceImpl(@Qualifier("ragChatClient")ChatClient chatClient,@Qualifier("chatMemoryRagClient")ChatClient chatMemoryClient, QuestionAnswerAdvisor questionAnswerAdvisor, KnowledgeBaseDao knowledgeBaseDao,ChatConversationDao chatConversationDao) {
//        this.chatClient =chatClient;
//        this.chatMemoryClient=chatMemoryClient;
//        this.questionAnswerAdvisor=questionAnswerAdvisor;
//        this.knowledgeBaseDao=knowledgeBaseDao;
//        this.chatConversationDao=chatConversationDao;
//
//    }

    public AiChatServiceImpl(
            @Qualifier("chatClient")
            ChatClient chatClient,

            @Qualifier("ragChatClient")
            ChatClient ragChatClient,

            @Qualifier("chatMemoryRagClient")
            ChatClient chatMemoryRagClient,

            KnowledgeBaseDao knowledgeBaseDao,
            ChatConversationDao chatConversationDao) {

        this.chatClient = chatClient;
        this.ragChatClient = ragChatClient;
        this.chatRagMemoryClient = chatMemoryRagClient;
        this.knowledgeBaseDao = knowledgeBaseDao;
        this.chatConversationDao = chatConversationDao;
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
    public QuestionAnalysisDto chatConvertJson(String message) {
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
        String filterExpression = "knowledgeBaseId == '" + knowledgeBaseId + "'";

        //5.交给大模型生成最终答案
        String content = chatClient.prompt().system("""
                   你是企业知识库问答助手。
                   请严格根据提供的知识库上下文回答用户问题。
                   规则：
                    1.优先依据知识库上下文回答。
                    2.不允许编造知识库中不存在的信息。
                    3.如果上下文不足以回答，明确告诉用户知识库中没有足够信息。
                    4.使用中文回答。
                """)
                .user(question)
                .advisors(a->a.param(QuestionAnswerAdvisor.FILTER_EXPRESSION,filterExpression))
                .call()
                .content();
        return content;
    }

    @Override
    public String memoryRagChat(MemoryRagChatDto memoryRagChat) {

        String conversationId = memoryRagChat.getConversationId();
        String question = memoryRagChat.getQuestion();
        //1.查询会话
        ChatConversationEntity chatConversation = chatConversationDao.selectById(conversationId);

        if(chatConversation==null){
            throw new NoSuchElementException("会话不存在");
        }

        String knowledgeBaseId = chatConversation.getKnowledgeBaseId();

        //2.检验知识库
        KnowledgeBaseEntity knowledgeBase = knowledgeBaseDao.selectById(knowledgeBaseId);

        if(knowledgeBase==null){
            throw new NoSuchElementException("知识库不存在");
        }
        String filterExpression = "knowledgeBaseId == '" + knowledgeBaseId + "'";

        //3.Memory+Rag
        //封装client，调用大模型返回
       String res= chatRagMemoryClient.prompt()
                .system("""
                   你是企业知识库问答助手。
                   请结合：
                   1.当前会话历史
                   2.当前知识库资料
                   回答用户问题。
                   如果知识库资料不足，
                   请明确说明，不允许编造。
                """)
               .user(question)
//                Tip :ChatMemory.CONVERSATION_ID 必须不能为空，必传，否则报错
               .advisors(advisorSpec ->
                       advisorSpec.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId)
                .param(QuestionAnswerAdvisor.FILTER_EXPRESSION,filterExpression))
                .call()
                .content();
        return res;
    }
}
