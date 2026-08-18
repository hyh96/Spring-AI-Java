package com.huang.enterpriseai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/17 20:10
 **/
public record CreateChatConversationRequestDto(
        @Size(max = 100, message = "会话标题不能超过100个字符")
        String title,
        @NotBlank
        String knowledgeBaseId
) {
}