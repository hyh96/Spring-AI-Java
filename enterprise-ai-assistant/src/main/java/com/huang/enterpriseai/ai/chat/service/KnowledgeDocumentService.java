package com.huang.enterpriseai.ai.chat.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 16:20
 **/
public interface KnowledgeDocumentService {
    void upload(String knowledgeBaseId, MultipartFile file);
}
