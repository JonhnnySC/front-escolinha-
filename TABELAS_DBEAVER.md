# 📊 GUIA DE TABELAS PARA DBEAVER

**Destinado para**: IA configurar banco de dados PostgreSQL via DBeaver
**Banco de Dados**: `sanscritinho`
**Usuário**: `postgres`
**Senha**: `Sen@c2023`
**Host**: `localhost`
**Porta**: `5432`

---

## 🗄️ BANCO DE DADOS

```sql
CREATE DATABASE sanscritinho
  WITH ENCODING 'UTF8'
  LC_COLLATE 'C'
  LC_CTYPE 'C'
  TEMPLATE template0;
```

---

## 📋 TABELA PRINCIPAL: `alunos`

### Estrutura
```sql
CREATE TABLE alunos (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  matricula VARCHAR(20) NOT NULL UNIQUE,
  nota NUMERIC(3,1) NOT NULL CHECK (nota >= 0 AND nota <= 10),
  observacoes VARCHAR(500),
  data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Detalhes dos Campos

| Campo | Tipo | Tamanho | Restrições | Descrição |
|-------|------|---------|-----------|-----------|
| `id` | BIGSERIAL | Auto | PRIMARY KEY | Identificador único, auto-incremento |
| `nome` | VARCHAR | 150 | NOT NULL | Nome completo do aluno |
| `matricula` | VARCHAR | 20 | NOT NULL, UNIQUE | Matrícula única por aluno |
| `nota` | NUMERIC | 3,1 | NOT NULL, CHECK (0-10) | Nota entre 0 e 10 (uma casa decimal) |
| `observacoes` | VARCHAR | 500 | NULLABLE | Observações adicionais (opcional) |
| `data_cadastro` | TIMESTAMP | - | NOT NULL, DEFAULT NOW() | Data/hora de criação |
| `data_atualizacao` | TIMESTAMP | - | NOT NULL, DEFAULT NOW() | Data/hora de última atualização |

---

## 🔑 ÍNDICES

### Índice 1: Busca rápida por matrícula
```sql
CREATE UNIQUE INDEX idx_alunos_matricula 
  ON alunos(matricula);
```
- **Por quê**: Matrícula é única e buscada frequentemente
- **Tipo**: UNIQUE (garante unicidade)

### Índice 2: Busca por nota
```sql
CREATE INDEX idx_alunos_nota 
  ON alunos(nota DESC);
```
- **Por quê**: Buscas por intervalo de nota são frequentes
- **Tipo**: DESC (notas altas aparecem primeiro)

### Índice 3: Busca por nome
```sql
CREATE INDEX idx_alunos_nome 
  ON alunos(nome);
```
- **Por quê**: Busca por nome (LIKE) precisa de índice
- **Tipo**: ASC (ordem alfabética)

### Índice 4: Busca por intervalo de nota
```sql
CREATE INDEX idx_alunos_nota_intervalo 
  ON alunos(nota ASC, data_atualizacao DESC);
```
- **Por quê**: Filtro por intervalo de nota é principal
- **Tipo**: Índice composto (nota + data)

---

## 📝 SCRIPT COMPLETO (COPIAR E COLAR NO DBEAVER)

```sql
-- 1. Criar banco de dados
CREATE DATABASE sanscritinho
  WITH ENCODING 'UTF8'
  LC_COLLATE 'C'
  LC_CTYPE 'C'
  TEMPLATE template0;

-- 2. Conectar ao banco (no DBeaver, selecione "sanscritinho" como banco atual)

-- 3. Criar tabela
CREATE TABLE alunos (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(150) NOT NULL,
  matricula VARCHAR(20) NOT NULL UNIQUE,
  nota NUMERIC(3,1) NOT NULL CHECK (nota >= 0 AND nota <= 10),
  observacoes VARCHAR(500),
  data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 4. Criar índices
CREATE UNIQUE INDEX idx_alunos_matricula 
  ON alunos(matricula);

CREATE INDEX idx_alunos_nota 
  ON alunos(nota DESC);

CREATE INDEX idx_alunos_nome 
  ON alunos(nome);

CREATE INDEX idx_alunos_nota_intervalo 
  ON alunos(nota ASC, data_atualizacao DESC);

-- 5. Inserir dados de teste
INSERT INTO alunos (nome, matricula, nota, observacoes) VALUES
('João Silva', '2024001', 8.5, 'Desempenho excelente'),
('Maria Santos', '2024002', 9.0, 'Aluna destaque'),
('Pedro Costa', '2024003', 6.5, 'Necessita reforço'),
('Ana Oliveira', '2024004', 7.8, 'Bom desempenho'),
('Carlos Souza', '2024005', 5.2, 'Abaixo da média'),
('Beatriz Lima', '2024006', 9.5, 'Excelente aluna'),
('David Mendes', '2024007', 7.0, 'Médio desempenho'),
('Elena Rocha', '2024008', 8.0, 'Bom aluno'),
('Felipe Gomes', '2024009', 6.0, 'Precisa melhorar'),
('Gabriela Martins', '2024010', 8.8, 'Muito bom');

-- 6. Verificar dados
SELECT COUNT(*) as total_alunos FROM alunos;
SELECT * FROM alunos ORDER BY nota DESC;
```

---

## 🔄 OPERAÇÕES COMUNS NO DBEAVER

### Ver todas as tabelas
```sql
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';
```

### Ver estrutura da tabela
```sql
\d alunos
```
Ou no DBeaver: Botão direito > Propriedades

### Ver índices
```sql
SELECT indexname, indexdef 
FROM pg_indexes 
WHERE tablename = 'alunos';
```

### Ver dados com paginação
```sql
SELECT * FROM alunos 
ORDER BY nota DESC 
LIMIT 10 OFFSET 0;
```

### Contar alunos por faixa de nota
```sql
SELECT 
  CASE 
    WHEN nota >= 9.0 THEN 'Excelente'
    WHEN nota >= 8.0 THEN 'Muito Bom'
    WHEN nota >= 7.0 THEN 'Bom'
    WHEN nota >= 6.0 THEN 'Satisfatório'
    ELSE 'Insuficiente'
  END as faixa,
  COUNT(*) as quantidade
FROM alunos
GROUP BY faixa
ORDER BY faixa DESC;
```

---

## 📊 ESPECIFICAÇÕES TÉCNICAS

### PostgreSQL
- **Versão**: 12+
- **Encoding**: UTF-8
- **Collation**: C (padrão)

### Tipos de Dados Usados
| Tipo | Uso | Exemplo |
|------|-----|---------|
| BIGSERIAL | IDs auto-incremento | id: 1, 2, 3... |
| VARCHAR(n) | Texto com limite | nome: "João Silva" |
| NUMERIC(p,s) | Decimal com precisão | nota: 8.5 |
| TIMESTAMP | Data e hora | 2024-08-18 20:27:50 |

### Constraints (Restrições)
| Constraint | Campo | Propósito |
|-----------|-------|----------|
| PRIMARY KEY | id | Garante unicidade |
| UNIQUE | matricula | Uma matrícula por aluno |
| NOT NULL | nome, matricula, nota | Campos obrigatórios |
| CHECK | nota | Nota entre 0 e 10 |
| DEFAULT | data_cadastro, data_atualizacao | Preenche automaticamente |

---

## ✅ CHECKLIST DBEAVER

- [ ] PostgreSQL instalado e rodando
- [ ] Criar conexão com `localhost:5432`
- [ ] Usuário `postgres` com senha `Sen@c2023`
- [ ] Executar script para criar banco `sanscritinho`
- [ ] Selecionar banco `sanscritinho`
- [ ] Criar tabela `alunos` com todos os campos
- [ ] Criar 4 índices (matricula, nota, nome, intervalo)
- [ ] Inserir 10 registros de teste
- [ ] Verificar: SELECT COUNT(*) FROM alunos; (deve retornar 10)
- [ ] Testar: SELECT * FROM alunos WHERE nota BETWEEN 7 AND 9; (deve retornar 7 registros)

---

## 🔗 RELACIONAMENTO COM SPRING BOOT

### Como o Spring Boot vê a tabela:

**Java Entity** (Aluno.java):
```java
@Entity
@Table(name = "alunos")
public class Aluno {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "nome", length = 150, nullable = false)
  private String nome;
  
  @Column(name = "matricula", length = 20, nullable = false, unique = true)
  private String matricula;
  
  @Column(name = "nota", nullable = false)
  private BigDecimal nota;
  
  @Column(name = "observacoes", length = 500)
  private String observacoes;
  
  @Column(name = "data_cadastro", nullable = false, updatable = false)
  private LocalDateTime dataCadastro;
  
  @Column(name = "data_atualizacao", nullable = false)
  private LocalDateTime dataAtualizacao;
}
```

**Mapeamento**:
- `id` (BIGSERIAL) → `@GeneratedValue`
- `nome` (VARCHAR) → `@Column`
- `matricula` (VARCHAR UNIQUE) → `@Column(unique = true)`
- `nota` (NUMERIC) → `BigDecimal`
- Timestamps → `LocalDateTime`

---

## 📌 PONTOS IMPORTANTES

1. **Encoding UTF-8**: Suporta acentos e caracteres especiais
2. **Nota com 1 casa decimal**: Use `NUMERIC(3,1)` não FLOAT
3. **Timestamps automáticos**: Gerados automaticamente pelo banco
4. **Índices**: Essenciais para performance em buscas por nota
5. **Matrícula UNIQUE**: Garante que cada aluno tem matrícula única

---

## 🚀 PRÓXIMOS PASSOS

1. ✅ Copiar script completo acima
2. ✅ Colar no DBeaver (File > New > SQL Script)
3. ✅ Executar cada bloco (CTRL + ENTER)
4. ✅ Verificar dados (SELECT * FROM alunos)
5. ✅ Iniciar aplicação Spring Boot
6. ✅ Testar API

---

*Guia criado em: 2026-08-18*
*Para: Configuração via DBeaver*
*Status: Pronto para usar*
