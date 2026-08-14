CREATE TABLE vector_store
(
    id        UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    content   TEXT,
    metadata  JSON,
    embedding VECTOR(1024)
);

CREATE INDEX idx_vector_store_embedding
    ON vector_store
    USING HNSW (embedding vector_cosine_ops);