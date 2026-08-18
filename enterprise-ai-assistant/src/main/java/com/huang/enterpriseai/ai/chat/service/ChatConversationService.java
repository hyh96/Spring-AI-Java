package com.huang.enterpriseai.ai.chat.service;

import com.huang.enterpriseai.dto.ChatConversationResponseDto;
import com.huang.enterpriseai.dto.CreateChatConversationRequestDto;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/17 20:12
 **/
public interface ChatConversationService {
    ChatConversationResponseDto create(CreateChatConversationRequestDto requestDto);
}
