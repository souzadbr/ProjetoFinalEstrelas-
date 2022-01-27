# CouchZupper
![enter image description here](https://img.shields.io/badge/JAVA-11-orange)![enter image description here](https://img.shields.io/badge/SPRING-FRAMEWORK-green)![enter image description here](https://img.shields.io/badge/STATUS-EM%20DESENVOLVIMENTO-red)

## Descrição do Projeto
Aplicação focada em encontrar hospedagem de Zuppers para Zuppers *(seguindo o modelo *"[CouchSurfing](https://www.segurospromo.com.br/blog/couchsurfing-o-que-e-e-como-ter-hospedagem-gratis/)": hospedagem gratuita, a ideia é intermediar a troca cultural entre pessoas*),* pensando no fato de termos Zuppers por todo o Brasil, sendo assim um modo de instigar mais ainda o nosso pilar de #ZupperAjudaZupper, o turismo, o networking e o nosso modelo de liberdade através dessa troca de experiências.

## Diagrama de UML
~~EM DESENVOLVIMENTO~~

## Funcionalidades

- Cadastrar novos usuários;
- Listar todos os usuários cadastrados;
- Filtrar usuários por:  localidade, gênero e preferências;
- Atualizar informações do usuário: dados pessoais, dados de login e as preferências;
- Deletar usuário cadastrado.

## Regras de Negócio

1. É necessário ter mais de 18 anos para se cadastrar.
2. E-mail (somente emails de Zupper "@zup.com.br") e telefone do usuário são dados únicos. Um usuário só poderá ter uma conta.
3. CRUD (cadastrar usuário, listar somente perfis ativos (opção dos filtros de acordo com as preferências, localidade e gênero), atualizar dados e deletar dados do usuário)
4. Exibir somente usuários com perfil ativo (disponível para recepcionar um Zupper viajante)

## Requisitos e Dependências

- Maven
- Spring Boot (2.6.3)
- Java 11
- Hibernate
- JPA
- Validation
- SpringWeb
- JWT (jsonwebtoken - jjwt)
- MySQL Driver
- Spring Security

Para rodar a aplicação localmente, executar o método  `main` da classe  *CouchZupperApplication*

## Configuração do Banco de Dados


- Verificar resources documento application.properties


**spring.datasource.url=jdbc:mysql://(localhost:porta-que-se-banco-usa)/(nome-do-projeto)** (exemplo)  
*(inserir os dados de configuração do seu banco de dados)*  
**spring.datasource.username= usuario  
spring.datasource.password= senha**

**spring.jpa.hibernate.ddl-auto=update**

**jwt.segredo= senha  
jwt.milissegundos= tempo de validade do token**

*Recomendado utilizar variáveis de ambiente para configurar os dados e para manter a segurança da aplicação*


# Rotas Disponíveis

## Cadastro de Usuário

**Método:** POST    
**EndPoint:** /usuario  
**Corpo:**

      {
      "nome": "Jane Doe",
      "email": "jane@zup.com.br",
      "idade": 30,
      "telefone": "+55 (12) 98888-8888",
      "estado": "MANAUS",
      "genero": "FEMININO",
      "senha": "minhasenha",
      "preferencia": {
        "temPet": true,
        "tipoDePet": "PASSAROS",
        "fumante": false,
        "disponivelParaReceberUmZupper": true,
        "conteAlgoQueNaoPerguntamos": "Lorem ipsum neque pellentesque dictumst eu duis fusce, eros congue dolor vehicula consequat litora porta curabitur,"
      }
    }



**Resposta** 201
## Login de Usuário
**Método:** POST  
**EndPoint:** /login
**Corpo:**

    {
      "email": "jane@zup.com.br",
      "senha": "minhasenha"
    } 

**Resposta** 200

    {
    "Authorization": "Token eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5lQHp1cC5jb20uYnIiLCJpZFVzdWFyaW8iOiI0MDI4ODE4MjdlOTJjMGNlMDE3ZTkyYzE2ZGY1MDAwMCIsImV4cCI6MTY0MzMwOTM4OX0.e-LP1LkdG7NZ0IMXbxffzzqKAHXGFO9ys6CLLIraWVntdhJ5su_zULZ84n1r-4a3zlRtvtRll0q4_sradgVAwA"
    }

## Listar TODOS os Usuários
**Método:** GET  
**EndPoint:** /usuario  
**Necessário autenticação de usuário**
**Resposta** 200

## Listar Usuário por ID
**Método:** GET  
**EndPoint:** /usuario/{id}  
**Necessário autenticação de usuário**

- **Resposta** 200


## Listar Usuários por ESTADO

**Método:** GET

- **EndPoint:** /usuario?estado=BAHIA
- **EndPoint:** /usuario?estado=DISTRITO_FEDERAL
-  **EndPoint:**/usuario?estado=SAO_PAULO

**Necessário autenticação de usuário**

- **Resposta** 200

## Listar Usuários por GÊNERO

**Método:** GET

- **EndPoint:** /usuario?genero=FEMININO
-   **EndPoint:** /usuario?genero=MASCULINO
-   **EndPoint:** usuario?genero=NAO_BINARIO

**Necessário autenticação de usuário**

- **Resposta** 200



## Atualizar Dados do Usuário

**Método:** PUT  
**EndPoint:** /usuario/dados/{id}  
**Necessário autenticação de usuário**

- **Corpo:**


       {
              "nome": "Jane Doe",
              "email": "jane@zup.com.br",
              "idade": 28,
              "telefone": "61999999999",
              "estado": "TOCANTINS",
              "genero": "NAO_BINARIO",
              "senha": "minhasenha"
            }

**Resposta** 200

## Atualizar Dados de LOGIN do Usuário

**Método:** PUT  
**EndPoint:** /usuario/login/{id}  
**Necessário autenticação de usuário**
- **Corpo**:



      {
              "email": "jane@zup.com.br",
              "senha": "minhasenha2"
            }

**Resposta** 200

## Atualizar as PREFERÊNCIAS do Usuário
**Método:** PUT  
**EndPoint:** /preferencias/{id}  
**Necessário autenticação de usuário**
- **Corpo**:


       {
          "nome": "Jane Doe",
          "email": "jane@zup.com.br",
          "idade": 28,
          "telefone": "61999999999",
          "estado": "TOCANTINS",
          "genero": "NAO_BINARIO",
          "senha": "minhasenha"
        }

**Resposta** 200

## Deletar Usuário

- **Método:** DELETE
- **EndPoint:** /usuario/{id}
-  **Necessário autenticação de usuário**
-  **Resposta** 204


