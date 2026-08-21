package com.huang.enterpriseai.ai.chat.tool;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.huang.enterpriseai.enums.KnowledgeDocumentCountScope;
import com.huang.enterpriseai.model.ChatConversationEntity;
import com.huang.enterpriseai.model.KnowledgeDocumentCountToolEntity;
import com.huang.enterpriseai.model.KnowledgeDocumentCountToolResultEntity;
import com.huang.enterpriseai.model.KnowledgeDocumentEntity;
import com.huang.enterpriseai.repository.KnowledgeDocumentDao;
import org.springframework.ai.tool.annotation.Tool;
/**
 * @Author: huang
 * @Description: 大模型调用工具类
 * @DateTime: 2026/8/20 16:23
 **/
public class KnowledgeBaseTools {

    private final KnowledgeDocumentDao knowledgeDocumentDao;

    private final ChatConversationEntity chatConversation;

    public KnowledgeBaseTools(KnowledgeDocumentDao knowledgeDocumentDao, ChatConversationEntity chatConversation) {
        this.knowledgeDocumentDao = knowledgeDocumentDao;
        this.chatConversation = chatConversation;
    }

    @Tool(name = "countKnowledgeBaseDocuments",
            description = """
                    查询当前会话绑定知识库中的实时文档数量。
                    scope含义：
                       ALL：全部文档
                       COMPLETED：处理完成
                       PROCESSING：处理中
                       FAILED：处理失败
                    """
    )
    public KnowledgeDocumentCountToolResultEntity countKnowledgeBaseDocuments(KnowledgeDocumentCountToolEntity entity) {
        KnowledgeDocumentCountScope scope = entity.getScope();
        if (scope == null ) {
            scope = KnowledgeDocumentCountScope.ALL;
        }

        LambdaQueryWrapper<KnowledgeDocumentEntity> wrapper = Wrappers.<KnowledgeDocumentEntity>lambdaQuery()
                .eq(KnowledgeDocumentEntity::getKnowledgeBaseId, chatConversation.getKnowledgeBaseId());

        if(scope!=KnowledgeDocumentCountScope.ALL){
            wrapper.eq(KnowledgeDocumentEntity::getStatus, scope.name());
        }

        Long count = knowledgeDocumentDao.selectCount(wrapper);
        return new KnowledgeDocumentCountToolResultEntity(scope.name(), count);
    }
}