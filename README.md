# TesteMSI-Rafael-Teodoro
Sistema de Cadastro de Pessoas Físicas
Este projeto é uma API desenvolvida em Spring Boot para cadastro e gerenciamento de pessoas físicas. Inclui funcionalidades de CRUD (Criar, Ler, Atualizar, Deletar) e foi configurado para usar o banco de dados H2 em memória.

Tecnologias Utilizadas
Java com Spring Boot
Banco de Dados H2 em memória
Gson para manipulação de JSON
Postman para testes de API

Acesse o Banco de Dados H2:

O console do H2 estará disponível em http://localhost:8080/h2-console.
Configurações do banco H2:
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: Em branco
Essas configurações também estão disponíveis no arquivo application.properties:

Endpoints da API
1. Listar Todos os Endereços
Descrição: Retorna uma lista de todos os endereços cadastrados.
Endpoint: GET /endereco

2. Consultar Endereço por ID
Descrição: Retorna o endereço com o ID especificado.
Endpoint: GET /endereco/{id}

3. Cadastrar Novo Endereço
Descrição: Adiciona um novo endereço para uma pessoa existente.
Endpoint: POST /endereco/{pessoaId}
C
Exemplo de Resposta: Retorna o ID do novo endereço.
4. Atualizar Endereço
Descrição: Atualiza os dados de um endereço existente.
Endpoint: PUT /endereco

Exemplo de Resposta: Código de status 200 OK se a atualização for bem-sucedida.
5. Excluir Endereço
Descrição: Remove um endereço pelo ID.
Endpoint: DELETE /endereco/{id}
Exemplo de Resposta: Código de status 200 OK se a exclusão for bem-sucedida.
6. Consultar um CEP Externo (ViaCEP)
Descrição: Consulta um endereço externo com base no CEP usando a API ViaCEP.
Endpoint: GET /endereco/cep/{cep}

Testes com Postman
Para realizar testes nos endpoints acima com o Postman:

Exemplo de Requisição POST para Cadastrar Endereço:

Método: POST
URL: http://localhost:8080/endereco/{pessoaId}

Teste de Consulta de CEP com GET:

Método: GET
URL: http://localhost:8080/endereco/cep/12345678

Observações
Banco de Dados H2: O H2 é um banco em memória, então os dados são apagados quando o servidor é encerrado.
Tratamento de Exceções: As respostas com erro retornarão um JSON com detalhes da falha, como formato inválido de CEP ou ID inexistente.
