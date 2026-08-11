package com.huang.enterpriseai.dto;

import com.huang.enterpriseai.constants.KnowledgeBaseStatus;
import java.time.OffsetDateTime;
/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/10 15:59
 **/
public record KnowledgeBaseResponseDto(
        String id,
        String name,
        String description,
        KnowledgeBaseStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}