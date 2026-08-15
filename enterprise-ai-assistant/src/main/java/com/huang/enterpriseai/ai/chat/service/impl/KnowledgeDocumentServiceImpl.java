package com.huang.enterpriseai.ai.chat.service.impl;

import com.huang.enterpriseai.ai.chat.service.KnowledgeDocumentService;
import com.huang.enterpriseai.enums.KnowledgeDocumentStatus;
import com.huang.enterpriseai.model.KnowledgeBaseEntity;
import com.huang.enterpriseai.model.KnowledgeDocumentEntity;
import com.huang.enterpriseai.repository.KnowledgeBaseDao;
import com.huang.enterpriseai.repository.KnowledgeDocumentDao;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/14 16:20
 **/
@Service
public class KnowledgeDocumentServiceImpl implements KnowledgeDocumentService {


    private final KnowledgeBaseDao knowledgeBaseDao;

    private final KnowledgeDocumentDao knowledgeDocumentDao;

    private final VectorStore vectorStore;

    public KnowledgeDocumentServiceImpl(KnowledgeBaseDao knowledgeBaseDao, KnowledgeDocumentDao knowledgeDocumentDao, VectorStore vectorStore) {
        this.knowledgeBaseDao = knowledgeBaseDao;
        this.knowledgeDocumentDao = knowledgeDocumentDao;
        this.vectorStore = vectorStore;
    }


    @Override
    public void upload(String knowledgeBaseId, MultipartFile file) {

        //1.确认知识库存在
        KnowledgeBaseEntity knowledgeBase = knowledgeBaseDao.selectById(knowledgeBaseId);
        if (knowledgeBase == null) {
            throw new NoSuchElementException("知识库不存在");
        }

        //2.创建业务文档记录
        KnowledgeDocumentEntity documentEntity = new KnowledgeDocumentEntity();
        documentEntity.setKnowledgeBaseId(knowledgeBaseId);
        documentEntity.setFileName(file.getOriginalFilename());
        documentEntity.setFileSize(file.getSize());
        documentEntity.setContentType(file.getContentType());
        documentEntity.setStatus(KnowledgeDocumentStatus.PROCESSING.name());
        documentEntity.setChunkCount(0);
        knowledgeDocumentDao.insert(documentEntity);

        try {

            //3.解析文件
            TikaDocumentReader reader = new TikaDocumentReader(file.getResource());

            //读取原文件内容
            List<Document> documentList = reader.read();

            //增加业务数据标签metadata
            documentList.forEach(document -> {
                document.getMetadata().put("knowledgeBaseId", knowledgeBaseId);
                document.getMetadata().put("documentId", documentEntity.getId());
                document.getMetadata().put("fileName", file.getOriginalFilename());
            });

            //5.切片
            TokenTextSplitter splitter = TokenTextSplitter.builder()
                    .withChunkSize(500)
                    .withMinChunkSizeChars(200)
                    .withMinChunkLengthToEmbed(20)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();

            //等同于splitter.apply(documentList);
            List<Document> chunks = splitter.transform(documentList);
            vectorStore.add(chunks);
            //更新状态
            documentEntity.setStatus(KnowledgeDocumentStatus.COMPLETED.name());
            documentEntity.setChunkCount(chunks.size());
            knowledgeDocumentDao.updateById(documentEntity);

        } catch (Exception e) {
            documentEntity.setStatus(KnowledgeDocumentStatus.FAILED.name());
            knowledgeDocumentDao.updateById(documentEntity);
            throw e;
        }
    }
}
