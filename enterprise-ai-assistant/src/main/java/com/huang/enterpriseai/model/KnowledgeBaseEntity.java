package com.huang.enterpriseai.model;

import com.baomidou.mybatisplus.annotation.*;
import com.huang.enterpriseai.constants.KnowledgeBaseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/7 16:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ai_knowledge_base")
public class KnowledgeBaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String  id;

    private String name;

    private String description;

    private KnowledgeBaseStatus status;

    @TableField(fill = FieldFill.INSERT)
    private OffsetDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private OffsetDateTime updatedAt;
}