# ToDo

- [x] MS de Cadastro de Alunos
- [x] MS de Cadastro de Cartões
- [x] NGIX como proxy reverso para ambas as aplicações
- [x] Documentação Swagger para os endpoints já criados 
- [ ] Carga do arquivo TXT (Spring Batch?)
- [x] Endpoint de Transações
- [ ] Testes unitários Alunos
- [ ] Testes unitários Cartões
- [ ] Testes integrados (Postman?)
- [x] Criar o readme com as instruções para subir aplicação
- [x] Readme deve conter também as justificativas de uso das ferramentas (banco de dados MYSQL, docker, etc.)
- [ ] Massa simulada de transações (Postman?)
- [ ] Plus a mais: Controle de usuários com Spring Security?


# Documentação da aplicação FiapCard

## Introdução

O FiapCard é um cartão especialmente feito para alunos da Fiap.
Qualquer aluno devidamente matriculado pode solicitar seu cartão.

## Fazer o deploy da aplicação

O primeiro passo é clonar o repositório do Github e entrar no diretório da aplicação:

    git clone https://github.com/hodestito/SpringTrabalhoFinal.git
    cd SpringTrabalhoFinal

Toda a configuração necessária está contida no arquivo `docker-compose.yaml`. Para fazer o deploy executar o comando abaixo:

    docker-compose up

A aplicação irá inicializar dois serviços: **Alunos** e **Cartões**.


## Chamadas aos endpoints

Ao executar o comando `docker-compose up`, o serviço **NGINX** é iniciado e faz o proxy reverso para centralizar as chamadas às APIs.

* **Alunos:** http://localhost:80/alunos
* **Cartões:** http://localhost:80/cartoes

## Documentação das APIs

* **Alunos:** Controla o cadastro de alunos matriculados na Fiap. Consultar documentação em: [Swagger](localhost:8080/swagger-ui.html)

* **Cartões:** Controla o cadastro de cartões e de transações. Consultar documentação em: [Swagger](localhost:8081/swagger-ui.html)


## Ferramentas utilizadas

Para esta aplicação foram escolhidas as seguintes ferramentas:

- **Banco de dados MYSQL** - É gratuito e leve, mesmo suportando aplicações robustas
- **Docker** e **Docker-compose** - Facilita a portabilidade das aplicações possibilitando deploy em diversas plataformas de forma rápida e integrada
- **Proxy reverso NGINX** - Preserva a identidade do servidor real da aplicação e facilita as chamadas externas às APIs

