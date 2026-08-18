# 📚 Sanscritinho - Referência Rápida

## 🚀 Início Rápido

### 1. Setup
```bash
# Crie banco de dados PostgreSQL
CREATE DATABASE sanscritinho;

# Edite application.yaml com suas credenciais
# Compile e execute
.\mvnw.cmd spring-boot:run
```

### 2. Criar Aluno
```bash
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{"nome":"João","matricula":"2024001","nota":8.5}'
```

### 3. Buscar por Notas
```bash
# Nota >= 7.0
curl "http://localhost:8080/api/v1/alunos/buscar/nota-minima?nota=7.0"

# Nota entre 7.0 e 9.0
curl "http://localhost:8080/api/v1/alunos/buscar/intervalo-nota?notaMinima=7.0&notaMaxima=9.0"
```

## 📁 Estrutura

```
sanscritinho/
├── src/main/java/br/com/sanscritinho/
│   ├── domain/model/
│   │   └── Aluno              ← Entidade JPA
│   ├── application/
│   │   ├── dto/               ← DTOs para requisição/resposta
│   │   └── service/           ← Lógica de negócio
│   ├── infrastructure/
│   │   └── repository/        ← Acesso a dados
│   └── presentation/
│       ├── controller/        ← Endpoints REST
│       └── exception/         ← Tratamento de erros
├── src/test/java/
│   └── application/service/   ← Testes unitários
└── src/main/resources/
    └── application.yaml       ← Configuração
```

## 🔌 API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/alunos` | Criar aluno |
| GET | `/api/v1/alunos` | Listar todos |
| GET | `/api/v1/alunos/{id}` | Obter por ID |
| GET | `/api/v1/alunos/matricula/{matricula}` | Obter por matrícula |
| PUT | `/api/v1/alunos/{id}` | Atualizar |
| DELETE | `/api/v1/alunos/{id}` | Deletar |
| GET | `/api/v1/alunos/buscar/nome` | Buscar por nome |
| GET | `/api/v1/alunos/buscar/nota-minima` | Buscar nota >= X |
| GET | `/api/v1/alunos/buscar/nota-maxima` | Buscar nota <= X |
| GET | `/api/v1/alunos/buscar/intervalo-nota` | Buscar nota entre X e Y |

## 📊 Modelo de Dados

### Tabela: alunos
- `id` (PK, auto-increment)
- `nome` (VARCHAR 150, NOT NULL)
- `matricula` (VARCHAR 20, UNIQUE, NOT NULL)
- `nota` (DOUBLE, NOT NULL)
- `data_cadastro` (TIMESTAMP)
- `data_atualizacao` (TIMESTAMP)
- `observacoes` (VARCHAR 500)

## 🔐 Segurança

- **Usuário**: `admin`
- **Senha**: `admin`
- Autenticação: Basic Auth (HTTP)

Configure em `application.yaml`:
```yaml
spring:
  security:
    user:
      name: seu_usuario
      password: sua_senha
```

## 🧪 Testes

```bash
# Executar testes unitários
.\mvnw.cmd test

# Executar com cobertura
.\mvnw.cmd test jacoco:report
```

## 📦 Build

```bash
# Criar JAR
.\mvnw.cmd clean package

# Executar JAR
java -jar target/sanscritinho-0.0.1-SNAPSHOT.jar
```

## 📝 Exemplos de Payload

### Criar/Atualizar Aluno
```json
{
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "observacoes": "Bom aluno"
}
```

### Resposta
```json
{
  "id": 1,
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "dataCadastro": "2024-08-18 20:15:30",
  "dataAtualizacao": "2024-08-18 20:15:30",
  "observacoes": "Bom aluno"
}
```

## 🎯 Parâmetros de Paginação

```
?page=0&size=10&sort=nota,desc
```

- `page` (padrão: 0) - Número da página
- `size` (padrão: 20) - Itens por página
- `sort` (padrão: id,asc) - Campo e direção

## 🛠️ Tecnologias

- **Java 16**
- **Spring Boot 4.1.0**
- **Spring Data JPA**
- **PostgreSQL 12+**
- **Lombok**
- **Maven**

## 📖 Documentação Completa

- `README.md` - Documentação completa
- `SETUP.md` - Guia de instalação
- `ARQUITETURA.md` - Detalhes técnicos
- `EXEMPLOS_CURL.md` - Exemplos de requisições

## 🔗 Links Úteis

- [Spring Boot Docs](https://spring.io/)
- [PostgreSQL Docs](https://www.postgresql.org/)
- [JPA/Hibernate](https://hibernate.org/)
- [Postman](https://www.postman.com/)

## ⚠️ Troubleshooting

| Erro | Solução |
|------|---------|
| Connection refused | PostgreSQL não está rodando |
| Database doesn't exist | Execute: `CREATE DATABASE sanscritinho;` |
| Port already in use | Mude a porta: `server.port: 8081` |
| Release version not supported | Ajuste Java version no pom.xml |

## 📋 Checklist de Deploy

- [ ] Banco de dados criado
- [ ] Credenciais configuradas
- [ ] Aplicação compilada
- [ ] Testes passando
- [ ] JAR gerado
- [ ] Documentação atualizada

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

**Última atualização**: 2024-08-18
