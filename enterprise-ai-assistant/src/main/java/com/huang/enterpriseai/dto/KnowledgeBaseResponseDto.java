package com.huang.enterpriseai.dto;

import com.huang.enterpriseai.constants.KnowledgeBaseStatus;
import java.time.OffsetDateTime;
import java.util.Date;

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
        Date createdAt,
        Date updatedAt
) {
}