# Arquitetura da Aplicação Sanscritinho

## Visão Geral

A aplicação segue a arquitetura em **camadas** com separação de responsabilidades, facilitando manutenção e testes.

```
┌─────────────────────────────────────────────────────────────────────┐
│                        CAMADA DE APRESENTAÇÃO                        │
│                    (Presentation Layer)                              │
│                                                                      │
│  ┌──────────────────────┐      ┌─────────────────────────────────┐ │
│  │   AlunoController    │      │  GlobalExceptionHandler         │ │
│  │   REST Endpoints     │      │  Tratamento de Erros            │ │
│  └──────────────────────┘      └─────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                     CAMADA DE APLICAÇÃO                              │
│                  (Application Layer)                                 │
│                                                                      │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │                    AlunoService                              │  │
│  │  - Lógica de negócio                                         │  │
│  │  - Validações                                               │  │
│  │  - Transformação de DTOs                                    │  │
│  │  - Orquestração de operações                                │  │
│  └──────────────────────────────────────────────────────────────┘  │
│                                                                      │
│  DTOs:                                                              │
│  ├─ AlunoRequestDTO (entrada)                                       │
│  └─ AlunoResponseDTO (saída)                                        │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                   CAMADA DE INFRAESTRUTURA                           │
│                  (Infrastructure Layer)                              │
│                                                                      │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │                  AlunoRepository                             │  │
│  │  - Consultas customizadas                                    │  │
│  │  - Acesso aos dados                                          │  │
│  │  - JPA Queries                                               │  │
│  └──────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                    CAMADA DE DOMÍNIO                                 │
│                   (Domain Layer)                                     │
│                                                                      │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │                   Aluno Entity                               │  │
│  │  - Classe JPA anotada                                        │  │
│  │  - Representação do objeto de negócio                        │  │
│  │  - Ciclo de vida de persistência                             │  │
│  └──────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────┐
│                      BANCO DE DADOS                                  │
│                                                                      │
│  ┌──────────────────────────────────────────────────────────────┐  │
│  │              PostgreSQL (sanscritinho)                       │  │
│  │                                                              │  │
│  │  Tabela: alunos                                              │  │
│  │  ├─ id (PK)                                                  │  │
│  │  ├─ nome                                                     │  │
│  │  ├─ matricula (UNIQUE)                                       │  │
│  │  ├─ nota                                                     │  │
│  │  ├─ data_cadastro                                            │  │
│  │  ├─ data_atualizacao                                         │  │
│  │  └─ observacoes                                              │  │
│  └──────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────┘
```

## Fluxo de uma Requisição

### Exemplo: Criar um Aluno

```
1. Cliente HTTP
        │
        ▼
   POST /api/v1/alunos
        │
        ▼
   AlunoController.criar()
        │ - Recebe AlunoRequestDTO
        │ - Valida entrada
        ▼
   AlunoService.criarAluno()
        │ - Executa lógica de negócio
        │ - Verifica se matrícula existe
        │ - Cria instância de Aluno
        │ - Chama repository.save()
        ▼
   AlunoRepository.save()
        │ - Prepara instrução SQL INSERT
        ▼
   PostgreSQL
        │ - Insere dados na tabela
        │ - Retorna registro com ID
        ▼
   AlunoService (mapping)
        │ - Converte Aluno para AlunoResponseDTO
        ▼
   AlunoController
        │ - Retorna ResponseEntity com status 201
        ▼
   Cliente HTTP
        └─ Resposta com dados do aluno criado
```

### Exemplo: Consultar Alunos por Nota

```
1. Cliente HTTP
        │
        ▼
   GET /api/v1/alunos/buscar/intervalo-nota?notaMinima=7.0&notaMaxima=9.0
        │
        ▼
   AlunoController.buscarPorIntervaloNota()
        │ - Extrai parâmetros
        │ - Cria objeto Pageable
        ▼
   AlunoService.listarAlunosPorIntervaloNota()
        │ - Valida parâmetros
        │ - Chama repository
        ▼
   AlunoRepository.findByNotaBetween()
        │ - Query customizada com @Query
        │ - SELECT a FROM Aluno a WHERE nota BETWEEN :min AND :max
        ▼
   PostgreSQL
        │ - Executa query
        │ - Retorna Page<Aluno>
        ▼
   AlunoService (mapping)
        │ - Converte List<Aluno> para List<AlunoResponseDTO>
        │ - Mantém estrutura de Page
        ▼
   AlunoController
        │ - Retorna ResponseEntity com status 200
        ▼
   Cliente HTTP
        └─ Resposta com lista paginada de alunos
```

## Estrutura de Pacotes

```
br.com.sanscritinho/
│
├── domain/
│   └── model/
│       └── Aluno.java              # Entidade JPA - Representa a tabela alunos
│
├── application/
│   ├── dto/
│   │   ├── AlunoRequestDTO.java    # DTO para receber dados do cliente
│   │   └── AlunoResponseDTO.java   # DTO para enviar dados ao cliente
│   │
│   └── service/
│       └── AlunoService.java       # Lógica de negócio principal
│
├── infrastructure/
│   └── repository/
│       └── AlunoRepository.java    # Acesso a dados com JPA
│
├── presentation/
│   ├── controller/
│   │   └── AlunoController.java    # Endpoints REST
│   │
│   └── exception/
│       ├── GlobalExceptionHandler.java  # Interceptor de exceções
│       └── ErrorResponse.java           # Modelo de resposta de erro
│
└── SanscritinhoApplication.java    # Classe main
```

## Padrões de Design Utilizados

### 1. **Camadas (Layered Architecture)**
- Separação clara de responsabilidades
- Cada camada conhece apenas a camada abaixo
- Facilita testes unitários e manutenção

### 2. **Repository Pattern**
- Abstração do acesso a dados
- Interface `AlunoRepository` estende `JpaRepository`
- Métodos customizados com `@Query`

### 3. **Service Pattern**
- Lógica de negócio centralizada
- Transações gerenciadas com `@Transactional`
- Transformação entre DTOs e Entities

### 4. **DTO (Data Transfer Object)**
- Separação entre objeto de banco e transferência
- Controle sobre quais campos são expostos
- Formato JSON específico por endpoint

### 5. **Global Exception Handler**
- Tratamento centralizado de exceções
- Respostas padronizadas
- Logging automático

### 6. **Builder Pattern**
- Construção de objetos com Lombok
- Código mais limpo e legível

## Tecnologias e Frameworks

| Tecnologia | Versão | Propósito |
|-----------|--------|----------|
| Spring Boot | 4.1.0 | Framework principal |
| Spring Data JPA | 4.1.0 | Persistência de dados |
| PostgreSQL | - | Banco de dados relacional |
| Lombok | Última | Redução de boilerplate code |
| Jackson | Integrado | Serialização JSON |
| Spring Security | 4.1.0 | Autenticação e autorização |

## Configuração Spring

### Anotações Utilizadas

#### Na Entidade (Aluno.java)
```java
@Entity              // Marca como entidade JPA
@Table(name = "alunos")  // Nome da tabela
@Data                // Lombok: getters, setters, equals, hashCode, toString
@NoArgsConstructor   // Lombok: construtor sem argumentos
@AllArgsConstructor  // Lombok: construtor com todos os argumentos
@Builder             // Lombok: padrão builder

@Id                  // Chave primária
@GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremento
@Column              // Mapear coluna
@PrePersist          // Hook antes de persistir
@PreUpdate           // Hook antes de atualizar
```

#### Na Classe de Serviço
```java
@Service             // Component specializado para lógica de negócio
@RequiredArgsConstructor  // Lombok: construtor para injeção de dependências
@Slf4j               // Lombok: logger automático
@Transactional       // Gerenciamento de transações
```

#### No Controlador
```java
@RestController      // Combina @Controller + @ResponseBody
@RequestMapping      // URL base para a classe
@RequiredArgsConstructor  // Injeção de dependências

@PostMapping         // HTTP POST
@GetMapping          // HTTP GET
@PutMapping          // HTTP PUT
@DeleteMapping       // HTTP DELETE

@PathVariable        // Parâmetro na URL
@RequestParam        // Parâmetro query string
@RequestBody         // Corpo da requisição JSON
```

## Validações e Regras de Negócio

1. **Matrícula Única**
   - Verificação antes de criar novo aluno
   - Constraint `UNIQUE` no banco

2. **Nota Válida**
   - Tipo Double permite valores decimais
   - Validação no serviço (pode ser expandida)

3. **Campos Obrigatórios**
   - Nome: não nulo
   - Matrícula: não nulo e única
   - Nota: não nula

4. **Controle de Data**
   - `dataCadastro`: definida automaticamente no `@PrePersist`
   - `dataAtualizacao`: atualizada no `@PreUpdate`

## Performance e Otimizações

1. **Paginação**
   - Todos os endpoints list retornam Page<T>
   - Reduz volume de dados trafegado

2. **Transações Read-Only**
   - `@Transactional(readOnly = true)` em consultas
   - Otimiza o Hibernate

3. **Queries Customizadas**
   - Use `@Query` para consultas complexas
   - Evita N+1 queries

4. **Lazy Loading**
   - Não aplicável nesta versão (sem relacionamentos)
   - Pronto para expansão

## Fluxo de Desenvolvimento Futuro

### Adicionar Validação com Jakarta Validation
```java
@NotBlank(message = "Nome é obrigatório")
private String nome;

@Min(value = 0, message = "Nota não pode ser negativa")
@Max(value = 10, message = "Nota não pode ser maior que 10")
private Double nota;
```

### Adicionar Relacionamentos
```java
@ManyToOne
@JoinColumn(name = "disciplina_id")
private Disciplina disciplina;
```

### Adicionar Auditoria
```java
@EntityListeners(AuditingEntityListener.class)
@CreatedBy
@CreatedDate
@LastModifiedBy
@LastModifiedDate
```

## Como Executar Testes

```bash
# Compilar
mvn clean compile

# Executar testes
mvn test

# Build completo
mvn clean package

# Executar aplicação
mvn spring-boot:run
```
