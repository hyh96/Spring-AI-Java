package com.huang.enterpriseai.ai.chat.controller;

import com.huang.enterpriseai.ai.chat.service.AiEmbeddingService;
import com.huang.enterpriseai.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/13 17:26
 **/
@Slf4j
@RequestMapping("/api/ai/embedding")
@RestController
public class AiEmbeddingController {

    private final AiEmbeddingService aiEmbeddingService;

    public AiEmbeddingController(AiEmbeddingService aiEmbeddingService) {
        this.aiEmbeddingService = aiEmbeddingService;
    }

    @GetMapping
    public ResultVo<Map<String, Object>> embedding(@RequestParam String text) {
        float[] vector = aiEmbeddingService.embedding(text);
        return ResultVo.success(Map.of("text", text, "dimensions", vector.length, "firstValue", vector[0]));
    }


}
