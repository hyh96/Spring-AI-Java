package com.huang.enterpriseai.ai.chat.service;
import org.springframework.ai.document.Document;
import java.util.List;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 15:16
 **/
public interface AiVectorService {

    void add();

    List<Document> search(String query);
}
