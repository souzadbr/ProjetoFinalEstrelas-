# CouchZupper
![enter image description here](https://img.shields.io/badge/JAVA-11-orange)![enter image description here](https://img.shields.io/badge/SPRING-FRAMEWORK-green)![enter image description here](https://img.shields.io/badge/STATUS-EM%20DESENVOLVIMENTO-red)

## Descrição do Projeto
Aplicação focada em encontrar hospedagem de Zuppers para Zuppers *(seguindo o modelo *"[CouchSurfing](https://www.segurospromo.com.br/blog/couchsurfing-o-que-e-e-como-ter-hospedagem-gratis/)": hospedagem gratuita, a ideia é intermediar a troca cultural entre pessoas*),* pensando no fato de termos Zuppers pelo mundo, sendo assim um modo de instigar mais ainda o nosso pilar de #ZupperAjudaZupper, o turismo, o networking e o nosso modelo de liberdade através dessa troca de experiências.

## Diagrama de UML
~~EM DESENVOLVIMENTO~~

## Funcionalidades

- Cadastrar usuários;
- Listar todos os usuários cadastrados;
- Filtrar usuários por:  localidade e preferências;
- Atualizar informações cadastrais dos usuários;
- Deletar usuário cadastrado.

## Regras de Negócio

1. E-mail e telefone do usuário são dados únicos. Um usuário só poderá
   ter uma conta.
2. É necessário ter mais de 18 anos para se cadastrar.
3. CRUD (cadastrar usuário, listar perfis ativos(opção dos filtros de
   acordo com as preferências), atualizar dados e deletar dados)
4. Exibir somente usuários com perfil ativo (disponível para dividir
   aluguel)

## Requisitos e Dependências

-   Maven
-   Spring Boot (2.6.3)
-   Java 11
-   Hibernate
-   JPA
- Validation
- SpringWeb
- JWT (jsonwebtoken - jjwt)
- MySQL Driver
- Spring Security

Para rodar a aplicação localmente, executar o método  `main`  da classe  *CouchZupperApplication*

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


# Endpoints Disponíveis

## Cadastro de Usuário

**Método:** POST  
**EndPoint:** /usuario
**Corpo:**

    {
        "nome" : "Jane Doe",
        "email" : "jane@zup.com.br",
        "idade" : 30,
        "telefone" : "999999999",
        "estado" : "SERGIPE",
        "genero" : "FEMININO",
        "senha" : "minhasenha",
        "preferencia" : {
            "temPet" : true,
            "tipoDePet" : "GATO",
            "fumante" : false,
            "disponivelParaReceberUmZupper" : true,
            "conteAlgoQueNaoPerguntamos" : "Gosto de música e de sossego"
        }
    }

## Login de Usuário
**Método:** POST
**EndPoint:** /login

## Listar TODOS os Usuários
**Método:** GET
**EndPoint:** /usuario

## Listar Usuário por ID
**Método:** GET
**EndPoint:** /usuario/{id}

## Listar Usuários por ESTADO

**Método:** GET
**EndPoint:** /usuario?estado=BAHIA

## Atualizar Dados do Usuário

**Método:** PUT
**EndPoint:** /usuario/{id}

## Deletar Usuário
**Método:** DELETE
**EndPoint:** /usuario/{id}


