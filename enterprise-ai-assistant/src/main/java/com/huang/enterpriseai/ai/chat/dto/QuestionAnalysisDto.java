package com.huang.enterpriseai.ai.chat.dto;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/12 11:49
 **/
public record QuestionAnalysisDto(
        String category,
        String summary,
        String intent
) {
}
