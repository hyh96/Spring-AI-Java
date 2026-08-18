package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.dto.CreateKnowledgeBaseRequestDto;
import com.huang.enterpriseai.dto.KnowledgeBaseResponseDto;
import com.huang.enterpriseai.service.KnowledgeBaseService;
import com.huang.enterpriseai.vo.ResultVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/10 16:21
 **/
@RestController
@RequestMapping("/api/knowledge-bases")
public class KnowledgeBaseController {
    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @PostMapping("/create")
    public ResultVo<KnowledgeBaseResponseDto> create(@Valid @RequestBody CreateKnowledgeBaseRequestDto requestDto) {
        return ResultVo.success(knowledgeBaseService.create(requestDto));
    }

    @GetMapping
    public ResultVo<List<KnowledgeBaseResponseDto>> list() {
        return ResultVo.success(knowledgeBaseService.list());
    }

    @GetMapping("/{id}")
    public ResultVo<KnowledgeBaseResponseDto> getById(@PathVariable String id) {
        return ResultVo.success(knowledgeBaseService.getById(id));
    }

    @PutMapping("/{id}")
    public ResultVo<KnowledgeBaseResponseDto> update(@PathVariable String id, @Valid @RequestBody CreateKnowledgeBaseRequestDto requestDto) {
        return ResultVo.success(knowledgeBaseService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResultVo<Void> delete(@PathVariable String id) {
        knowledgeBaseService.delete(id);
        return ResultVo.success();
    }
}
