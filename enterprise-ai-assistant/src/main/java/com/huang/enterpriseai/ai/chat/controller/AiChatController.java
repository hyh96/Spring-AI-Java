package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.ai.chat.dto.ChatRequestDto;
import com.huang.enterpriseai.ai.chat.service.AiChatService;
import com.huang.enterpriseai.vo.ResultVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/11 17:49
 **/
@RestController
@RequestMapping("/api/ai/chat")
public class AiChatController {

    private final AiChatService aiChatService;

    public AiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping
    public ResultVo<String> chat(@Valid @RequestBody ChatRequestDto requestDto) {
        return ResultVo.success(aiChatService.chat(requestDto.message()));
    }
}