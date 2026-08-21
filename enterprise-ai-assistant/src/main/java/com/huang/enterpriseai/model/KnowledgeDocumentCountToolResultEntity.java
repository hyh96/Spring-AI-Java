package com.huang.enterpriseai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: huang
 * @Description: Tool 返回实体
 * @DateTime: 2026/8/20 16:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocumentCountToolResultEntity {

    private String scope;

    private Long documentCount;
}