# Sanscritinho - API de Gestão de Alunos

API REST para gerenciamento de notas de alunos com filtros por nota e consulta avançada.

## Estrutura do Projeto

```
sanscritinho/
├── src/main/java/br/com/sanscritinho/
│   ├── domain/
│   │   └── model/
│   │       └── Aluno.java                    # Entidade JPA
│   ├── application/
│   │   ├── dto/
│   │   │   ├── AlunoRequestDTO.java          # DTO para requisições
│   │   │   └── AlunoResponseDTO.java         # DTO para respostas
│   │   └── service/
│   │       └── AlunoService.java             # Lógica de negócio
│   ├── infrastructure/
│   │   └── repository/
│   │       └── AlunoRepository.java          # Acesso a dados
│   └── presentation/
│       ├── controller/
│       │   └── AlunoController.java          # Endpoints REST
│       └── exception/
│           ├── GlobalExceptionHandler.java   # Tratamento global de exceções
│           └── ErrorResponse.java            # Modelo de erro
└── src/main/resources/
    └── application.yaml                      # Configuração da aplicação
```

## Configuração

### Pré-requisitos

- Java 25 ou superior
- Maven
- PostgreSQL 12 ou superior

### Variáveis de Ambiente

Edite o arquivo `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sanscritinho
    username: postgres
    password: sua_senha
```

### Criar Banco de Dados

```sql
CREATE DATABASE sanscritinho;
```

## Endpoints da API

### Base URL
```
http://localhost:8080/api/v1/alunos
```

### 1. Criar Aluno
**POST** `/api/v1/alunos`

```json
{
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "observacoes": "Desempenho excelente"
}
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "dataCadastro": "2024-08-18 20:15:30",
  "dataAtualizacao": "2024-08-18 20:15:30",
  "observacoes": "Desempenho excelente"
}
```

### 2. Obter Aluno por ID
**GET** `/api/v1/alunos/{id}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "dataCadastro": "2024-08-18 20:15:30",
  "dataAtualizacao": "2024-08-18 20:15:30",
  "observacoes": "Desempenho excelente"
}
```

### 3. Obter Aluno por Matrícula
**GET** `/api/v1/alunos/matricula/{matricula}`

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "dataCadastro": "2024-08-18 20:15:30",
  "dataAtualizacao": "2024-08-18 20:15:30",
  "observacoes": "Desempenho excelente"
}
```

### 4. Atualizar Aluno
**PUT** `/api/v1/alunos/{id}`

```json
{
  "nome": "João Silva Santos",
  "matricula": "2024001",
  "nota": 9.0,
  "observacoes": "Excelente progresso"
}
```

**Resposta (200 OK):**
```json
{
  "id": 1,
  "nome": "João Silva Santos",
  "matricula": "2024001",
  "nota": 9.0,
  "dataCadastro": "2024-08-18 20:15:30",
  "dataAtualizacao": "2024-08-18 20:20:45",
  "observacoes": "Excelente progresso"
}
```

### 5. Deletar Aluno
**DELETE** `/api/v1/alunos/{id}`

**Resposta (204 No Content):**
```
(sem corpo)
```

### 6. Listar Todos os Alunos (com paginação)
**GET** `/api/v1/alunos?page=0&size=10&sort=nome,asc`

**Resposta (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "nome": "João Silva",
      "matricula": "2024001",
      "nota": 8.5,
      "dataCadastro": "2024-08-18 20:15:30",
      "dataAtualizacao": "2024-08-18 20:15:30",
      "observacoes": "Desempenho excelente"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

### 7. Buscar Alunos por Nome
**GET** `/api/v1/alunos/buscar/nome?nome=João&page=0&size=10`

**Resposta (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "nome": "João Silva",
      "matricula": "2024001",
      "nota": 8.5,
      "dataCadastro": "2024-08-18 20:15:30",
      "dataAtualizacao": "2024-08-18 20:15:30",
      "observacoes": "Desempenho excelente"
    }
  ],
  "pageable": {...},
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

### 8. Buscar Alunos com Nota Mínima
**GET** `/api/v1/alunos/buscar/nota-minima?nota=7.0&page=0&size=10`

Retorna alunos com nota >= especificada, ordenados por nota decrescente.

### 9. Buscar Alunos com Nota Máxima
**GET** `/api/v1/alunos/buscar/nota-maxima?nota=8.0&page=0&size=10`

Retorna alunos com nota <= especificada, ordenados por nota crescente.

### 10. Buscar Alunos por Intervalo de Nota
**GET** `/api/v1/alunos/buscar/intervalo-nota?notaMinima=7.0&notaMaxima=9.0&page=0&size=10`

Retorna alunos com notas entre os valores especificados, ordenados por nota decrescente.

## Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | OK - Requisição bem-sucedida |
| 201 | Created - Recurso criado com sucesso |
| 204 | No Content - Operação bem-sucedida sem retorno |
| 400 | Bad Request - Requisição inválida |
| 404 | Not Found - Recurso não encontrado |
| 500 | Internal Server Error - Erro no servidor |

## Tratamento de Erros

Erro de validação:
```json
{
  "status": 400,
  "message": "Matrícula já existe: 2024001",
  "timestamp": "2024-08-18 20:15:30",
  "path": "/api/v1/alunos"
}
```

Erro de recurso não encontrado:
```json
{
  "status": 400,
  "message": "Aluno não encontrado: 999",
  "timestamp": "2024-08-18 20:15:30",
  "path": "/api/v1/alunos/999"
}
```

## Compilar e Executar

### Compilar
```bash
mvn clean compile
```

### Executar testes
```bash
mvn test
```

### Executar aplicação
```bash
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package
java -jar target/sanscritinho-0.0.1-SNAPSHOT.jar
```

## Tecnologias

- **Spring Boot** 4.1.0
- **Spring Data JPA** - ORM
- **PostgreSQL** - Banco de dados
- **Lombok** - Geração automática de getters/setters
- **Spring Security** - Segurança
- **Maven** - Gerenciador de dependências

## Modelagem de Dados

### Tabela: alunos

| Coluna | Tipo | Constraints |
|--------|------|-------------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT |
| nome | VARCHAR(150) | NOT NULL |
| matricula | VARCHAR(20) | NOT NULL, UNIQUE |
| nota | DOUBLE | NOT NULL |
| data_cadastro | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| data_atualizacao | TIMESTAMP | NOT NULL, DEFAULT CURRENT_TIMESTAMP |
| observacoes | VARCHAR(500) | NULL |

## Segurança

A aplicação usa Spring Security com autenticação básica:
- **Usuário:** admin
- **Senha:** admin

Configure conforme necessário no arquivo `application.yaml`.

## Logs

Os logs são configurados para:
- **br.com.sanscritinho**: INFO
- **org.springframework**: WARN
- **org.hibernate**: WARN

Altere o nível de log no arquivo `application.yaml`.

## Paginação

Todos os endpoints que retornam listas suportam paginação:
- `page` (padrão: 0) - Número da página
- `size` (padrão: 20) - Quantidade de itens por página
- `sort` (padrão: id,asc) - Campo e direção da ordenação

**Exemplo:**
```
GET /api/v1/alunos?page=0&size=5&sort=nota,desc
```

## Autor

Desenvolvido com Spring Boot 4.1.0
