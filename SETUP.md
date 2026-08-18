# Guia de Setup - Sanscritinho

## Pré-requisitos

- **Java 16+** instalado
- **PostgreSQL 12+** instalado e rodando
- **Maven** ou Maven Wrapper (incluído)
- **Git** (opcional)

## Passo a Passo

### 1. Criar Banco de Dados

Abra o terminal ou pgAdmin e execute:

```sql
-- Criar database
CREATE DATABASE sanscritinho;

-- Conectar ao database
\c sanscritinho

-- (Opcional) Criar usuário específico
CREATE USER sanscritinho_user WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE sanscritinho TO sanscritinho_user;
```

### 2. Configurar Conexão com PostgreSQL

Edite o arquivo `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sanscritinho
    username: postgres          # ou seu usuário
    password: sua_senha        # sua senha
    driver-class-name: org.postgresql.Driver
```

### 3. Compilar o Projeto

No diretório raiz do projeto:

```bash
# Windows
.\mvnw.cmd clean compile

# Linux/Mac
./mvnw clean compile
```

### 4. Executar a Aplicação

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

Você deve ver na saída:
```
Started SanscritinhoApplication in X.XXX seconds
```

### 5. Testar a Aplicação

Use curl, Postman, ou o arquivo `EXEMPLOS_CURL.md`:

```bash
# Criar um aluno
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "João Silva",
    "matricula": "2024001",
    "nota": 8.5,
    "observacoes": "Teste inicial"
  }'

# Listar alunos
curl -X GET "http://localhost:8080/api/v1/alunos" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

## Credenciais Padrão

- **Usuário**: `admin`
- **Senha**: `admin`

Você pode mudar em `application.yaml`:

```yaml
spring:
  security:
    user:
      name: seu_usuario
      password: sua_senha
```

## Estrutura de Tabelas

A aplicação cria automaticamente a tabela `alunos`:

```sql
CREATE TABLE alunos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    nota DOUBLE PRECISION NOT NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacoes VARCHAR(500)
);

-- Índice para melhor performance em buscas por nota
CREATE INDEX idx_alunos_nota ON alunos(nota);

-- Índice para buscas por matrícula
CREATE INDEX idx_alunos_matricula ON alunos(matricula);
```

## Troubleshooting

### Erro: Connection refused

```
java.sql.SQLException: Unable to connect to PostgreSQL
```

**Solução**:
- Verifique se PostgreSQL está rodando
- Verifique URL de conexão no `application.yaml`
- Verifique credenciais (usuário/senha)

### Erro: Database doesn't exist

```
org.postgresql.util.PSQLException: ERROR: database "sanscritinho" does not exist
```

**Solução**:
- Crie o database: `CREATE DATABASE sanscritinho;`

### Erro: Permission denied

```
org.postgresql.util.PSQLException: FATAL: role "usuario" does not exist
```

**Solução**:
- Verifique o usuário do PostgreSQL configurado
- Padrão é `postgres`

### Porta já está em uso

```
Address already in use: bind
```

**Solução**:
- Altere a porta em `application.yaml`:
```yaml
server:
  port: 8081  # ou outra porta livre
```

### Erro de compilação com Java 25

```
error: release version 25 not supported
```

**Solução**:
- Já foi corrigido no `pom.xml`
- Seu Java é versão 16, modificado para: `<java.version>16</java.version>`

## Verificar Dados no PostgreSQL

Conecte-se ao banco e execute:

```sql
-- Ver todos os alunos
SELECT * FROM alunos;

-- Ver alunos ordenados por nota
SELECT * FROM alunos ORDER BY nota DESC;

-- Ver alunos com nota >= 7
SELECT * FROM alunos WHERE nota >= 7 ORDER BY nota DESC;

-- Contar alunos
SELECT COUNT(*) as total_alunos FROM alunos;

-- Média de notas
SELECT AVG(nota) as media_notas FROM alunos;

-- Aluno com melhor nota
SELECT * FROM alunos ORDER BY nota DESC LIMIT 1;
```

## Ambiente de Desenvolvimento

### VSCode

Extensões recomendadas:
- Extension Pack for Java
- Spring Boot Extension Pack
- REST Client

### IntelliJ IDEA

- Abre automaticamente como projeto Maven
- Suporte completo a Spring Boot

### Eclipse

- Instale Eclipse IDE for Java Developers
- Plugin Maven already included

## Variáveis de Ambiente (Opcional)

Você pode usar variáveis de ambiente:

```bash
# Windows (PowerShell)
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/sanscritinho"
$env:SPRING_DATASOURCE_USERNAME = "postgres"
$env:SPRING_DATASOURCE_PASSWORD = "sua_senha"

# Linux/Mac
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/sanscritinho
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=sua_senha
```

Depois execute:
```bash
./mvnw spring-boot:run
```

## Build e Deployment

### Gerar JAR executável

```bash
.\mvnw.cmd clean package
```

Arquivo gerado: `target/sanscritinho-0.0.1-SNAPSHOT.jar`

### Executar JAR

```bash
java -jar target/sanscritinho-0.0.1-SNAPSHOT.jar
```

### Com variáveis de ambiente

```bash
java -jar target/sanscritinho-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:postgresql://localhost:5432/sanscritinho \
  --spring.datasource.username=postgres \
  --spring.datasource.password=senha
```

## Próximos Passos

1. Integre com um frontend (Angular, React, Vue)
2. Adicione autenticação JWT
3. Implemente testes unitários
4. Configure CI/CD com GitHub Actions
5. Deploy em servidor (Heroku, AWS, DigitalOcean)

## Documentação

- `README.md` - Overview da aplicação
- `ARQUITETURA.md` - Descrição técnica
- `EXEMPLOS_CURL.md` - Exemplos de requisições HTTP

## Suporte

Para dúvidas sobre:
- **Spring Boot**: https://spring.io/
- **PostgreSQL**: https://www.postgresql.org/
- **JPA/Hibernate**: https://hibernate.org/
