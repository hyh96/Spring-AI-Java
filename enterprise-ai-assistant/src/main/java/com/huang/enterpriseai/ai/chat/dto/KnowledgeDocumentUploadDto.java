package com.huang.enterpriseai.ai.chat.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/15 11:45
 **/
@Data
public class KnowledgeDocumentUploadDto {

    private String knowledgeBaseId;

    private MultipartFile file;
}