# Jornada — Login e Cadastro com Spring Boot + Thymeleaf

Atividade 02 (dupla) — Tela de Login com **Spring Boot + Thymeleaf**.

Aplicação web com autenticação e cadastro de usuários, usando Spring
Security para login/logout, Thymeleaf para as páginas, BCrypt para senhas
e um fluxo de recuperação de senha por e-mail.

## Integrantes

- Miguel — *(adicionar o nome da dupla aqui)*

## Stack

- Java 25 + Spring Boot
- Spring Web MVC + Thymeleaf
- Spring Security (autenticação por formulário, BCrypt)
- Spring Data JPA + H2 (padrão) / MySQL (opcional)
- Spring Mail (recuperação de senha por e-mail)
- Bean Validation (`jakarta.validation`)

## Endpoints

| Método | Endpoint            | Descrição                                          |
|--------|----------------------|-----------------------------------------------------|
| GET    | `/`                  | Redireciona para `/login`                           |
| GET    | `/login`              | Exibe a tela de login                                |
| POST   | `/login`              | Processado automaticamente pelo Spring Security      |
| GET    | `/register`           | Exibe a tela de cadastro                             |
| POST   | `/register`           | Processa o cadastro de um novo usuário               |
| GET    | `/recoverpassword`    | Exibe a tela de recuperação de senha                 |
| POST   | `/recoverpassword`    | Envia o link de redefinição por e-mail               |
| GET    | `/reset-password?token=...` | Tela para definir uma nova senha              |
| POST   | `/reset-password`     | Salva a nova senha                                   |
| GET    | `/home`               | Área autenticada (requer login)                      |
| POST   | `/logout`             | Encerra a sessão                                     |

Todas as rotas, exceto `/login`, `/register`, `/recoverpassword`,
`/reset-password` e os arquivos estáticos, exigem usuário autenticado
(ver `SecurityConfig`).

## Como executar

1. **Sem configurar nada** — a aplicação já roda com banco H2 em memória:

   ```bash
   ./mvnw spring-boot:run
   ```

   Acesse `http://localhost:8080`. Crie uma conta em `/register` e faça
   login em `/login`.

2. **Console do H2** (opcional, útil para conferir os usuários
   cadastrados): `http://localhost:8080/h2-console`
   (JDBC URL: `jdbc:h2:mem:demo_db`, usuário `sa`, sem senha).

## Configuração de ambiente (opcional)

Nenhuma credencial real está no código — tudo vem de variáveis de
ambiente, com valores padrão seguros para rodar localmente.

### Usar MySQL em vez de H2

```bash
export DB_USERNAME=seu_usuario
export DB_PASSWORD=sua_senha
```

E ajuste `spring.datasource.url`/`driver-class-name` em
`application.properties` para o MySQL (o driver já está no `pom.xml`).

### Habilitar o envio real de e-mail na recuperação de senha

Por padrão, se não houver credenciais de e-mail configuradas, o link de
redefinição de senha é apenas **registrado no log do console** (não
quebra a aplicação). Para enviar de verdade, configure uma conta SMTP
(ex.: Gmail com senha de app) antes de subir o projeto:

```bash
export MAIL_USERNAME=seuemail@gmail.com
export MAIL_PASSWORD=sua_senha_de_app
export APP_BASE_URL=http://localhost:8080
```

## Segurança

- Senhas armazenadas com **BCrypt** (`PasswordEncoder`), nunca em texto puro.
- Autenticação feita pelo próprio **Spring Security** (`UserDetailsService`
  customizado lendo do banco).
- Rotas fora das públicas exigem login (`/home`, por exemplo).
- Link de recuperação de senha expira em 30 minutos e é de uso único.
- O fluxo de recuperação não informa se um e-mail existe ou não na base
  (evita enumeração de usuários).

## Estrutura do projeto

```text
src/main/java/com/miguel/demo/
├── config/          # SecurityConfig
├── controllers/      # AuthController (login/registro/recuperação), HomeController
├── dto/               # Formulários (RegisterForm, RecoverPasswordForm, ResetPasswordForm)
├── exceptions/        # UserAlreadyExistsException
├── models/            # User (JPA entity)
├── repositories/      # UserRepository
├── security/           # CustomUserDetailsService
└── services/           # UserService, EmailService

src/main/resources/
├── application.properties
├── static/css/auth.css
├── static/js/particles.js
└── templates/
    ├── auth/login.html
    ├── auth/register.html
    ├── auth/recover-password.html
    ├── auth/reset-password.html
    └── home.html
```
