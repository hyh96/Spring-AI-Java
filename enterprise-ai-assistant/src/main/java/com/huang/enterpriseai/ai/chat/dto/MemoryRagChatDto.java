package com.huang.enterpriseai.ai.chat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/18 11:15
 **/
@Data
public class MemoryRagChatDto {

    @NotBlank(message = "会话ID不能为空")
    private String conversationId;

    @NotBlank(message = "问题不能为空")
    private String question;
}