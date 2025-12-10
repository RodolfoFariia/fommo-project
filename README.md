# FOMMO - Music Reviews API 🎵

> "Fear Of Missing Music Out"

Uma API RESTful robusta para uma plataforma de avaliação e descoberta de músicas, inspirada no Letterboxd. O sistema permite que usuários se cadastrem, busquem músicas/álbuns/artistas (via integração com **Spotify API**) e registrem suas avaliações pessoais.

Este projeto foi desenvolvido como parte da disciplina de Programação Web da **UNIFEI**.

---

### 🏗️ Status do Projeto

* **Backend (Este repositório):** ✅ **Concluído (V1.0)**
* **Frontend (Angular):** ✅ **Concluído** - [Acesse o Repositório do Front](https://github.com/RodolfoFariia/fommo-frontend)

---

## ✨ Principais Features

### 🔐 Segurança & Autenticação
* **Login & Registro:** Implementação completa de autenticação via **JWT (JSON Web Tokens)**.
* **Criptografia:** Senhas armazenadas de forma segura usando **BCrypt**.
* **Proteção de Rotas:** Configuração do Spring Security para bloquear endpoints privados e liberar apenas autenticação.
* **Segurança de Dados:** Uso do padrão **DTO (Data Transfer Object)** para "blindar" a API, impedindo vazamento de dados sensíveis (como senhas e IDs internos) nas respostas.

### 🎧 Integração com Spotify
* **OpenFeign:** Utilização do cliente declarativo Spring Cloud OpenFeign para consumir a API do Spotify.
* **Fluxo OAuth2 (Client Credentials):** O backend gerencia a autenticação segura com o Spotify (troca de chaves por token) de forma transparente para o frontend.
* **Busca Unificada:** Endpoint `/api/spotify/search` que serve como um "túnel" seguro para buscar álbuns, artistas e faixas.

### 💾 Regras de Negócio & Persistência
* **Arquitetura em Camadas:** Controller -> Service -> Repository.
* **Gestão de Usuário:** Funcionalidades para o usuário gerenciar o próprio perfil (`/me`) e ver avaliações de outros.
* **Avaliações:** Sistema relacional onde cada avaliação é vinculada a um usuário e a um item externo (Spotify ID).

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework:** Spring Boot 3.4.x
* **Segurança:** Spring Security, Java JWT (Auth0)
* **Banco de Dados:** PostgreSQL
* **Integração:** Spring Cloud OpenFeign (Spotify API)
* **Ferramentas:** Maven, Lombok, Spring Validation

---

## 🚦 Como Executar o Projeto

### 1. Pré-requisitos
* Java 21
* PostgreSQL
* Conta de Desenvolvedor no Spotify (para obter as chaves de API)

### 2. Variáveis de Ambiente (Configuração Crítica)
Por segurança, este projeto **não contém senhas hardcoded**. Para rodar, você deve configurar as seguintes variáveis de ambiente na sua IDE (IntelliJ/Eclipse) ou no seu container Docker:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `DB_PASSWORD` | Senha do seu banco PostgreSQL local | `minha_senha_123` |
| `JWT_SECRET` | Chave secreta para assinar os tokens | `segredo_super_secreto` |
| `SPOTIFY_CLIENT_ID` | Client ID do seu Dashboard Spotify | `abc123xyz...` |
| `SPOTIFY_CLIENT_SECRET`| Client Secret do seu Dashboard Spotify | `987zyx321...` |

### 3. Banco de Dados
Crie um banco de dados vazio no PostgreSQL chamado:
`fommo_db`

### 4. Rodando
```bash
# Clone o repositório
git clone [https://github.com/RodolfoFariia/fommo-backend.git](https://github.com/RodolfoFariia/fommo-backend.git)

# Instale as dependências e compile
./mvnw clean install

# Execute
./mvnw spring-boot:run

```

## 📖 Documentação da API

### 🔐 Autenticação (`/auth`)
| Método | Rota | Descrição | Body (JSON) |
| :--- | :--- | :--- | :--- |
| `POST` | `/login` | Autentica e retorna o Token JWT. | `{ "email": "...", "senha": "..." }` |
| `POST` | `/register` | Cria uma nova conta de usuário. | `{ "nome": "...", "email": "...", "senha": "...", "dataNascimento": "YYYY-MM-DD" }` |

### 👤 Usuários (`/usuarios`)
*Requer Header: `Authorization: Bearer <token>`*

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `GET` | `/me` | Retorna o perfil do usuário logado. |
| `PUT` | `/me` | Atualiza dados do usuário logado (Nome, Email, Senha). |
| `DELETE` | `/me` | Exclui a conta do usuário logado. |
| `GET` | `/{id}` | Visualiza o perfil público de outro usuário. |

### ⭐ Avaliações (`/avaliacoes`)
*Requer Header: `Authorization: Bearer <token>`*

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/` | Cria uma nova avaliação (Vincula ao usuário logado). |
| `GET` | `/me` | Retorna todas as avaliações do usuário logado. |
| `GET` | `/usuario/{id}`| Retorna as avaliações de um usuário específico. |

### 🎵 Spotify (`/api/spotify`)
*Requer Header: `Authorization: Bearer <token>`*

| Método | Rota | Descrição | Params |
| :--- | :--- | :--- | :--- |
| `GET` | `/search` | Busca músicas/álbuns no Spotify. | `?q=Nome&type=album,artist` |

---


## 👨‍💻 Autor

Projeto desenvolvido por graduando em Ciência da Computação pela **UNIFEI**:

| **Rodolfo Henrique Faria** |
|:--------------------------:|
| [![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/rodolfofaaria/) |
| [![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/RodolfoFariia) |


