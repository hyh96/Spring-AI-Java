CREATE TABLE ai_chat_conversation
(
    id                VARCHAR(36)  PRIMARY KEY,
    knowledge_base_id VARCHAR(36)  NOT NULL,
    title             VARCHAR(100) NOT NULL,
    status            VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_chat_conversation_knowledge_base
        FOREIGN KEY (knowledge_base_id)
            REFERENCES ai_knowledge_base(id),

    CONSTRAINT ck_chat_conversation_status
        CHECK (status IN ('ACTIVE', 'ARCHIVED'))
);

CREATE INDEX idx_chat_conversation_knowledge_base
    ON ai_chat_conversation(knowledge_base_id);