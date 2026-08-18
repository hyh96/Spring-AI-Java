package com.huang.enterpriseai.dto;

import com.huang.enterpriseai.enums.ChatConversationStatus;
import java.util.Date;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/17 20:11
 **/
public record ChatConversationResponseDto(
        String id,
        String knowledgeBaseId,
        String title,
        ChatConversationStatus status,
        Date createdAt,
        Date updatedAt
) {
}