package com.huang.enterpriseai.model;

import com.baomidou.mybatisplus.annotation.*;
import com.huang.enterpriseai.enums.ChatConversationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/17 20:09
 **/
@TableName("ai_chat_conversation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatConversationEntity {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    private String knowledgeBaseId;

    private String title;

    private ChatConversationStatus status;

    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;
}