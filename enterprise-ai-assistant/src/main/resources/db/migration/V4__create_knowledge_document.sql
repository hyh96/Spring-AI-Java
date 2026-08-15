CREATE TABLE ai_knowledge_document
(
    id                VARCHAR(36) PRIMARY KEY,
    knowledge_base_id VARCHAR(36)  NOT NULL,
    file_name         VARCHAR(255) NOT NULL,
    content_type      VARCHAR(100),
    file_size         BIGINT       NOT NULL,
    status            VARCHAR(20)  NOT NULL,
    chunk_count       INT          DEFAULT 0,
    created_at        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_ai_knowledge_document_base
        FOREIGN KEY (knowledge_base_id)
            REFERENCES ai_knowledge_base(id),

    CONSTRAINT ck_ai_knowledge_document_status
        CHECK (status IN ('PROCESSING', 'COMPLETED', 'FAILED'))
);

CREATE INDEX idx_ai_knowledge_document_base_id
    ON ai_knowledge_document(knowledge_base_id);