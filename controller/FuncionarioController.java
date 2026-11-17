package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Funcionario;
import model.FuncionarioRepository;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FuncionarioController implements Initializable {

    @FXML private TextField txtNome;
    @FXML private TextField txtCargo;
    @FXML private TextField txtSalario;

    @FXML private Button btnSalvar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnDeletar;

    @FXML private TableView<Funcionario> tabelaFuncionarios;
    @FXML private TableColumn<Funcionario, Integer> colId;
    @FXML private TableColumn<Funcionario, String> colNome;
    @FXML private TableColumn<Funcionario, String> colCargo;
    @FXML private TableColumn<Funcionario, Double> colSalario;

    private FuncionarioRepository repository;
    private ObservableList<Funcionario> listaObservable;
    private Funcionario funcionarioSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repository = new FuncionarioRepository();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        tabelaFuncionarios.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarFuncionario(newValue)
        );

        atualizarTabela();
    }

    //Create
    @FXML
    private void handleSalvar() {
        if (!validarCampos()) return;

        try {
            String nome = txtNome.getText().trim();
            String cargo = txtCargo.getText().trim();
            double salario = Double.parseDouble(txtSalario.getText().replace(",", "."));

            Funcionario novo = new Funcionario(nome, cargo, salario);
            repository.salvarOuAtualizar(novo);

            atualizarTabela();
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Digite um valor numérico válido para o salário.");
        } catch (SQLException e) {
            mostrarAlerta("Erro no Banco", "Falha ao cadastrar funcionário: " + e.getMessage());
        }
    }

    //Update
    @FXML
    private void handleAtualizar() {
        if (funcionarioSelecionado == null || !validarCampos()) return;

        try {
            funcionarioSelecionado.setNome(txtNome.getText().trim());
            funcionarioSelecionado.setCargo(txtCargo.getText().trim());
            funcionarioSelecionado.setSalario(Double.parseDouble(txtSalario.getText().replace(",", ".")));

            repository.salvarOuAtualizar(funcionarioSelecionado);

            atualizarTabela();
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Digite um valor numérico válido para o salário.");
        } catch (SQLException e) {
            mostrarAlerta("Erro no Banco", "Falha ao atualizar funcionário: " + e.getMessage());
        }
    }

    //Delete
    @FXML
    private void handleDeletar() {
        if (funcionarioSelecionado == null) return;

        try {
            repository.deletar(funcionarioSelecionado);
            atualizarTabela();
            limparCampos();
        } catch (SQLException e) {
            mostrarAlerta("Erro no Banco", "Falha ao excluir funcionário: " + e.getMessage());
        }
    }

    // Read
    private void atualizarTabela() {
        try {
            listaObservable = FXCollections.observableArrayList(repository.listarTodos());
            tabelaFuncionarios.setItems(listaObservable);
            tabelaFuncionarios.refresh();
        } catch (SQLException e) {
            mostrarAlerta("Erro no Banco", "Não foi possível carregar os registros: " + e.getMessage());
        }
    }

    private void selecionarFuncionario(Funcionario funcionario) {
        funcionarioSelecionado = funcionario;
        if (funcionario != null) {
            txtNome.setText(funcionario.getNome() != null ? funcionario.getNome() : "");
            txtCargo.setText(funcionario.getCargo() != null ? funcionario.getCargo() : "");
            txtSalario.setText(funcionario.getSalario() != null ? String.valueOf(funcionario.getSalario()) : "");

            btnSalvar.setDisable(true);
            btnAtualizar.setDisable(false);
            btnDeletar.setDisable(false);
        }
    }

    @FXML
    private void limparCampos() {
        txtNome.clear();
        txtCargo.clear();
        txtSalario.clear();
        funcionarioSelecionado = null;
        tabelaFuncionarios.getSelectionModel().clearSelection();

        btnSalvar.setDisable(false);
        btnAtualizar.setDisable(true);
        btnDeletar.setDisable(true);
    }

    private boolean validarCampos() {
        if (txtNome.getText().isBlank() || txtCargo.getText().isBlank() || txtSalario.getText().isBlank()) {
            mostrarAlerta("Campos Obrigatórios", "Preencha Nome, Cargo e Salário antes de continuar.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}