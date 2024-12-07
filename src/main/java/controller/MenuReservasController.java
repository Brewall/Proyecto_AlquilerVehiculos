package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



public class MenuReservasController {
    public void clickComboBoxCliente(ActionEvent actionEvent) {
    }

    public void clickComboBoxVehiculo(ActionEvent actionEvent) {
    }

    public void datePickerFechaInicio(ActionEvent actionEvent) {
    }

    public void textHoraInicio(ActionEvent actionEvent) {
    }

    public void clickDatePickerFechaDevolucion(ActionEvent actionEvent) {
    }

    public void textHoraDevolucion(ActionEvent actionEvent) {
    }

    public void clickComboBoxReserva(ActionEvent actionEvent) {
    }

    public void clickButtomCrearReserva(ActionEvent actionEvent) {
    }

    public void clickEditarReservaExistente(ActionEvent actionEvent) {
    }

    public void clickButtomCancelarReserva(ActionEvent actionEvent) {
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
}
