package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Usuario;
import util.UserSession;

public class MenuPrincipalController {

    @FXML
    private VBox idmodvehiculos;

    @FXML
    private VBox idmodclientes;

    private Usuario usuario;

    @FXML
    public void initialize() {
        comprobarRolUsuario();
    }

    // Método para establecer el usuario y comprobar el rol
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        comprobarRolUsuario();
    }

    // Método para comprobar el rol del usuario y ocultar módulos si no es Administrador
    public void comprobarRolUsuario() {
        if (UserSession.getRol() != null && UserSession.getRol().getIdRol() != 1) {
            // Ocultar los módulos de Mantenimiento de Vehículos y Clientes
            idmodvehiculos.setVisible(false);
            idmodclientes.setVisible(false);
        }
    }


    @FXML
    public void clickButtomAccederReservas(ActionEvent actionEvent) {
        try {
            // Cargamos la vista de Reservas
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuReservas.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú de reservas", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clickButtomAccederDevoluciones(ActionEvent actionEvent) {
        try {
            // Cargamos la vista de Devoluciones
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuDevoluciones.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú de devoluciones", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clickButtomAccederMantenimientoVehiculo(ActionEvent actionEvent) {
        try {
            // Cargamos la vista de Mantenimiento de Vehículo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuMantenimientoVehiculos.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú de mantenimiento de vehículos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clickButtomAccederMantenimientoClientes(ActionEvent actionEvent) {
        try {
            // Cargamos la vista de Mantenimiento de Clientes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuTipoCliente.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú de mantenimiento de clientes", Alert.AlertType.ERROR);
        }
    }


    public void clickButtomAccederConsultaAlquiler(ActionEvent actionEvent) {
        try {
            // Cargamos la vista de Mantenimiento de Clientes
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuConsultaAlquileres.fxml"));
            Parent root = loader.load();

            // Creamos la nueva escena
            Scene scene = new Scene(root);

            // Obtenemos el Stage actual y lo cambiamos
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el menú de mantenimiento de clientes", Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
