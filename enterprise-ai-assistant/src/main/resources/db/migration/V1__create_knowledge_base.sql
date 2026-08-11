CREATE TABLE ai_knowledge_base
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

    name        VARCHAR(100) NOT NULL,

    description VARCHAR(500),

    status      VARCHAR(20) NOT NULL DEFAULT 'ENABLED',

    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uk_ai_knowledge_base_name
        UNIQUE (name),

    CONSTRAINT ck_ai_knowledge_base_status
        CHECK (status IN ('ENABLED', 'DISABLED'))
);

COMMENT ON TABLE ai_knowledge_base
    IS 'AI知识库';

COMMENT ON COLUMN ai_knowledge_base.id
    IS '知识库ID';

COMMENT ON COLUMN ai_knowledge_base.name
    IS '知识库名称';

COMMENT ON COLUMN ai_knowledge_base.description
    IS '知识库描述';

COMMENT ON COLUMN ai_knowledge_base.status
    IS '状态：ENABLED启用，DISABLED禁用';