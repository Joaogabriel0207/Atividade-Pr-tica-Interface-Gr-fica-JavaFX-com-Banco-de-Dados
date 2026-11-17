# Sistema CRUD de Gerenciamento de Funcionários de um Restaurante

Este projeto é um aplicativo desktop simples para gerenciar funcionários, estruturado no padrão **Model-View-Controller** e conectado a um banco de dados local **SQLite** usando **ORMLite**.

---

## Como o código está dividido

* **Model (`Database`, `Funcionario`, `FuncionarioRepository`):** Cuida dos dados e do banco.
  * `Database`: Abre e fecha a conexão com o arquivo `funcionarios.db`.
  * `Funcionario`: Representa o funcionário (`id`, `nome`, `cargo` e `salario`).
  * `FuncionarioRepository`: Executa as ações no banco (salvar, listar, atualizar e excluir).
* **View (`funcionario.fxml`):** É a tela visual montada para o Scene Builder, contendo os campos de texto, os botões de ação e a tabela (`TableView`).
* **Controller (`FuncionarioController`):** Faz a ponte entre a tela e o banco de dados. Ele captura o que o usuário digita ou clica na tela e manda o repositório salvar ou buscar as informações.
* **App (`App.java`):** Classe principal que inicia a janela do programa.

---

## Como as operações funcionam

1. **Read:** Ao abrir o programa ou realizar qualquer alteração, o controlador busca todos os registros no banco e preenche a `TableView`.
2. **Create:** O usuário preenche os campos e clica em **Cadastrar**. O controlador cria um objeto `Funcionario` e o salva no banco com um `ID` gerado sequencialmente.
3. **Update:** Ao clicar em uma linha da tabela, os dados sobem para os campos de texto. Após alterar os valores e clicar em **Atualizar**, o registro é sobrescrito no banco.
4. **Delete:** Ao clicar em **Excluir**, o registro **não é apagado** da tabela. O código mantém o `ID` intacto e define `nome`, `cargo` e `salario` como `null` no banco de dados. Desta foma o ID nunca é reutilizado.  

## Demonstração de Funcionamento

1. **Janela base:** Mostra os campos das variaveis de que um funcionário possue, os botões CRUD e uma tabela dos funcionarios.  

2. **Cadastrando:** Cadastro alguns funcionarios aleatoriamente.  

3. **Editando:** Um aumento foi concedido ao Roberto.  

4. **Deletando:** Roberto foi demitido, seu cadastro é apagado mas seu ID permanece mas nullificado.  

5. **Cadastro:** A fim de demonstrar a sequenciea de IDs cadastro o ROberto Segunso, que ganha um ID diferente do Roberto Primeiro.
