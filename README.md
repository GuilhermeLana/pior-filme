# Pior Filme

Este é um projeto Spring Boot que processa dados de filmes vencedores e calcula os intervalos entre as vitórias dos produtores.

## Requisitos

- JDK 17 ou superior
- Maven 3.8.1 ou superior

## Configuração e Execução

### Clonando o Repositório

```bash
git clone https://github.com/GuilhermeLana/pior-filme.git
cd pior-filme
```

### Construindo o Projeto

```bash
mvn clean install
```

### Executando o Projeto

```bash
mvn spring-boot:run
```

### Obtendo resultado

#### Navegador
Abra uma guia no navegador com projeto rodando e insira esse endereço 
http://localhost:8080/movies/producers-interval

#### Postman
Faça uma chamada GET no seguinte endpoint 
http://localhost:8080/movies/producers-interval

### Executando os Testes de Integração

```bash
mvn test
```
