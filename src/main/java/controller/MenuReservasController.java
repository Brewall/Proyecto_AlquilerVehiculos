package controller;

import dao.ClienteDAO;
import dao.PersonaDAO;
import dao.VehiculoDAO;
import javafx.application.Platform;
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
import model.Cliente;
import model.Persona;
import model.Vehiculo;

import java.util.List;


public class MenuReservasController {

    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    @FXML
    private ComboBox<Vehiculo> comboBoxVehiculo;
    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    public void initialize() {
        cargarVehiculos();
        cargarPersonas();
    }

    private void cargarVehiculos() {
        try {
            // Obtener los vehículos desde la base de datos
            List<Vehiculo> vehiculos = vehiculoDAO.getAllVehiculos();
            ObservableList<Vehiculo> vehiculosObservable = FXCollections.observableArrayList(vehiculos);

            // Asignar los vehículos al ComboBox
            comboBoxVehiculo.setItems(vehiculosObservable);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los vehículos", Alert.AlertType.ERROR);
        }
    }

    private void cargarPersonas() {
        try {
            // Obtener las personas desde la base de datos
            List<Cliente> clientes = clienteDAO.getAllClientes();
            ObservableList<Cliente> personasObservable = FXCollections.observableArrayList(clientes);

            // Asignar las personas al ComboBox
            comboBoxCliente.setItems(personasObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar las Personas", Alert.AlertType.ERROR);
        }
    }

    public void clickComboBoxCliente(ActionEvent actionEvent) {
    }

    public void clickComboBoxVehiculo(ActionEvent actionEvent) {

    }

    public void datePickerFechaInicio(ActionEvent actionEvent) {
    }

    public void clickDatePickerFechaDevolucion(ActionEvent actionEvent) {
    }



    public void clickButtomCrearReserva(ActionEvent actionEvent) {
    }

    public void clickEditarReservaExistente(ActionEvent actionEvent) {
        try {
            // Cargamos la vista del menú principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editarReservaExistente.fxml"));
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

            // Obtener el controlador de la nueva escena (MenuPrincipalController)
            MenuPrincipalController controladorMenuPrincipal = loader.getController();

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

    public void clickComboBoxEmpleadoReserva(ActionEvent actionEvent) {
    }
}
