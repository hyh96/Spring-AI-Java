package com.huang.enterpriseai.ai.chat.service.impl;

import com.huang.enterpriseai.ai.chat.service.AiEmbeddingService;
import org.springframework.stereotype.Service;
import org.springframework.ai.embedding.EmbeddingModel;
/**
 * @Author: huang
 * @Description: EmbeddingModel转为向量
 * @DateTime: 2026/8/13 17:16
 **/
@Service
public class AiEmbeddingServiceImpl implements AiEmbeddingService {

    private final EmbeddingModel embeddingModel;

    public AiEmbeddingServiceImpl(EmbeddingModel embeddingModel){
        this.embeddingModel=embeddingModel;
    }

    @Override
    public float[] embedding(String text) {
        return embeddingModel.embed(text);
    }
}
