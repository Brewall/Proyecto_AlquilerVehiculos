package controller;

import dao.TipoVehiculoDAO;
import dao.VehiculoDAO;
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
import javafx.util.StringConverter;
import model.TipoVehiculo;
import model.Vehiculo;

import java.util.Arrays;
import java.util.List;



public class EditarVehiculoExistenteController {

    private TipoVehiculoDAO tipoVehiculoDAO = new TipoVehiculoDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();

    @FXML
    private ComboBox<Vehiculo> comboBoxVehiculoExistente;
    @FXML
    private ComboBox<TipoVehiculo> comboBoxTipoVehiculo;
    @FXML
    private TextField textFieldMarca;
    @FXML
    private TextField textFieldModelo;
    @FXML
    private TextField textFieldAnio;
    @FXML
    private TextField textFieldPlaca;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private CheckBox checkboxDisponible; // Declaración del CheckBox



    @FXML
    public void initialize() {
        cargarCategorias();
        cargarVehiculos();
    }

    private void cargarCategorias() {
        try {
            List<TipoVehiculo> categorias = tipoVehiculoDAO.getAllTipoVehiculo();
            comboBoxTipoVehiculo.getItems().addAll(categorias); // Agregamos las categorías
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar las categorías", Alert.AlertType.ERROR);
        }
    }

    private void cargarVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.getAllVehiculos(); // Obtener los vehículos de la base de datos
            ObservableList<Vehiculo> vehiculosObservable = FXCollections.observableArrayList(vehiculos);
            comboBoxVehiculoExistente.setItems(vehiculosObservable); // Asignamos los vehículos al ComboBox

            // Listener para cuando se selecciona un vehículo
            comboBoxVehiculoExistente.setOnAction(event -> {
                Vehiculo vehiculoSeleccionado = comboBoxVehiculoExistente.getValue();
                if (vehiculoSeleccionado != null) {
                    // Rellenar los campos con la información del vehículo seleccionado
                    textFieldMarca.setText(vehiculoSeleccionado.getMarca());
                    textFieldModelo.setText(vehiculoSeleccionado.getModelo());
                    textFieldAnio.setText(vehiculoSeleccionado.getAnioVehiculo());
                    textFieldPlaca.setText(vehiculoSeleccionado.getPlaca());
                    textFieldPrecio.setText(String.valueOf(vehiculoSeleccionado.getPrecioDia()));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los vehículos", Alert.AlertType.ERROR);
        }
    }

    public void clickComboBoxTipoVehiculo(ActionEvent actionEvent) {
    }

    public void clickButtomGuardarCambiosVehiculo(ActionEvent actionEvent) {

        // Obtener los datos del vehículo desde los campos de texto
        String marca = textFieldMarca.getText();
        String modelo = textFieldModelo.getText();
        String anio = textFieldAnio.getText();
        String placa = textFieldPlaca.getText();
        double precio = Double.parseDouble(textFieldPrecio.getText());
        boolean disponible = checkboxDisponible.isSelected();

        // Obtener el vehículo seleccionado desde el ComboBox
        Vehiculo vehiculoSeleccionado = comboBoxVehiculoExistente.getValue();

        if (vehiculoSeleccionado != null) {
            // Asignamos los nuevos valores al vehículo seleccionado
            vehiculoSeleccionado.setMarca(marca);
            vehiculoSeleccionado.setModelo(modelo);
            vehiculoSeleccionado.setAnioVehiculo(anio);
            vehiculoSeleccionado.setPlaca(placa);
            vehiculoSeleccionado.setPrecioDia(precio);
            vehiculoSeleccionado.setDisponible(disponible);

            // Llamamos al método actualizarVehiculo para actualizar el vehículo en la base de datos
            boolean actualizado = vehiculoDAO.updateVehiculo(vehiculoSeleccionado);

            if (actualizado) {
                mostrarAlerta("Éxito", "El vehículo se actualizó correctamente", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Error", "Hubo un problema al actualizar el vehículo", Alert.AlertType.ERROR);
            }
        }

    }

    public void clickButtomEliminarVehiculo(ActionEvent actionEvent) {

        // Implementa la lógica para eliminar un vehículo
        Vehiculo vehiculoSeleccionado = comboBoxVehiculoExistente.getValue();
        if (vehiculoSeleccionado != null) {
            vehiculoDAO.deleteVehiculo(vehiculoSeleccionado.getIdVehiculo());
            // Muestra una alerta indicando que el vehículo fue borrado exitosamente
            mostrarAlerta("Operación Exitosa", "Vehículo eliminado exitosamente.", Alert.AlertType.INFORMATION);

            // Limpia el ComboBox y cualquier otro campo relevante
            comboBoxVehiculoExistente.getSelectionModel().clearSelection();
            // Si tienes otros campos de texto u otros elementos que necesitan ser limpiados, los puedes limpiar aquí también
            textFieldMarca.clear();
            textFieldModelo.clear();
            textFieldAnio.clear();
            textFieldPlaca.clear();
            textFieldPrecio.clear();
        }

    }

    public void clickComboBoxVehiculoExistente(ActionEvent actionEvent) {

    }

    public void clickButtomRegresarMenuPrincipal(ActionEvent actionEvent) {
        try {
            // Cargamos la vista del menú principal
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
            mostrarAlerta("Error", "No se pudo cargar el menú anterior", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
