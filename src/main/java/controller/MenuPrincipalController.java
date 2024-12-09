package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import model.Usuario;

public class MenuPrincipalController {

    @FXML
    private Button buttomAccederReservas;
    @FXML
    private Button buttomAccederDevoluciones;
    @FXML
    private Button buttomAccederMantenimientoVehiculo;
    @FXML
    private Button buttomAccederMantenimientoClientes;
    @FXML
    private Button buttomAccederConsultaAlquiler;

    private Usuario usuario;


    // Este método permitirá que se pase el usuario al controlador
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Opcional: Realizar otras acciones con el usuario, como personalizar la interfaz
        comprobarRolUsuario();
    }

    // Método que puede comprobar el rol del usuario y habilitar/deshabilitar botones
    public void comprobarRolUsuario() {
        if (usuario != null) {
            if (usuario.getNombreUsuario().equals("Oficinista")) {
                // Deshabilitar botones para 'Mantenimiento Vehículo' y 'Mantenimiento Clientes'
                buttomAccederMantenimientoVehiculo.setDisable(true);
                buttomAccederMantenimientoClientes.setDisable(true);

                // Añadir manejadores de evento para los botones deshabilitados
                buttomAccederMantenimientoVehiculo.setOnMouseClicked(event -> {
                    if (buttomAccederMantenimientoVehiculo.isDisable()) {
                        mostrarAlerta("Acceso Restringido", "Tu rol no permite acceder a estas opciones", Alert.AlertType.INFORMATION);
                    }
                });

                buttomAccederMantenimientoClientes.setOnMouseClicked(event -> {
                    if (buttomAccederMantenimientoClientes.isDisable()) {
                        mostrarAlerta("Acceso Restringido", "Tu rol no permite acceder a estas opciones", Alert.AlertType.INFORMATION);
                    }
                });

            }
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
