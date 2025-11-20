# LudicoM Backend - API Documentation

Documentação completa das APIs REST disponíveis para gerenciamento de Jogos, Participantes, Instituições, Eventos e Empréstimos.

## Visão Geral

-   **Base URL padrão:** `http://localhost:8080`
-   **Versionamento:** não há versionamento explícito nos endpoints (todos expostos sob `/api`).
-   **Formato:** JSON para requisições e respostas.
-   **Autenticação:** não aplicada nesta versão (rotas públicas).
-   **Headers comuns:**
    -   `Content-Type: application/json`

Cada seção abaixo descreve os endpoints CRUD, payloads e exemplos de uso.

---

## 1. Jogos (`/api/jogo`)

### Campos

| Campo             | Tipo              | Obrigatório          | Descrição                       |
| ----------------- | ----------------- | -------------------- | ------------------------------- |
| `nome`            | string (1-200)    | Sim                  | Nome oficial do jogo            |
| `nomeAlternativo` | string (≤200)     | Não                  | Nome secundário                 |
| `anoPublicacao`   | data (YYYY-MM-DD) | Não                  | Ano ou data de publicação       |
| `tempoDeJogo`     | inteiro           | Não                  | Duração em minutos              |
| `minimoJogadores` | inteiro           | Não                  | Quantidade mínima de jogadores  |
| `maximoJogadores` | inteiro           | Não                  | Quantidade máxima de jogadores  |
| `codigoDeBarras`  | string            | Não                  | Código de barras para controle  |
| `isDisponivel`    | boolean           | Não (default `true`) | Disponibilidade para empréstimo |

### Endpoints

1. **Criar** – `POST /api/jogo`

```json
{
    "nome": "Monopoly",
    "nomeAlternativo": "Banco Imobiliário",
    "anoPublicacao": "2023-01-01",
    "tempoDeJogo": 180,
    "minimoJogadores": 2,
    "maximoJogadores": 6,
    "codigoDeBarras": "789123456",
    "isDisponivel": true
}
```

Resposta (`201`): `JogoResponse` com metadados (`uid`, `criadoQuando`, etc.).

2. **Listar** – `GET /api/jogo`

    - Retorna `200` com lista de `JogoResponse`.

3. **Buscar por ID** – `GET /api/jogo/{id}`

4. **Atualizar** – `PUT /api/jogo/{id}`

    - Corpo igual ao `POST`.

5. **Excluir** – `DELETE /api/jogo/{id}`
    - Resposta: `{ "message": "Jogo deletado com sucesso" }`.

**Exemplo cURL (criar jogo):**

```bash
curl -X POST http://localhost:8080/api/jogo \
  -H "Content-Type: application/json" \
  -d '{
        "nome": "Catan",
        "tempoDeJogo": 90,
        "minimoJogadores": 3,
        "maximoJogadores": 4,
        "isDisponivel": true
      }'
```

---

## 2. Participantes (`/api/participante`)

### Campos

| Campo           | Tipo                  | Obrigatório | Observações                                             |
| --------------- | --------------------- | ----------- | ------------------------------------------------------- |
| `nome`          | string (1-200)        | Sim         |                                                         |
| `email`         | string (válido, ≤150) | Sim         | Único                                                   |
| `documento`     | string (≤30)          | Sim         | Único                                                   |
| `idInstituicao` | string                | Condicional | Se informado, precisa existir                           |
| `ra`            | string (≤15)          | Condicional | Obrigatório **apenas** se `idInstituicao` for fornecido |

### Endpoints

1. **POST /api/participante** – cria participante.
2. **GET /api/participante** – lista todos.
3. **GET /api/participante/{id}`** – busca por UID.
4. **PUT /api/participante/{id}`** – atualiza (mesmo payload do POST).
5. **DELETE /api/participante/{id}`** – remove participante.

**Observações:**

-   O serviço valida unicidade de `email`, `documento` e, quando fornecido, `ra`.
-   O campo `instituicao` do response é um objeto completo (`uid`, `nome`, `endereco`).

**Exemplo de request (POST):**

```json
{
    "nome": "Ana Silva",
    "email": "ana@exemplo.com",
    "documento": "12345678900",
    "idInstituicao": "a1b2c3",
    "ra": "RA2024001"
}
```

---

## 3. Instituições (`/api/instituicao`)

### Campos

| Campo      | Tipo           | Obrigatório |
| ---------- | -------------- | ----------- |
| `nome`     | string (1-200) | Sim         |
| `endereco` | string (≤255)  | Não         |

### Endpoints

1. `POST /api/instituicao`
2. `GET /api/instituicao`
3. `GET /api/instituicao/{id}`
4. `PUT /api/instituicao/{id}`
5. `DELETE /api/instituicao/{id}`

**Exemplo (POST):**

```json
{
    "nome": "UTFPR - Curitiba",
    "endereco": "Av. Sete de Setembro, 3165"
}
```

---

## 4. Eventos (`/api/evento`)

### Campos

| Campo           | Tipo                | Obrigatório | Observações                             |
| --------------- | ------------------- | ----------- | --------------------------------------- |
| `idInstituicao` | string              | Sim         | Deve referenciar uma instituição válida |
| `data`          | string (YYYY-MM-DD) | Sim         |                                         |
| `horaInicio`    | string (HH:MM:SS)   | Sim         | Validação regex                         |
| `horaFim`       | string (HH:MM:SS)   | Sim         | Validação regex                         |

### Endpoints

1. `POST /api/evento`
2. `GET /api/evento`
3. `GET /api/evento/{id}`
4. `PUT /api/evento/{id}`
5. `DELETE /api/evento/{id}`

**Response:** inclui `InstituicaoResponse` aninhado com dados da instituição responsável.

**Exemplo (POST):**

```json
{
    "idInstituicao": "f1f2f3",
    "data": "2025-02-10",
    "horaInicio": "09:00:00",
    "horaFim": "18:00:00"
}
```

---

## 5. Empréstimos (`/api/emprestimo`)

### Campos

| Campo            | Tipo              | Obrigatório           |
| ---------------- | ----------------- | --------------------- |
| `idJogo`         | string            | Sim                   |
| `idParticipante` | string            | Sim                   |
| `idEvento`       | string            | Sim                   |
| `horaEmprestimo` | string (HH:MM:SS) | Sim                   |
| `horaDevolucao`  | string (HH:MM:SS) | Sim                   |
| `isDevolvido`    | boolean           | Não (default `false`) |

### Endpoints

1. `POST /api/emprestimo`
2. `GET /api/emprestimo`
3. `GET /api/emprestimo/{id}`
4. `PUT /api/emprestimo/{id}`
5. `DELETE /api/emprestimo/{id}`

**Response:**

```json
{
  "uid": "0c5adf47-3d0d-4fd4-a76d-2bac46e41cce",
  "jogo": { "uid": "...", "nome": "...", "isDisponivel": true, ... },
  "participante": { "uid": "...", "nome": "Ana", "instituicao": {"uid": "..."} },
  "evento": { "uid": "...", "instituicao": {"uid": "..."}, "data": "2025-02-10", ... },
  "horaEmprestimo": "10:00:00",
  "horaDevolucao": "12:30:00",
  "isDevolvido": false
}
```

**Exemplo cURL (criar empréstimo):**

```bash
curl -X POST http://localhost:8080/api/emprestimo \
  -H "Content-Type: application/json" \
  -d '{
        "idJogo": "f7b3...",
        "idParticipante": "a2d1...",
        "idEvento": "c9e8...",
        "horaEmprestimo": "10:00:00",
        "horaDevolucao": "12:00:00",
        "isDevolvido": false
      }'
```

---

## Códigos de Resposta Comuns

| Código                      | Significado                    | Possíveis causas                                                       |
| --------------------------- | ------------------------------ | ---------------------------------------------------------------------- |
| `200 OK`                    | Operação realizada com sucesso | Consultas, updates, deletes                                            |
| `201 Created`               | Recurso criado                 | POST em qualquer entidade                                              |
| `400 Bad Request`           | Falha de validação             | Campos obrigatórios ausentes, formatos inválidos                       |
| `404 Not Found`             | Recurso não encontrado         | IDs inexistentes ou formatos incorretos                                |
| `409 Conflict`              | Conflito de chave única        | E-mails/documentos duplicados (Participante), nomes de jogos repetidos |
| `500 Internal Server Error` | Erro inesperado                | Problemas internos do servidor                                         |

---

## Boas Práticas

-   Sempre validar IDs antes do envio (UUID strings).
-   Para ambientes diferentes (dev, prod), alterar a Base URL conforme necessidade.
-   Utilizar `application/json` em todas as requisições que enviam payload.
-   Em caso de erros, verificar o corpo retornado pelo `GlobalExceptionHandler` para mensagens detalhadas.

---

## Referências Rápidas

| Entidade     | Endpoint base       | DTO de criação              |
| ------------ | ------------------- | --------------------------- |
| Jogo         | `/api/jogo`         | `JogoCreateRequest`         |
| Participante | `/api/participante` | `ParticipanteCreateRequest` |
| Instituição  | `/api/instituicao`  | `InstituicaoCreateRequest`  |
| Evento       | `/api/evento`       | `EventoCreateRequest`       |
| Empréstimo   | `/api/emprestimo`   | `EmprestimoCreateRequest`   |

Essa documentação cobre todas as APIs expostas atualmente pelo backend LudicoM. Ajuste os exemplos conforme os dados reais do seu ambiente.
