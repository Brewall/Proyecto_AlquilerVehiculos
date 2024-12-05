package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    @FXML
    private TableView<?> tablaDatos;

    @FXML
    private TableColumn<?, ?> columnaID;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private TableColumn<?, ?> columnaEdad;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEdad;

    @FXML
    void handleAgregar(ActionEvent event) {
        System.out.println("Agregar registro");
    }

    @FXML
    void handleEditar(ActionEvent event) {
        System.out.println("Editar registro");
    }

    @FXML
    void handleEliminar(ActionEvent event) {
        System.out.println("Eliminar registro");
    }

    @FXML
    void handleGuardar(ActionEvent event) {
        System.out.println("Guardar datos del formulario");
    }

    @FXML
    void handleSalir(ActionEvent event) {
        System.exit(0);
    }
}
