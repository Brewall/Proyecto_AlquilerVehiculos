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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Devolucion;

import java.util.List;

public class MenuDevolucionesController {

    //Dao
    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private DevolucionDAO devolucionDAO = new DevolucionDAO();


    //Controles form
    @FXML
    private ComboBox<AlquileresinfoDto> comboBoxReserva;
    @FXML
    private DatePicker datePickerFechaDevolucion;
    @FXML
    private TextField textFieldcostosAdicionales;
    @FXML
    private TextArea textAreaComentariosAdicionales;

    @FXML
    public void initialize() {
        cargarReservas();
    }

    public void cargarReservas() {
        List<AlquileresinfoDto> alquileresinfoDtos = alquilerDAO.obtenerAlquileresInfo();
        ObservableList<AlquileresinfoDto> alquileresinfoDtoObservable = FXCollections.observableArrayList(alquileresinfoDtos);
        comboBoxReserva.setItems(alquileresinfoDtoObservable);
    }

    public void clickComboBoxReserva(ActionEvent actionEvent) {

    }

    public void clickRegistrarDevolucion(ActionEvent actionEvent) {
        try {
            Devolucion devolucion = new Devolucion();

            devolucion.setIdAlquiler(comboBoxReserva.getValue().getIdAlquiler());
            devolucion.setFechaDevolucion(datePickerFechaDevolucion.getValue().toString());
            devolucion.setCostosDano(Double.parseDouble(textFieldcostosAdicionales.getText()));
            devolucion.setObservaciones(textAreaComentariosAdicionales.getText());

            boolean success = devolucionDAO.createDevolucion(devolucion);
            if (success) {
                mostrarAlerta("Éxito", "Devolucio Exitosa", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "No se pudo hacer la Devolucion", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarReservas();
            limpiarCampos();
        }
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

    private void limpiarCampos() {
        comboBoxReserva.getSelectionModel().clearSelection();
        datePickerFechaDevolucion.setValue(null);
        textFieldcostosAdicionales.clear();
        textAreaComentariosAdicionales.clear();

    }
}
