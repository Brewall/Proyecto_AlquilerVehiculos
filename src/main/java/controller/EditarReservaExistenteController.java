package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class EditarReservaExistenteController {


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void clickComboBocReservaExistente(ActionEvent actionEvent) {
    }

    public void clickComboBoxCliente(ActionEvent actionEvent) {
    }

    public void clickComboBoxVehiculo(ActionEvent actionEvent) {
    }

    public void clickComboBoxEmpleadoReserva(ActionEvent actionEvent) {
    }

    public void datePickerFechaInicio(ActionEvent actionEvent) {
    }

    public void clickDatePickerFechaDevolucion(ActionEvent actionEvent) {
    }

    public void clickButtomGuardarCambiosReservaExistente(ActionEvent actionEvent) {
    }

    public void clickButtomEliminarReservaExistente(ActionEvent actionEvent) {
    }

    public void clickButtomRegresarMenuAnterior(ActionEvent actionEvent) {
    }
}
