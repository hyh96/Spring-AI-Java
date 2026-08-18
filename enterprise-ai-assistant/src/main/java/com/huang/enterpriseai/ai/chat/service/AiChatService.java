package com.huang.enterpriseai.ai.chat.service;

import com.huang.enterpriseai.ai.chat.dto.QuestionAnalysisDto;
import com.huang.enterpriseai.ai.chat.dto.MemoryRagChatDto;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:46
 **/
public interface AiChatService {

     String chat(String message);

     String templateChat(String message);

     QuestionAnalysisDto chatConvertJson(String message);

     String ragChat(String knowledgeBaseId,String question);


     String memoryRagChat(MemoryRagChatDto memoryRagChat);
}
