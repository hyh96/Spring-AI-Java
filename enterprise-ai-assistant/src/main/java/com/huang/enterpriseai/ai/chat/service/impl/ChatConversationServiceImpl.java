package com.huang.enterpriseai.ai.chat.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.huang.enterpriseai.ai.chat.service.ChatConversationService;
import com.huang.enterpriseai.dto.ChatConversationResponseDto;
import com.huang.enterpriseai.dto.CreateChatConversationRequestDto;
import com.huang.enterpriseai.enums.ChatConversationStatus;
import com.huang.enterpriseai.model.ChatConversationEntity;
import com.huang.enterpriseai.model.KnowledgeBaseEntity;
import com.huang.enterpriseai.repository.ChatConversationDao;
import com.huang.enterpriseai.repository.KnowledgeBaseDao;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/17 20:12
 **/
@Service
public class ChatConversationServiceImpl implements ChatConversationService {

    private final ChatClient ragClient;
    private final ChatClient chatMemoryClient;
    private final KnowledgeBaseDao knowledgeBaseDao;
    private final ChatConversationDao chatConversationDao;

    public ChatConversationServiceImpl(@Qualifier("chatMemoryRagClient") ChatClient chatMemoryClient,
                                       @Qualifier("ragChatClient") ChatClient ragChatClient,
                                       KnowledgeBaseDao knowledgeBaseDao,ChatConversationDao chatConversationDao){
        this.chatMemoryClient=chatMemoryClient;
        this.ragClient=ragChatClient;
        this.knowledgeBaseDao=knowledgeBaseDao;
        this.chatConversationDao=chatConversationDao;
    }

    @Transactional
    @Override
    public ChatConversationResponseDto create(CreateChatConversationRequestDto requestDto) {
        String knowledgeBaseId = requestDto.knowledgeBaseId();

        //1.查询知识库，判断是否存在
        KnowledgeBaseEntity knowledgeBase = knowledgeBaseDao.selectById(knowledgeBaseId);

        //不存在，直接返回不再执行
        if(ObjectUtil.isEmpty(knowledgeBase)){
            throw new NoSuchElementException("知识库不存在");
        }

        //查询对话
        ChatConversationEntity chatConversation=new ChatConversationEntity();
        chatConversation.setKnowledgeBaseId(knowledgeBaseId);
        chatConversation.setTitle(requestDto.title()==null || requestDto.title().isBlank() ? "新对话": requestDto.title());
        chatConversation.setStatus(ChatConversationStatus.ACTIVE);
        chatConversationDao.insert(chatConversation);

        return new ChatConversationResponseDto(chatConversation.getId(),
                chatConversation.getKnowledgeBaseId(),
                chatConversation.getTitle(),
                chatConversation.getStatus(),
                chatConversation.getCreatedAt(),
                chatConversation.getUpdatedAt());

    }
}
