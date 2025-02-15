CREATE TABLE IF NOT EXISTS produtos (
    id UUID PRIMARY KEY,
    nome VARCHAR(255),
    descricao VARCHAR(255),
    preco NUMERIC(19, 2),
    quantidade_em_estoque INTEGER
);