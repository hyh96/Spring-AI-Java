package com.huang.enterpriseai.ai.chat.service.impl;
import org.springframework.ai.document.Document;
import com.huang.enterpriseai.ai.chat.service.AiVectorService;

import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 15:17
 **/
@Service
public class AiVectorServiceImpl implements AiVectorService {


    private final VectorStore vectorStore;


    public AiVectorServiceImpl(VectorStore vectorStore){
        this.vectorStore=vectorStore;
    }


    @Override
    public void add() {
        List<Document> documents = List.of(
                new Document(
                        "Spring AI 是 Spring 官方提供的人工智能应用开发框架。",
                        Map.of(
                                "knowledgeBaseId", "test",
                                "category", "spring-ai"
                        )
                ),

                new Document(
                        "MyBatis-Plus 是 MyBatis 的增强工具，可以简化常见 CRUD 操作。",
                        Map.of(
                                "knowledgeBaseId", "test",
                                "category", "mybatis"
                        )
                ),

                new Document(
                        "PostgreSQL 可以通过 pgvector 扩展存储和检索向量数据。",
                        Map.of(
                                "knowledgeBaseId", "test",
                                "category", "postgresql"
                        )
                )
        );
        vectorStore.add(documents);
    }

    @Override
    public List<Document> search(String query) {
        SearchRequest searchRequest = SearchRequest.builder().query(query)
                .topK(2)
                .build();
        return vectorStore.similaritySearch(searchRequest);
    }
}
