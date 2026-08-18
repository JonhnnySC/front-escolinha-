-- 🗄️ Scripts SQL para Setup Sanscritinho
-- Banco de Dados: PostgreSQL 12+
-- Última atualização: 2026-08-18

-- ============================================================================
-- PARTE 1: CRIAR DATABASE
-- ============================================================================

-- Conectar ao banco padrão 'postgres'
-- \c postgres

-- Criar database principal
CREATE DATABASE sanscritinho
    ENCODING 'UTF8'
    LOCALE 'C'
    TEMPLATE template0
    OWNER postgres;

-- Comentar a database
COMMENT ON DATABASE sanscritinho IS 'Banco de dados para o sistema Sanscritinho - Gestão de Notas de Alunos';

-- ============================================================================
-- PARTE 2: CONECTAR AO DATABASE CRIADO
-- ============================================================================

-- Conectar ao novo database
-- \c sanscritinho

-- ============================================================================
-- PARTE 3: CRIAR SCHEMA (OPCIONAL - Se não usar public)
-- ============================================================================

-- CREATE SCHEMA IF NOT EXISTS app;
-- COMMENT ON SCHEMA app IS 'Schema da aplicação';

-- ============================================================================
-- PARTE 4: CRIAR TABELA ALUNOS (Será criada automaticamente pelo Hibernate)
-- ============================================================================

-- NOTA: A tabela será criada automaticamente pelo Hibernate com ddl-auto: update
-- Abaixo está o script SQL equivalente para referência:

CREATE TABLE IF NOT EXISTS public.alunos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    nota DOUBLE PRECISION NOT NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacoes VARCHAR(500)
);

-- Comentar tabela
COMMENT ON TABLE public.alunos IS 'Tabela de alunos com suas notas';

-- Comentar colunas
COMMENT ON COLUMN public.alunos.id IS 'Identificador único do aluno';
COMMENT ON COLUMN public.alunos.nome IS 'Nome completo do aluno';
COMMENT ON COLUMN public.alunos.matricula IS 'Matrícula única do aluno';
COMMENT ON COLUMN public.alunos.nota IS 'Nota do aluno (0-10)';
COMMENT ON COLUMN public.alunos.data_cadastro IS 'Data e hora de cadastro';
COMMENT ON COLUMN public.alunos.data_atualizacao IS 'Data e hora da última atualização';
COMMENT ON COLUMN public.alunos.observacoes IS 'Observações adicionais sobre o aluno';

-- ============================================================================
-- PARTE 5: CRIAR ÍNDICES PARA PERFORMANCE
-- ============================================================================

-- Índice para buscas por nota
CREATE INDEX IF NOT EXISTS idx_alunos_nota 
ON public.alunos (nota DESC);

-- Índice para buscas por matrícula
CREATE INDEX IF NOT EXISTS idx_alunos_matricula 
ON public.alunos (matricula);

-- Índice para buscas por nome
CREATE INDEX IF NOT EXISTS idx_alunos_nome 
ON public.alunos (nome);

-- Índice composto para buscas por intervalo de nota
CREATE INDEX IF NOT EXISTS idx_alunos_nota_interval 
ON public.alunos (nota, data_cadastro DESC);

-- ============================================================================
-- PARTE 6: CRIAR USUÁRIO ESPECÍFICO (RECOMENDADO PARA PRODUÇÃO)
-- ============================================================================

-- OPCIONAL: Criar usuário específico para a aplicação (mais seguro)
-- Descomente e execute se desejar usar usuário específico:

-- CREATE USER sanscritinho_user WITH PASSWORD 'sua_senha_forte_aqui';

-- Dar permissões ao usuário
-- GRANT CONNECT ON DATABASE sanscritinho TO sanscritinho_user;
-- GRANT USAGE ON SCHEMA public TO sanscritinho_user;
-- GRANT CREATE ON SCHEMA public TO sanscritinho_user;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO sanscritinho_user;
-- GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO sanscritinho_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO sanscritinho_user;
-- ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO sanscritinho_user;

-- ============================================================================
-- PARTE 7: DAR PERMISSÕES AO USUÁRIO PADRÃO (postgres)
-- ============================================================================

-- Se usar o usuário padrão 'postgres'
GRANT CONNECT ON DATABASE sanscritinho TO postgres;
GRANT USAGE ON SCHEMA public TO postgres;
GRANT CREATE ON SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO postgres;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO postgres;

-- ============================================================================
-- PARTE 8: DADOS DE TESTE (OPCIONAL)
-- ============================================================================

-- Inserir dados de teste
INSERT INTO public.alunos (nome, matricula, nota, observacoes) VALUES
('João Silva', '2024001', 8.5, 'Desempenho excelente'),
('Maria Santos', '2024002', 9.0, 'Excelente aluna'),
('Pedro Costa', '2024003', 6.5, 'Precisa melhorar'),
('Ana Oliveira', '2024004', 7.8, 'Bom desempenho'),
('Carlos Mendes', '2024005', 5.2, 'Abaixo da média'),
('Lucia Ferreira', '2024006', 8.2, 'Ótimo desempenho'),
('Bruno Silva', '2024007', 7.0, 'Desempenho médio'),
('Fernanda Lima', '2024008', 9.2, 'Excelente desempenho'),
('Roberto Alves', '2024009', 4.8, 'Baixo desempenho'),
('Camila Rocha', '2024010', 8.8, 'Muito bom');

-- ============================================================================
-- PARTE 9: VERIFICAÇÕES E CONSULTAS ÚTEIS
-- ============================================================================

-- Ver informações do database
SELECT datname, encoding, datcollate, datctype FROM pg_database WHERE datname = 'sanscritinho';

-- Ver tabelas criadas
SELECT tablename FROM pg_tables WHERE schemaname = 'public';

-- Ver índices criados
SELECT indexname FROM pg_indexes WHERE schemaname = 'public' AND tablename = 'alunos';

-- Ver permissões
SELECT grantee, privilege_type 
FROM information_schema.table_privileges 
WHERE table_name = 'alunos';

-- Ver dados inseridos
SELECT COUNT(*) as total_alunos FROM public.alunos;

-- Ver estatísticas
SELECT 
    COUNT(*) as total,
    AVG(nota) as media,
    MAX(nota) as nota_maxima,
    MIN(nota) as nota_minima
FROM public.alunos;

-- ============================================================================
-- PARTE 10: LIMPEZA (USE COM CUIDADO!)
-- ============================================================================

-- ⚠️ CUIDADO: Deletar todos os alunos
-- DELETE FROM public.alunos;

-- ⚠️ CUIDADO: Dropar tabela
-- DROP TABLE IF EXISTS public.alunos CASCADE;

-- ⚠️ CUIDADO: Dropar database (desconecte primeiro)
-- DROP DATABASE IF EXISTS sanscritinho;

-- ============================================================================
-- FIM DO SCRIPT
-- ============================================================================
