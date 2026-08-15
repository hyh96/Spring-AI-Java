package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.ai.chat.dto.KnowledgeDocumentUploadDto;
import com.huang.enterpriseai.ai.chat.service.KnowledgeDocumentService;
import com.huang.enterpriseai.vo.ResultVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/15 11:40
 **/
@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeDocumentController {

    private final KnowledgeDocumentService knowledgeDocumentService;
    public KnowledgeDocumentController(KnowledgeDocumentService knowledgeDocumentService){
        this.knowledgeDocumentService=knowledgeDocumentService;
    }


    @PostMapping("/uploadDocument")
    public ResultVo uploadDocument( KnowledgeDocumentUploadDto dto){

        knowledgeDocumentService.upload(dto.getKnowledgeBaseId(),dto.getFile());

        return ResultVo.success();

    }


}
