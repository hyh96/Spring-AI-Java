ALTER TABLE ai_knowledge_base
    ALTER COLUMN id DROP DEFAULT;

ALTER TABLE ai_knowledge_base
ALTER COLUMN id TYPE VARCHAR(36)
    USING id::text;