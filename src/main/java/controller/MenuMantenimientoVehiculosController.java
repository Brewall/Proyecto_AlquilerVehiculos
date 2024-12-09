package controller;

import dao.TipoVehiculoDAO;
import dao.VehiculoDAO;
import dto.ClientePersonaDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import model.TipoVehiculo;
import model.Vehiculo;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.List;

public class MenuMantenimientoVehiculosController {

    //Dao
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private TipoVehiculoDAO tipoVehiculoDAO = new TipoVehiculoDAO(); // Para manejar las categorías

    //Objetos
    private Vehiculo vehiculoSeleccionado;


    //Controles Form
    @FXML
    private TextField textFieldMarcaVehiculo;
    @FXML
    private TextField textFieldModeloVehiculo;
    @FXML
    private TextField textFieldAnioVehiculo;
    @FXML
    private TextField textFieldPlacaVehiculo;
    @FXML
    private TextField textFieldPrecioVehiculo;
    @FXML
    private ComboBox<TipoVehiculo> comboBoxCategoriaVehiculo; // Cambiado a TipoVehiculo

    @FXML
    private TableView<Vehiculo> listaVehiculo;
    @FXML
    private TableColumn<Vehiculo, String> columnaMarca;
    @FXML
    private TableColumn<Vehiculo, String> columnaModelo;
    @FXML
    private TableColumn<Vehiculo, String> columnaAnio;
    @FXML
    private TableColumn<Vehiculo, String> columnaPlaca;
    @FXML
    private TableColumn<Vehiculo, Double> columnaPrecio;
    @FXML
    private TableColumn<Vehiculo, Boolean> columnaDisponibilidad;
    @FXML
    private TableColumn<Vehiculo, String> columnaCategoria;


    @FXML
    public void initialize() {
        cargarCategorias();
        cargarVehiculos();

        // Evento de selección en la tabla
        listaVehiculo.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Verificamos si es una sola clic
                vehiculoSeleccionado = listaVehiculo.getSelectionModel().getSelectedItem();
                if (vehiculoSeleccionado != null) {
                    cargarFormulario(vehiculoSeleccionado);
                }
            }
        });
    }

    private void cargarFormulario(Vehiculo vehiculo) {
        // Cargar los datos del vehiculo en el formulario
        textFieldMarcaVehiculo.setText(vehiculo.getMarca());
        textFieldModeloVehiculo.setText(vehiculo.getModelo());
        textFieldAnioVehiculo.setText(vehiculo.getAnioVehiculo());
        textFieldPlacaVehiculo.setText(vehiculo.getPlaca());
        textFieldPrecioVehiculo.setText(Double.toString(vehiculo.getPrecioDia()));
        comboBoxCategoriaVehiculo.setValue(tipoVehiculoDAO.getTipoVehiculoById(vehiculo.getIdTipoVehiculo()));
    }

    private void cargarVehiculos() {
        try {
            List<Vehiculo> vehiculos = vehiculoDAO.getAllVehiculos(); // Obtener los vehículos de la base de datos
            ObservableList<Vehiculo> vehiculosObservable = FXCollections.observableArrayList(vehiculos);
            System.out.println("Vehículos: " + vehiculos);


            // Asociamos las columnas con las propiedades de la clase Vehiculo
            columnaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
            columnaModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
            columnaAnio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAnioVehiculo()));
            columnaPlaca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaca()));
            columnaPrecio.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrecioDia()).asObject());

            // Usar un cell factory para mostrar "Disponible" o "No disponible"
            columnaDisponibilidad.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isDisponible()).asObject());
            columnaDisponibilidad.setCellFactory(column -> new TableCell<Vehiculo, Boolean>() {
                @Override
                protected void updateItem(Boolean disponible, boolean empty) {
                    super.updateItem(disponible, empty);
                    if (empty || disponible == null) {
                        setText(null);
                    } else {
                        setText(disponible ? "Disponible" : "No disponible");
                    }
                }
            });

            // Llenar la columna de categoría con el nombre del tipo de vehículo
            columnaCategoria.setCellValueFactory(cellData -> {
                int idTipoVehiculo = cellData.getValue().getIdTipoVehiculo();
                TipoVehiculo tipoVehiculo = tipoVehiculoDAO.getTipoVehiculoById(idTipoVehiculo);
                return new SimpleStringProperty(tipoVehiculo.getTipoVehiculo());
            });

            // Asignamos los datos al TableView
            listaVehiculo.setItems(vehiculosObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los vehículos", Alert.AlertType.ERROR);
        }
    }

    private void cargarCategorias() {
        try {
            List<TipoVehiculo> categorias = tipoVehiculoDAO.getAllTipoVehiculo();
            comboBoxCategoriaVehiculo.getItems().addAll(categorias); // Agregamos las categorías
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar las categorías", Alert.AlertType.ERROR);
        }
    }

    public void clickComboBoxCategoriaVehiculo(ActionEvent actionEvent) {
        if (comboBoxCategoriaVehiculo.getItems().isEmpty()) { // Solo carga si está vacío
            cargarCategorias();
        }
    }

    public void clickButtomAgregarVehiculo(ActionEvent actionEvent) {
        try {
            System.out.println("entra al metodo");
            // Validar datos ingresados
            String marca = textFieldMarcaVehiculo.getText().trim();
            String modelo = textFieldModeloVehiculo.getText().trim();
            String anio = textFieldAnioVehiculo.getText().trim();
            String placa = textFieldPlacaVehiculo.getText().trim();
            String precioStr = textFieldPrecioVehiculo.getText().trim();
            TipoVehiculo categoria = comboBoxCategoriaVehiculo.getValue();

            if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || placa.isEmpty() || precioStr.isEmpty() || categoria == null) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }

            // Validar que el precio sea un número válido
            double precio;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    mostrarAlerta("Precio inválido", "El precio debe ser mayor a 0", Alert.AlertType.WARNING);
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Formato inválido", "El precio debe ser un número válido", Alert.AlertType.WARNING);
                return;
            }

            //double precio = Double.parseDouble(precioStr);

            // Crear un objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setMarca(marca);
            vehiculo.setModelo(modelo);
            vehiculo.setAnioVehiculo(anio);
            vehiculo.setPlaca(placa);
            vehiculo.setPrecioDia(precio);
            vehiculo.setDisponible(true); // Asumimos que todos los vehículos nuevos están disponibles
            vehiculo.setIdTipoVehiculo(categoria.getIdTipoVehiculo()); // Usamos el ID de la categoría

            // System.out.println(vehiculo.toString());

            // Guardar en la base de datos
            boolean success = vehiculoDAO.createVehiculo(vehiculo);
            if (success) {
                mostrarAlerta("Éxito", "Vehículo agregado correctamente", Alert.AlertType.INFORMATION);
                cargarVehiculos(); // Refrescar la lista
                limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo agregar el vehículo", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error inesperado", "Ocurrió un error al intentar agregar el vehículo", Alert.AlertType.ERROR);
        }

            /*if (vehiculoDAO.createVehiculo(vehiculo)) {
                mostrarAlerta("Éxito", "Vehículo agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el vehículo", Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Formato Incorrecto", "El precio debe ser un número válido", Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error inesperado", Alert.AlertType.ERROR);
        }*/
    }



    public void clickButtomEditarVehiculoExistente(ActionEvent actionEvent) {
        try {
            System.out.println("entra al metodo");
            // Validar datos ingresados
            String marca = textFieldMarcaVehiculo.getText().trim();
            String modelo = textFieldModeloVehiculo.getText().trim();
            String anio = textFieldAnioVehiculo.getText().trim();
            String placa = textFieldPlacaVehiculo.getText().trim();
            String precioStr = textFieldPrecioVehiculo.getText().trim();
            TipoVehiculo categoria = comboBoxCategoriaVehiculo.getValue();

            if (marca.isEmpty() || modelo.isEmpty() || anio.isEmpty() || placa.isEmpty() || precioStr.isEmpty() || categoria == null) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }

            // Validar que el precio sea un número válido
            double precio;
            try {
                precio = Double.parseDouble(precioStr);
                if (precio <= 0) {
                    mostrarAlerta("Precio inválido", "El precio debe ser mayor a 0", Alert.AlertType.WARNING);
                    return;
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Formato inválido", "El precio debe ser un número válido", Alert.AlertType.WARNING);
                return;
            }

            //double precio = Double.parseDouble(precioStr);

            // Crear un objeto Vehiculo
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setIdVehiculo(vehiculoSeleccionado.getIdVehiculo());
            vehiculo.setMarca(marca);
            vehiculo.setModelo(modelo);
            vehiculo.setAnioVehiculo(anio);
            vehiculo.setPlaca(placa);
            vehiculo.setPrecioDia(precio);
            vehiculo.setDisponible(true); // Asumimos que todos los vehículos nuevos están disponibles
            vehiculo.setIdTipoVehiculo(categoria.getIdTipoVehiculo()); // Usamos el ID de la categoría

            // System.out.println(vehiculo.toString());

            // Guardar en la base de datos
            boolean success = vehiculoDAO.updateVehiculo(vehiculo);
            if (success) {
                mostrarAlerta("Éxito", "Vehículo agregado correctamente", Alert.AlertType.INFORMATION);
                cargarVehiculos(); // Refrescar la lista
                limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo agregar el vehículo", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error inesperado", "Ocurrió un error al intentar agregar el vehículo", Alert.AlertType.ERROR);
        }
    }

    public void clickButtomEliminarVehiculoExistente(ActionEvent actionEvent){
        try {
            boolean deleteVehiculo = vehiculoDAO.deleteVehiculo(vehiculoSeleccionado.getIdVehiculo());

            if(deleteVehiculo) {
                mostrarAlerta("Éxito", "Vehículo Eliminado correctamente", Alert.AlertType.INFORMATION);
                cargarVehiculos(); // Refrescar la lista
                limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo eliminar el vehículo", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();

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

    private void limpiarCampos() {
        textFieldMarcaVehiculo.clear();
        textFieldModeloVehiculo.clear();
        textFieldAnioVehiculo.clear();
        textFieldPlacaVehiculo.clear();
        textFieldPrecioVehiculo.clear();
        comboBoxCategoriaVehiculo.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
