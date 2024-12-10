package controller;

import dao.AlquilerDAO;
import dao.DevolucionDAO;
import dto.AlquileresinfoDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MenuDevolucionesController {

    @FXML
    private ComboBox comboBoxReserva;

    private DevolucionDAO devolucionDAO = new DevolucionDAO();
    private AlquileresinfoDto alquileresinfoDto = new AlquileresinfoDto();

    public void clickComboBoxReserva(ActionEvent actionEvent) {
        
    }

    public void clickRegistrarDevolucion(ActionEvent actionEvent) {

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
