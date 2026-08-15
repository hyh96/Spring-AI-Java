package com.huang.enterpriseai.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 16:18
 **/
@TableName("ai_knowledge_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocumentEntity {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String knowledgeBaseId;

    private String fileName;

    private String contentType;

    private Long fileSize;

    private String status;

    private Integer chunkCount;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}