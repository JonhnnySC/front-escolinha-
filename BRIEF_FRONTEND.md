# 🎨 BRIEF PARA IA FRONTEND - Sanscritinho

**Data**: 2026-08-18
**Versão**: 1.0
**Destinado Para**: IA/Copilot VSCode - Desenvolvimento Frontend
**Objetivo**: Criar uma aplicação web para consulta de alunos por nota

---

## 📋 RESUMO EXECUTIVO

Você vai desenvolver um **frontend web** para a aplicação Sanscritinho.

**O que fazer?** Criar uma interface para:
- ✅ Listar alunos
- ✅ Criar novo aluno
- ✅ Buscar alunos por nota (principal funcionalidade)
- ✅ Atualizar aluno
- ✅ Deletar aluno
- ✅ Buscar por nome

**API Backend**: Já existe e está rodando em `http://localhost:8080/api/v1/alunos`

---

## 🔌 API ENDPOINTS

### Base URL
```
http://localhost:8080/api/v1/alunos
```

### Autenticação
```
Authorization: Basic YWRtaW46YWRtaW4=
Header: Authorization
Username: admin
Password: admin
```

### CRUD Básico

#### 1. Criar Aluno
```
POST /api/v1/alunos

Request:
{
  "nome": "João Silva",
  "matricula": "2024001",
  "nota": 8.5,
  "observacoes": "Desempenho excelente"
}

Response (201 Created):
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

#### 2. Listar Todos (Com Paginação)
```
GET /api/v1/alunos?page=0&size=10&sort=nota,desc

Response (200 OK):
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
    "sort": { "sorted": true }
  },
  "totalElements": 10,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "numberOfElements": 10,
  "first": true,
  "last": true,
  "empty": false
}
```

#### 3. Obter Aluno por ID
```
GET /api/v1/alunos/{id}

Response (200 OK):
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

#### 4. Obter por Matrícula
```
GET /api/v1/alunos/matricula/{matricula}

Response (200 OK): (mesmo modelo acima)
```

#### 5. Atualizar Aluno
```
PUT /api/v1/alunos/{id}

Request:
{
  "nome": "João Silva Santos",
  "matricula": "2024001",
  "nota": 9.0,
  "observacoes": "Excelente desempenho"
}

Response (200 OK): (aluno atualizado)
```

#### 6. Deletar Aluno
```
DELETE /api/v1/alunos/{id}

Response (204 No Content)
```

### Buscas Avançadas (PRINCIPAL)

#### 7. Buscar por Intervalo de Nota ⭐⭐⭐
```
GET /api/v1/alunos/buscar/intervalo-nota?notaMinima=7.0&notaMaxima=9.0&page=0&size=10

Response (200 OK): Page com alunos ordenados por nota DESC
```

#### 8. Buscar por Nota Mínima
```
GET /api/v1/alunos/buscar/nota-minima?nota=7.0&page=0&size=10

Response: Alunos com nota >= 7.0
```

#### 9. Buscar por Nota Máxima
```
GET /api/v1/alunos/buscar/nota-maxima?nota=8.0&page=0&size=10

Response: Alunos com nota <= 8.0
```

#### 10. Buscar por Nome
```
GET /api/v1/alunos/buscar/nome?nome=João&page=0&size=10

Response: Alunos com nome contendo "João"
```

---

## 📊 MODELOS DE DADOS

### Aluno (Response Model)
```typescript
interface Aluno {
  id: number;                    // Único, auto-increment
  nome: string;                  // 150 caracteres max
  matricula: string;             // Único, 20 caracteres
  nota: number;                  // 0-10
  dataCadastro: string;          // "YYYY-MM-DD HH:mm:ss"
  dataAtualizacao: string;       // "YYYY-MM-DD HH:mm:ss"
  observacoes?: string;          // 500 caracteres, opcional
}
```

### Aluno Create/Update (Request Model)
```typescript
interface AlunoCreate {
  nome: string;        // Obrigatório
  matricula: string;   // Obrigatório, único
  nota: number;        // Obrigatório (0-10)
  observacoes?: string; // Opcional
}
```

### Page Response (Paginação)
```typescript
interface Page<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: { sorted: boolean };
  };
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  numberOfElements: number;
  first: boolean;
  last: boolean;
  empty: boolean;
}
```

### Erro Response
```typescript
interface ErrorResponse {
  status: number;              // 400, 404, 500, etc
  message: string;             // Mensagem de erro
  timestamp: string;           // "YYYY-MM-DD HH:mm:ss"
  path: string;                // /api/v1/alunos
}
```

---

## 🎨 REQUISITOS DO FRONTEND

### Funcionalidades Principais
- [ ] Autenticação básica com admin/admin
- [ ] Listagem de alunos com paginação
- [ ] Criar novo aluno (formulário)
- [ ] Editar aluno existente
- [ ] Deletar aluno (com confirmação)
- [ ] **Buscar por intervalo de nota** (filtro principal)
- [ ] Buscar por nome
- [ ] Buscar por nota mínima
- [ ] Buscar por nota máxima
- [ ] Ordenação por coluna (nota DESC/ASC, nome ASC/DESC)
- [ ] Tratamento de erros e validação

### Requisitos de Experiência
- [ ] Responsivo (mobile + desktop)
- [ ] Tabela com dados paginados
- [ ] Filtros/Buscas amigáveis
- [ ] Formulários intuitivos
- [ ] Loading indicators
- [ ] Mensagens de sucesso/erro
- [ ] Confirmação antes de deletar

### Requisitos Técnicos
- [ ] Consumir API REST
- [ ] Gerenciar estado (alunos, paginação, filtros)
- [ ] Autenticação (Basic Auth)
- [ ] Tratamento de erros HTTP
- [ ] Validação de formulários
- [ ] Requisições assíncronas (async/await)

---

## 🚀 STACK RECOMENDADO

### Opção 1: React (Recomendado)
```
Frontend: React 18+
HTTP: Axios ou Fetch API
State: React Context ou Redux
UI: Material-UI / TailwindCSS
Routing: React Router
Build: Vite ou Create React App
```

### Opção 2: Angular
```
Frontend: Angular 16+
HTTP: HttpClient
State: RxJS/NgRx
UI: Angular Material
Build: Angular CLI
```

### Opção 3: Vue
```
Frontend: Vue 3
HTTP: Axios
State: Pinia ou Vuex
UI: PrimeVue ou Vuetify
Build: Vite
```

---

## 📁 ESTRUTURA DE DIRETÓRIOS SUGERIDA (React)

```
frontend/
├── src/
│   ├── components/
│   │   ├── AlunoForm/
│   │   │   ├── AlunoForm.jsx
│   │   │   └── AlunoForm.css
│   │   ├── AlunoTable/
│   │   │   ├── AlunoTable.jsx
│   │   │   └── AlunoTable.css
│   │   ├── AlunoSearch/
│   │   │   ├── AlunoSearch.jsx
│   │   │   └── AlunoSearch.css
│   │   ├── Pagination/
│   │   │   └── Pagination.jsx
│   │   └── Layout/
│   │       └── Layout.jsx
│   │
│   ├── pages/
│   │   ├── Home.jsx
│   │   ├── CreateAluno.jsx
│   │   ├── EditAluno.jsx
│   │   └── NotFound.jsx
│   │
│   ├── services/
│   │   ├── api.js (configuração Axios)
│   │   ├── alunoService.js (chamadas API)
│   │   └── authService.js (autenticação)
│   │
│   ├── context/
│   │   └── AlunoContext.jsx (state global)
│   │
│   ├── hooks/
│   │   ├── useAlunos.js
│   │   ├── usePagination.js
│   │   └── useFilters.js
│   │
│   ├── styles/
│   │   ├── global.css
│   │   └── variables.css
│   │
│   ├── App.jsx
│   ├── App.css
│   └── main.jsx
│
├── public/
├── index.html
├── package.json
└── vite.config.js
```

---

## 🔐 Autenticação

### Implementar Basic Auth
```javascript
// Axios interceptor
const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1',
  auth: {
    username: 'admin',
    password: 'admin'
  }
});

// Ou manualmente
const token = btoa('admin:admin');
headers = {
  'Authorization': `Basic ${token}`
};
```

---

## 📝 Exemplo de Uso (React + Axios)

### Serviço
```javascript
// alunoService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/alunos';
const AUTH = { username: 'admin', password: 'admin' };

export const alunoService = {
  // Listar
  listar: (page = 0, size = 10) => 
    axios.get(API_URL, { params: { page, size }, auth: AUTH }),
  
  // Criar
  criar: (aluno) => 
    axios.post(API_URL, aluno, { auth: AUTH }),
  
  // Atualizar
  atualizar: (id, aluno) => 
    axios.put(`${API_URL}/${id}`, aluno, { auth: AUTH }),
  
  // Deletar
  deletar: (id) => 
    axios.delete(`${API_URL}/${id}`, { auth: AUTH }),
  
  // Buscar por intervalo
  buscarPorIntervalo: (notaMin, notaMax, page = 0) =>
    axios.get(`${API_URL}/buscar/intervalo-nota`, {
      params: { notaMinima: notaMin, notaMaxima: notaMax, page },
      auth: AUTH
    })
};
```

### Componente
```javascript
// AlunoSearch.jsx
import { useState } from 'react';
import { alunoService } from '../services/alunoService';

export function AlunoSearch() {
  const [notaMin, setNotaMin] = useState(0);
  const [notaMax, setNotaMax] = useState(10);
  const [alunos, setAlunos] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    try {
      setLoading(true);
      const response = await alunoService.buscarPorIntervalo(notaMin, notaMax);
      setAlunos(response.data.content);
      setError(null);
    } catch (err) {
      setError(err.response?.data?.message || 'Erro ao buscar');
      setAlunos([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <input 
        type="number" 
        value={notaMin} 
        onChange={(e) => setNotaMin(e.target.value)}
        placeholder="Nota mínima"
      />
      <input 
        type="number" 
        value={notaMax} 
        onChange={(e) => setNotaMax(e.target.value)}
        placeholder="Nota máxima"
      />
      <button onClick={handleSearch} disabled={loading}>
        {loading ? 'Buscando...' : 'Buscar'}
      </button>
      
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {alunos.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Matrícula</th>
              <th>Nota</th>
            </tr>
          </thead>
          <tbody>
            {alunos.map(aluno => (
              <tr key={aluno.id}>
                <td>{aluno.nome}</td>
                <td>{aluno.matricula}</td>
                <td>{aluno.nota}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
```

---

## 📊 Tela Principal (Wireframe)

```
┌─────────────────────────────────────────────────────┐
│ SANSCRITINHO - Gestão de Alunos                    │
├─────────────────────────────────────────────────────┤
│                                                     │
│ ┌─────────────────────────────────────────────────┐ │
│ │ Filtros de Busca                              │ │
│ │ ┌─────────┐ ┌─────────┐ ┌─────────┐          │ │
│ │ │ Nome    │ │ Nota Min│ │ Nota Max│ [Buscar]│ │
│ │ └─────────┘ └─────────┘ └─────────┘          │ │
│ │ ┌──────────────────────────────────────────┐  │ │
│ │ │ [+ Novo Aluno]                          │  │ │
│ │ └──────────────────────────────────────────┘  │ │
│ └─────────────────────────────────────────────────┘ │
│                                                     │
│ ┌─────────────────────────────────────────────────┐ │
│ │ ID │ Nome         │ Matrícula │ Nota │ Ações │ │
│ ├────┼──────────────┼───────────┼──────┼───────┤ │
│ │ 1  │ João Silva   │ 2024001   │ 8.5  │ ✏️ 🗑️ │ │
│ │ 2  │ Maria Santos │ 2024002   │ 9.0  │ ✏️ 🗑️ │ │
│ │ 3  │ Pedro Costa  │ 2024003   │ 6.5  │ ✏️ 🗑️ │ │
│ └─────────────────────────────────────────────────┘ │
│                                                     │
│ Página 1 de 1 | Total: 10 registros               │
│ [Anterior] [1] [2] [3] [Próximo]                   │
└─────────────────────────────────────────────────────┘
```

---

## ✅ CHECKLIST DE DESENVOLVIMENTO

### Setup
- [ ] Criar projeto com Vite/Create React App
- [ ] Instalar dependências (axios, react-router)
- [ ] Configurar proxy da API (opcional)
- [ ] Criar estrutura de pastas

### Autenticação
- [ ] Implementar Basic Auth
- [ ] Configurar interceptor Axios
- [ ] Testar autenticação

### Componentes
- [ ] AlunoTable (listagem)
- [ ] AlunoForm (criar/editar)
- [ ] AlunoSearch (filtros)
- [ ] Pagination (paginação)
- [ ] ErrorMessage (erros)
- [ ] LoadingSpinner (loading)

### Funcionalidades
- [ ] Listar alunos
- [ ] Criar aluno
- [ ] Editar aluno
- [ ] Deletar aluno
- [ ] Buscar por intervalo de nota
- [ ] Buscar por nome
- [ ] Buscar por nota mínima/máxima
- [ ] Paginação
- [ ] Ordenação

### Testes
- [ ] Testar chamadas API
- [ ] Testar validações
- [ ] Testar erros
- [ ] Teste de usabilidade

### Deploy
- [ ] Build otimizado (npm run build)
- [ ] Testar em produção
- [ ] Configurar CORS se necessário

---

## 🔗 LINKS IMPORTANTES

**Backend API**:
- Base URL: `http://localhost:8080/api/v1/alunos`
- Documentação: `README.md`
- Exemplos: `EXEMPLOS_CURL.md`

**Documentação**:
- `QUICK_REFERENCE.md` - Referência rápida
- `README.md` - API completa
- `ARQUITETURA.md` - Detalhes técnicos

---

## 📞 COMO EXECUTAR O BACKEND

```bash
# 1. Criar banco de dados
psql -U postgres -f SETUP_DATABASE.sql

# 2. Compilar backend
cd sanscritinho
.\mvnw.cmd clean compile

# 3. Executar backend
.\mvnw.cmd spring-boot:run

# Backend rodando em: http://localhost:8080
```

---

## 🎯 PRIORIDADES DE DESENVOLVIMENTO

### Fase 1: MVP (Semana 1)
1. Listagem de alunos com paginação
2. Buscar por intervalo de nota
3. Criar novo aluno
4. Editar aluno
5. Deletar aluno

### Fase 2: Melhorias (Semana 2)
1. Buscar por nome
2. Buscar por nota min/max
3. Ordenação dinâmica
4. Validações aprimoradas
5. Mensagens de feedback

### Fase 3: Polimento (Semana 3)
1. Design responsivo
2. Temas/Dark mode
3. Testes unitários
4. Otimizações
5. Deploy

---

## 📝 OBSERVAÇÕES IMPORTANTES

1. **Autenticação**: Use `admin:admin` em desenvolvimento
2. **CORS**: O backend pode estar bloqueando requisições locais - configure se necessário
3. **API Local**: Certifique-se que o backend está rodando antes de iniciar o frontend
4. **Paginação**: Page começa em 0 (não 1)
5. **Datas**: Formato `YYYY-MM-DD HH:mm:ss`
6. **Nota**: Deve estar entre 0 e 10
7. **Matrícula**: Deve ser única por aluno

---

## 🚀 PRÓXIMOS PASSOS

1. Enviar este documento para a IA do VSCode/Copilot
2. Copilot criará a estrutura do projeto
3. Revisar o código gerado
4. Testar contra o backend
5. Fazer ajustes conforme necessário

---

*Brief criado em: 2026-08-18 20:19:51*
*Versão: 1.0*
*Status: Pronto para desenvolvimento*
