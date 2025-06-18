# 📝 ToDoList API

API RESTful desenvolvida com Spring Boot para gerenciamento de tarefas. O projeto implementa autenticação robusta (com três métodos disponíveis) e segue boas práticas de arquitetura, segurança e documentação.

## 📌 Funcionalidades

- ✅ Cadastro, listagem, atualização e exclusão de **tarefas** (`Task`)
- 📊 Organização por **situação** da tarefa (`Situation`)
- 🔐 Autenticação e autorização via:
  - HTTP Basic Auth
  - OAuth2 (Login com Google)
  - JWT (JSON Web Token)
- 📄 Documentação automática com Swagger/OpenAPI
- 🛡️ Proteção de endpoints com Spring Security
- 🧾 Validações e mensagens de erro customizadas

## 🧱 Entidades principais

- **Task**: Representa uma tarefa da lista.
- **Situation**: Estado ou status da tarefa (ex: pendente, em andamento, concluída).
- **UserLogin**: Responsável pela autenticação do usuário.

## 🛠️ Tecnologias utilizadas

- Java 21
- Spring Boot 3.5
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring OAuth2 Client & Authorization Server
  - Spring Validation
  - Spring Mail
  - Springdoc OpenAPI
- Lombok
- MapStruct
- MySQL
- Swagger UI

## 🚀 Como executar o projeto

### Pré-requisitos

- Java 21+
- Maven 3.8+
- MySQL

### Configuração do banco de dados

Crie um banco de dados no MySQL:

```sql
CREATE DATABASE todolist_db;
```

### Configure as credenciais do banco em application.properties ou application.yml:
```
spring.datasource.url=jdbc:mysql://localhost:3306/todolist_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### A documentação interativa da API está disponível via Swagger UI.

http://localhost:8000/swagger-ui/index.html

