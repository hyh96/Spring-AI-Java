package com.huang.enterpriseai.ai.chat.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:46
 **/
public record ChatRequestDto(
     @NotBlank(message = "消息不能为空")
     String message) {
}