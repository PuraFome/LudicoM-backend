-- Schema DDL para todas as entidades do projeto (PostgreSQL)
-- Gera as tabelas: instituicao, participante, evento, emprestimo, jogo, users
-- Observações:
-- - Campos de ID baseados em UUID foram mapeados como TEXT para refletir o columnDefinition dos modelos.
-- - Tabela users utiliza BIGSERIAL para IDENTITY (GenerationType.IDENTITY em PostgreSQL).

-- 1) Tabela de instituição
CREATE TABLE IF NOT EXISTS instituicao (
    uid               TEXT PRIMARY KEY,
    nome              VARCHAR(200) NOT NULL,
    endereco          VARCHAR(255)
);

-- 2) Tabela de participante
CREATE TABLE IF NOT EXISTS participante (
    uid               TEXT PRIMARY KEY,
    id_instituicao    VARCHAR(255) UNIQUE,
    nome              VARCHAR(200) NOT NULL,
    email             VARCHAR(150) NOT NULL UNIQUE,
    documento         VARCHAR(30)  NOT NULL UNIQUE,
    ra                VARCHAR(15) UNIQUE
);

-- 3) Tabela de evento
CREATE TABLE IF NOT EXISTS evento (
    uid               TEXT PRIMARY KEY,
    id_instituicao    TEXT NOT NULL,
    data              DATE NOT NULL,
    hora_inicio       TIME NOT NULL,
    hora_fim          TIME NOT NULL,
    CONSTRAINT fk_evento_instituicao
        FOREIGN KEY (id_instituicao) REFERENCES instituicao(uid)
);
CREATE INDEX IF NOT EXISTS idx_evento_instituicao ON evento(id_instituicao);

-- 4) Tabela de empréstimo
CREATE TABLE IF NOT EXISTS emprestimo (
    uid               TEXT PRIMARY KEY,
    id_participante   TEXT NOT NULL,
    id_evento         TEXT NOT NULL,
    hora_emprestimo   TIME NOT NULL,
    hora_devolucao    TIME NOT NULL,
    is_devolvido      BOOLEAN NOT NULL,
    CONSTRAINT fk_emp_participante
        FOREIGN KEY (id_participante) REFERENCES participante(uid),
    CONSTRAINT fk_emp_evento
        FOREIGN KEY (id_evento) REFERENCES evento(uid),
    -- OneToOne com participante: garante um empréstimo ativo por participante (ajuste se não desejar unicidade)
    CONSTRAINT uq_emp_participante UNIQUE (id_participante)
);
CREATE INDEX IF NOT EXISTS idx_emp_evento ON emprestimo(id_evento);

-- 5) Tabela de jogo
CREATE TABLE IF NOT EXISTS jogo (
    uid                TEXT PRIMARY KEY,
    nome               VARCHAR(200) NOT NULL,
    nome_alternativo   VARCHAR(200),
    ano_publicacao     DATE,
    tempo_de_jogo      INTEGER,
    minimo_jogadores   INTEGER,
    maximo_jogadores   INTEGER,
    codigo_de_barras   VARCHAR(255),
    is_disponivel      BOOLEAN DEFAULT TRUE,
    criado_quando      TIMESTAMP,
    atualizado_quando  TIMESTAMP
);

-- 6) Tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(255) NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    is_active   BOOLEAN DEFAULT TRUE
);
