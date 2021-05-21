DEVTEST
-------

### SOLUÇÃO PROPOSTA:
Foi criada uma API para cadastro e listagem de pontos de interesse, normalmente chamados de POIs. Como solicitado, para o cadastro de POIs foram criados endpoints com a possibilidade de Cadastrar, Alterar e Excluir um POI. Já para o serviço de listagem de POIs, foram criados dois endpoits, um para a lista geral e outro em que é possível fazer a paginação dessa lista, de forma a facilitar a visualização. Por ultimo foi criado o terceiro serviço solicitado, que é responsável pela localização de POIs na proximidade, o endpoint recebe uma localização de referência e uma distância máxima, para buscar os POIs dentro dessa faixa, o que é feito por meio de uma consulta personalizada ao banco de dados.

### TECNOLOGIAS E PADRÕES UTILIZADOS
Java 13.0.2

Maven Project

Framework Spring 2.4.5

MYSQL

GIT

API RESTFUL

Swagger2

Mockito

### INSTRUÇÕES PARA CONFIGURAR O FAZER O SETUP:
1)	Faça a importação do projeto para a sua IDE;
2)	Execute o terminal e acesse a pasta POI do projeto;
3)	No terminal, execute o comando a seguir para dar build no projeto: mvnw clean package -e OU mvn clean package -e;
4)	Ainda no terminal, execute o seguinte comando para subir os containers da API e banco de dados com o Docker: docker-compose up -d

### DOCUMENTAÇÃO 
A documentação do projeto foi gerada pelo Swagger, por meio da utilização de anotações no projeto. Para acessar a documentação que contem a explicação dos endpoins e entidades do projeto, após o setup é só fazer uma requisição GET no link a seguir:
http://localhost:8080/swagger-ui.html#/

### TESTES UNITÁRIOS
Os testes unitários automatizados estão com comentários bem detalhados a respeito do propósito de cada um, inclusive com a nomenclatura do método de forma clara e objetiva do resultado esperado.

### AUTENTICAÇÃO 
Toda os endpoints da API requerem autenticação para acesso. Por tanto deixo abaixo o passo a passo com os dados de autenticação.
1) Faça uma requisição POST no caminho http://localhost:8080/oauth/token com as seguintes informações adicionais:
a.	Auth - Type: Basic Auth  | Username: client | Password:123 
b.	Body – grant_type: password | Username: user | Password: 123
2)	Na resposta dessa requisição será informado o access_token que deve ser utilizado em todas as próximas requisições enviadas para a API, caso contrário não será possível o acesso.

### COMO EXECUTAR OS TESTES
Para facilitar a execução dos testes de funcionamento da API, preparei uma coleção de requisições que vocês podem utilizar para fazer todas iterações com a API. Para acessa-la basta clicar o link a seguir (Utilização pelo Postman) : https://www.getpostman.com/collections/5c39532eb22dda07feb7 . Para o perfeito funcionamento das requisições, você deve acessar a primeira requisição de autenticação, ela já esta preenchida com os dados necessários para a autenticação. Após enviar essa requisição, você deve copiar o access_token, informa-lo na raiz da coleção e clicar em SAVE para que todas próximas requisições fiquem com o token de acesso salvas.

![image](https://user-images.githubusercontent.com/6644825/119056042-66785480-b9a0-11eb-83be-acb18e534982.png)
![image](https://user-images.githubusercontent.com/6644825/119056249-c111b080-b9a0-11eb-9085-7f79ddec3186.png)


### DESCRIÇÃO DE CADA ENDPOINT DA APLICAÇÃO

### CADASTRO DE PONTO DE INTERESSE:

Requisição POST em http://localhost:8080/pois/cadastrar

Os parâmetros serão passados por formato JSON e como no exemplo abaixo:

JSON

{

    "nome": "Lanchonete",
    
    "coordenadaX": 20,
    
    "coordenadaY": 10
    
}


O nome não pode ser vazio, as coordenadas não podem ser negativas, caso contrário você terá um BAD REQUEST, e poderá consultar o erro especifico no log da aplicação.

### ALTERAÇÃO DE PONTO DE INTERESSE:

Requisição PUT em http://localhost:8080/pois/alterar

Os parâmetros serão passados por formato JSON e como no exemplo abaixo:

JSON

{

    "id": 1,
    
    "nome": "Lanchonete",
    
    "coordenadaX": 10,
    
    "coordenadaY": 20
    
}


O nome não pode ser vazio, as coordenadas não podem ser negativas, e é preciso informar um ID cadastrado no banco de dados caso contrário você terá um BAD REQUEST, e poderá consultar o erro especifico no log da aplicação. 

### PARA DELETAR UM POI:

Requisição DELETE em http://localhost:8080/pois/deletar/{id}

O ID do POI a ser excluido deve ser passado por meio de routeparams.

O ID não pode ser vazio e deve estar presente no banco de dados caso contrário você terá um BAD REQUEST, e poderá consultar o erro especifico no log da aplicação. 

### PARA LISTAR TODOS OS POIS:

Requisição GET em http://localhost:8080/pois/todos

A requisição não necessita de parametros e irá listar todos os POIs cadastrados no banco de dados.

### PARA LISTAR TODOS OS POIS COM PAGINAÇÃO:

Requisição GET em http://localhost:8080/pois/pagina/{numerodapaginatual}/{quantidadedepoiporpagina}

Os parametros para formar a paginação são passados por meio de routeparams.

Número da pagina atual (começa no número 0).

Quantidade de itens por pagina .

Exemplo: Em um banco com 40 POIs, informando a página atual 2 e 10 POIs por página. a requisição http:// localhost:8080/pois/pagina/2/10 irá informar do POI 21 até o 30.

### PARA LOCALIZAR POIs PROXIMOS DE UMA REFERENCIA:

Requisição GET em http://localhost:8080/pois/localizar

Para localizar POIs próximos a uma referência, você deve informar os parâmetros por meio de queryparams, que são classificados da seguinte forma:

referenciaX – utilizando o param x

referenciaY – utilizando o param y

distanciaMax – utilizando o param dmax

Exemplo: Localizar POIs próximos a referenciaX 10, referenciaY 10 e distancia máxima de 5:

http://localhost:8080/pois/localizar?x=10&y=10&dmax=5
