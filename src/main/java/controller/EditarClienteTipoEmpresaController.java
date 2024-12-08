package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class EditarClienteTipoEmpresaController {


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void clickComboBoxClienteEmpresa(ActionEvent actionEvent) {
    }

    public void clickButtomGuardarCambiosClienteTipoEmpresa(ActionEvent actionEvent) {
    }

    public void clickButtomEliminarClienteTipoEmpresa(ActionEvent actionEvent) {
    }

    public void clickButtomRegresarMenuAnterior(ActionEvent actionEvent) {
        try {
            // Cargamos la vista del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuMantenimientoClientesEmpresa.fxml"));
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
}
