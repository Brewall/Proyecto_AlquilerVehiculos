package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class EditarClienteTipoPersonaController {


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    public void clickComboBoxClienteTipoPersona(ActionEvent actionEvent) {
    }

    public void clickComboBoxSeleccionarGenero(ActionEvent actionEvent) {
    }

    public void clickButtomGuardarCambiosClienteTipoPersona(ActionEvent actionEvent) {
    }

    public void clickButtomEliminarClienteTipoPersona(ActionEvent actionEvent) {
    }

    public void clickButtomRegresarMenuAnterior(ActionEvent actionEvent) {
    }
}
