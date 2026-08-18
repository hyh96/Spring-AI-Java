package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.ai.chat.service.ChatConversationService;
import com.huang.enterpriseai.dto.CreateChatConversationRequestDto;
import com.huang.enterpriseai.repository.ChatConversationDao;
import com.huang.enterpriseai.vo.ResultVo;
import jakarta.validation.Valid;
import lombok.val;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/18 9:31
 **/
@RestController
@RequestMapping("/api/conversation")
public class ChatConversationController {

    private final ChatConversationService chatConversationService;

    public ChatConversationController(ChatConversationService chatConversationService){
        this.chatConversationService=chatConversationService;
    }

    @PostMapping("/createConversation")
    public ResultVo createConversation(@Valid @RequestBody CreateChatConversationRequestDto dto){
        return ResultVo.success(chatConversationService.create(dto));

    }

}
