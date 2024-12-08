package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MenuMantenimientoClientesController {
    public void textNombresCliente(ActionEvent actionEvent) {
    }

    public void textApellidoPaternoCliente(ActionEvent actionEvent) {
    }

    public void textFieldAppellidoMaternoCliente(ActionEvent actionEvent) {
    }

    public void textDniCliente(ActionEvent actionEvent) {
    }

    public void textDireccionCliente(ActionEvent actionEvent) {
    }

    public void textTelefonoCliente(ActionEvent actionEvent) {
    }

    public void textCorreoCliente(ActionEvent actionEvent) {
    }

    public void clickComboBocGeneroCliente(ActionEvent actionEvent) {
    }

    public void textFechaNacimientoCliente(ActionEvent actionEvent) {
    }

    public void clickButtomAgregarCliente(ActionEvent actionEvent) {
    }

    @FXML
    public void clickButtomRegresarMenuPrincipal(ActionEvent actionEvent) {
        try {
            // Cargamos la vista del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuPrincipal.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú principal", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void clickButtomEditarClienteTipoPersona(ActionEvent actionEvent) {
        try {
            // Cargamos la vista del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editarClienteTipoPersona.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú para editar Clientes", Alert.AlertType.ERROR);
        }
    }
}
