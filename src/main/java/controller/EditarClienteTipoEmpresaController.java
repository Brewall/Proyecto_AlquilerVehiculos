package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

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
    }
}
