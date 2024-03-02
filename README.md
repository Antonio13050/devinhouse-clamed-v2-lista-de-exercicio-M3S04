# Sistema de Avaliação de Livros

Este projeto consiste no desenvolvimento de um sistema de avaliação de livros, onde os usuários podem se registrar, cadastrar livros, avaliar livros cadastrados por outros usuários e visualizar informações sobre os livros e suas avaliações.

## Funcionalidades Implementadas

- **Cadastro de Usuário**: Os usuários podem se registrar por meio de um endpoint público, fornecendo nome, e-mail e senha.

- **Autenticação de Usuário**: O Spring Security é configurado para autenticar os usuários com base em seu e-mail e senha.

- **Cadastro de Livros**: Usuários autenticados podem cadastrar livros, fornecendo título e ano de publicação.

- **Avaliação de Livros**: Usuários autenticados podem avaliar livros cadastrados por outros usuários, fornecendo uma nota de 1 a 5. O usuário não pode avaliar seus próprios livros.

- **Listagem de Livros**: Usuários autenticados podem listar os livros cadastrados na base de dados, obtendo informações como título, ano de publicação e média de avaliações.

- **Consulta de Livro Específico**: Usuários autenticados podem buscar informações detalhadas sobre um livro específico, incluindo sua lista de avaliações e a média de avaliações, bem como a contagem de avaliações para cada nota de 1 a 5.

## Funcionalidades Adicionais Implementadas

- Testes Unitários com Mockito: Foram adicionados testes unitários para as operações de cadastro de usuário, cadastro de livros, listagem de livros, busca por um livro e cadastro de avaliação, utilizando o Mockito para simular o comportamento de dependências.

- Testes de Integração com TestContainers: Testes de integração foram implementados para todos os endpoints dos controladores de livros e usuários, utilizando o TestContainers para configurar um ambiente de teste com um banco de dados PostgreSQL em um contêiner Docker.

## Estrutura do Projeto

O projeto está estruturado da seguinte forma:

- `src/main/java/com/example/miniprojeoavaliacoess4`: Contém o código-fonte Java do projeto.
  - `security/`: Contém as classes de configuração do Spring Security.
  - `controller/`: Contém os controladores responsáveis por receber e responder às requisições HTTP.
  - `service/`: Contém os serviços responsáveis por implementar a lógica de negócio.
  - `model/`: Contém as entidades do banco de dados.
  - `repository/`: Contém as interfaces de acesso ao banco de dados.
- `src/main/resources/`: Contém os recursos estáticos e de configuração do projeto.

## Tecnologias Utilizadas

- Spring Boot
- Spring Security
- Hibernate
- PostgreSQL
- Bean Validation
- Maven
- Mockito
- TestContainers

## Como Contribuir

Se deseja contribuir para este projeto, siga estas etapas:

1. Faça um fork deste repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`).
3. Faça commit de suas alterações (`git commit -am 'Adicionando nova feature'`).
4. Faça push para a branch (`git push origin feature/nova-feature`).
5. Crie um novo Pull Request.

## Licença

Este projeto está licenciado sob a licença [MIT](LICENSE) - veja o arquivo LICENSE para mais detalhes.
