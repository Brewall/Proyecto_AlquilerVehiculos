package controller;

import dao.AlquilerDAO;
import dao.ClienteDAO;
import dao.VehiculoDAO;
import dto.AlquilerReporteDto;
import dto.ClienteInfoDto;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Vehiculo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MenuConsultaAlquileresController {

    //Dao
    private ClienteDAO clienteDAO = new ClienteDAO();
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private AlquilerDAO alquilerDAO = new AlquilerDAO();

    //Controles Form

    @FXML
    private ComboBox<ClienteInfoDto> comboBoxCliente;
    @FXML
    private ComboBox<Vehiculo> comboBoxVehiculo;

    @FXML
    private TableView<AlquilerReporteDto> tablaReporteAlquiler;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaIdReserva;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaCliente;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaTipoCliente;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaDoc;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaMarca;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaModelo;
    @FXML
    private TableColumn<AlquilerReporteDto, Integer> columnaAño;
    @FXML
    private TableColumn<AlquilerReporteDto, LocalDate> columnaFechaInicio;
    @FXML
    private TableColumn<AlquilerReporteDto, LocalDate> columnaFechaFin;
    @FXML
    private TableColumn<AlquilerReporteDto, String> columnaEstado;


    //Objetos


    @FXML
    public void initialize() {
        cargarDataClientes();
        cargarDataVehiculos();
        cargarDataAlquileres();

    }

    private void cargarDataClientes() {
        try {
            // Obtener los Clientes desde la base de datos
            List<ClienteInfoDto> clientes = clienteDAO.getClienteInfo();
            ObservableList<ClienteInfoDto> personasObservable = FXCollections.observableArrayList(clientes);

            // Asignar los Clientes al ComboBox
            comboBoxCliente.setItems(personasObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los Clientes", Alert.AlertType.ERROR);
        }
    }

    private void cargarDataVehiculos() {
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

    public void cargarDataAlquileres() {
        List<AlquilerReporteDto> alquilerReporteDtos = alquilerDAO.obtenerReporteAlquileres();
        ObservableList<AlquilerReporteDto> alquilerReporteDtosObservable = FXCollections.observableArrayList(alquilerReporteDtos);


        columnaIdReserva.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdAlquiler())));
        columnaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCliente()));
        columnaTipoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoCliente()));
        columnaDoc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocCliente()));
        columnaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        columnaModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        columnaAño.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnioVehiculo()));

        columnaFechaInicio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaInicio()));
        columnaFechaFin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaFin()));
        //columnaEstado

        tablaReporteAlquiler.setItems(alquilerReporteDtosObservable);

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


    @FXML
    public void buttomProcesar() {

        Integer idCliente = comboBoxCliente.getValue() != null ? comboBoxCliente.getValue().getIdCliente() : null;
        Integer idVehiculo = comboBoxVehiculo.getValue() != null ? comboBoxVehiculo.getValue().getIdVehiculo() : null;



        List<AlquilerReporteDto> alquilerReporteDtos = alquilerDAO.obtenerReporteAlquileresFiltrado(idCliente, idVehiculo);
        ObservableList<AlquilerReporteDto> alquilerReporteDtosObservable = FXCollections.observableArrayList(alquilerReporteDtos);

        columnaIdReserva.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getIdAlquiler())));
        columnaCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreCliente()));
        columnaTipoCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTipoCliente()));
        columnaDoc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDocCliente()));
        columnaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarca()));
        columnaModelo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModelo()));
        columnaAño.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnioVehiculo()));

        columnaFechaInicio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaInicio()));
        columnaFechaFin.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFechaFin()));
        //columnaEstado

        tablaReporteAlquiler.setItems(alquilerReporteDtosObservable);

    }


    @FXML
    public void buttomLimpiar() {
        comboBoxCliente.getSelectionModel().clearSelection();
        comboBoxVehiculo.getSelectionModel().clearSelection();
        cargarDataAlquileres();
    }


    @FXML
    public void clickButtomExportarTXT() {
        // Obtener los datos de la tabla
        ObservableList<AlquilerReporteDto> datos = tablaReporteAlquiler.getItems();

        if (datos == null || datos.isEmpty()) {
            mostrarAlerta("Información", "No hay datos para exportar", Alert.AlertType.INFORMATION);
            return;
        }

        // Crear el FileChooser para seleccionar la ubicación y el nombre del archivo
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte TXT");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos TXT", "*.txt"));
        fileChooser.setInitialFileName("reporte_alquileres.txt");

        // Mostrar el cuadro de diálogo para guardar el archivo
        Stage stage = (Stage) tablaReporteAlquiler.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Escribir encabezados
                writer.write(String.format("%-12s %-20s %-15s %-15s %-15s %-15s %-5s %-15s %-15s",
                        "ID Reserva", "Cliente", "Tipo Cliente", "Doc", "Marca", "Modelo", "Año", "Fecha Inicio", "Fecha Fin"));
                writer.newLine();
                writer.write("-------------------------------------------------------------------------------------------------------------------------------");
                writer.newLine();

                // Escribir los datos de la tabla
                for (AlquilerReporteDto dto : datos) {
                    String row = String.format("%-12s %-20s %-15s %-15s %-15s %-15s %-5d %-15s %-15s",
                            dto.getIdAlquiler(),
                            dto.getNombreCliente() != null ? dto.getNombreCliente() : "-",
                            dto.getTipoCliente() != null ? dto.getTipoCliente() : "-",
                            dto.getDocCliente() != null ? dto.getDocCliente() : "-",
                            dto.getMarca() != null ? dto.getMarca() : "-",
                            dto.getModelo() != null ? dto.getModelo() : "-",
                            dto.getAnioVehiculo(),
                            dto.getFechaInicio() != null ? dto.getFechaInicio().toString() : "-",
                            dto.getFechaFin() != null ? dto.getFechaFin().toString() : "-");
                    writer.write(row);
                    writer.newLine();
                }

                mostrarAlerta("Éxito", "El reporte TXT se generó correctamente en: " + file.getAbsolutePath(), Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarAlerta("Error", "Ocurrió un error al generar el reporte: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

}
