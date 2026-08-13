package com.huang.enterpriseai.ai.chat.service;

import com.huang.enterpriseai.ai.chat.dto.QuestionAnalysisDto;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:46
 **/
public interface AiChatService {

     String chat(String message);

     String templateChat(String message);

     QuestionAnalysisDto analyze(String message);
}
