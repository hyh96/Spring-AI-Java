package com.huang.enterpriseai.model;

import com.huang.enterpriseai.enums.KnowledgeDocumentCountScope;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: huang
 * @Description: Tool 入参实体
 * @DateTime: 2026/8/20 16:25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KnowledgeDocumentCountToolEntity {

    private KnowledgeDocumentCountScope scope;
}