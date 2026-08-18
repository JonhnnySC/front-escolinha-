# Exemplos de Uso da API Sanscritinho

Este arquivo contém exemplos de requisições HTTP para testar a API usando `curl`.

## Pré-requisitos

- Aplicação rodando em `http://localhost:8080`
- PostgreSQL configurado e rodando
- Credenciais: `admin:admin`

## Exemplos de Requisições

### 1. Criar um Aluno

```bash
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "João Silva",
    "matricula": "2024001",
    "nota": 8.5,
    "observacoes": "Desempenho excelente"
  }'
```

### 2. Criar Múltiplos Alunos

```bash
# Aluno 2
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "Maria Santos",
    "matricula": "2024002",
    "nota": 9.0,
    "observacoes": "Excelente aluna"
  }'

# Aluno 3
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "Pedro Costa",
    "matricula": "2024003",
    "nota": 6.5,
    "observacoes": "Precisa melhorar"
  }'

# Aluno 4
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "Ana Oliveira",
    "matricula": "2024004",
    "nota": 7.8,
    "observacoes": "Bom desempenho"
  }'

# Aluno 5
curl -X POST http://localhost:8080/api/v1/alunos \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "Carlos Mendes",
    "matricula": "2024005",
    "nota": 5.2,
    "observacoes": "Abaixo da média"
  }'
```

### 3. Obter Aluno por ID

```bash
curl -X GET http://localhost:8080/api/v1/alunos/1 \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 4. Obter Aluno por Matrícula

```bash
curl -X GET "http://localhost:8080/api/v1/alunos/matricula/2024001" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 5. Listar Todos os Alunos (com paginação)

```bash
# Primeira página
curl -X GET "http://localhost:8080/api/v1/alunos?page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Ordenado por nota descrescente
curl -X GET "http://localhost:8080/api/v1/alunos?page=0&size=10&sort=nota,desc" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Ordenado por nome crescente
curl -X GET "http://localhost:8080/api/v1/alunos?page=0&size=10&sort=nome,asc" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 6. Buscar Alunos por Nome

```bash
# Buscar por nome parcial
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nome?nome=João&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Buscar por outra parte do nome
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nome?nome=Silva&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 7. Buscar Alunos com Nota Mínima

```bash
# Alunos com nota >= 7.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nota-minima?nota=7.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Alunos com nota >= 8.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nota-minima?nota=8.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 8. Buscar Alunos com Nota Máxima

```bash
# Alunos com nota <= 7.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nota-maxima?nota=7.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Alunos com nota <= 6.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/nota-maxima?nota=6.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 9. Buscar Alunos por Intervalo de Nota (MAIS USADO)

```bash
# Alunos com notas entre 7.0 e 9.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/intervalo-nota?notaMinima=7.0&notaMaxima=9.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Alunos com notas entre 6.0 e 8.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/intervalo-nota?notaMinima=6.0&notaMaxima=8.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="

# Alunos com notas entre 5.0 e 10.0
curl -X GET "http://localhost:8080/api/v1/alunos/buscar/intervalo-nota?notaMinima=5.0&notaMaxima=10.0&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

### 10. Atualizar Aluno

```bash
# Atualizar aluno com ID 1
curl -X PUT http://localhost:8080/api/v1/alunos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4=" \
  -d '{
    "nome": "João Silva Santos",
    "matricula": "2024001",
    "nota": 9.5,
    "observacoes": "Desempenho excepcional"
  }'
```

### 11. Deletar Aluno

```bash
# Deletar aluno com ID 5
curl -X DELETE http://localhost:8080/api/v1/alunos/5 \
  -H "Authorization: Basic YWRtaW46YWRtaW4="
```

## Scripts de Teste Completo

### Script em Bash para Popular Database

```bash
#!/bin/bash

BASE_URL="http://localhost:8080/api/v1/alunos"
AUTH="Authorization: Basic YWRtaW46YWRtaW4="

# Array de alunos
alunos=(
  '{"nome":"João Silva","matricula":"2024001","nota":8.5,"observacoes":"Desempenho excelente"}'
  '{"nome":"Maria Santos","matricula":"2024002","nota":9.0,"observacoes":"Excelente aluna"}'
  '{"nome":"Pedro Costa","matricula":"2024003","nota":6.5,"observacoes":"Precisa melhorar"}'
  '{"nome":"Ana Oliveira","matricula":"2024004","nota":7.8,"observacoes":"Bom desempenho"}'
  '{"nome":"Carlos Mendes","matricula":"2024005","nota":5.2,"observacoes":"Abaixo da média"}'
  '{"nome":"Lucia Ferreira","matricula":"2024006","nota":8.2,"observacoes":"Ótimo desempenho"}'
  '{"nome":"Bruno Silva","matricula":"2024007","nota":7.0,"observacoes":"Desempenho médio"}'
  '{"nome":"Fernanda Lima","matricula":"2024008","nota":9.2,"observacoes":"Excelente"}'
  '{"nome":"Roberto Alves","matricula":"2024009","nota":4.8,"observacoes":"Baixo desempenho"}'
  '{"nome":"Camila Rocha","matricula":"2024010","nota":8.8,"observacoes":"Muito bom"}'
)

# Criar cada aluno
for aluno in "${alunos[@]}"; do
  echo "Criando aluno: $aluno"
  curl -X POST "$BASE_URL" \
    -H "Content-Type: application/json" \
    -H "$AUTH" \
    -d "$aluno"
  echo ""
done
```

## Usando Postman

Você pode importar a coleção abaixo no Postman:

1. Criar nova Collection: `Sanscritinho API`
2. Adicionar as requisições acima
3. Configurar variável `base_url = http://localhost:8080`
4. Usar autenticação Basic com `admin:admin`

## Dicas

- O campo `observacoes` é opcional
- A `nota` deve ser um número entre 0 e 10
- A `matricula` deve ser única por aluno
- A paginação padrão é 20 itens por página
- Os resultados com intervalo de nota vêm ordenados pela nota de forma decrescente
