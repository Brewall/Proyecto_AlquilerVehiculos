package controller;

import dao.*;
import dto.ClienteInfoDto;
import dto.EmpleadosInfoDto;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
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
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class MenuReservasController {

    //DAO
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private AlquilerDAO alquilerDAO = new AlquilerDAO();
    private PersonaDAO personaDAO = new PersonaDAO();
    private EmpresaDAO empresaDAO = new EmpresaDAO();


    //Objeto
    private Alquiler alquilerSeleccionado;

    //Controladores form
    @FXML
    private ComboBox<Vehiculo> comboBoxVehiculo;
    @FXML
    private ComboBox<ClienteInfoDto> comboBoxCliente;
    @FXML
    private ComboBox<EmpleadosInfoDto> comboBoxEmpleadoReserva;
    @FXML
    private DatePicker datePickerFechaInicio;
    @FXML
    private DatePicker datePickerFechaFin;
    //Tabla view
    @FXML
    private TableView<Alquiler> listaAlquilerReservas;
    @FXML
    private TableColumn<Alquiler, String> columnaCliente;
    @FXML
    private TableColumn<Alquiler, String> columnaVehiculo;
    @FXML
    private TableColumn<Alquiler, String> columnaEmpleado;
    @FXML
    private TableColumn<Alquiler, String> columnaFInicio;
    @FXML
    private TableColumn<Alquiler, String> columnaHoraInicio;
    @FXML
    private TableColumn<Alquiler, String> columnaFFin;
    @FXML
    private TableColumn<Alquiler, String> columnaHoraFin;


    @FXML
    public void initialize() {
        cargarVehiculos();
        cargarClientes();
        cargarEmpleados();
        cargarTablaReservas();

        listaAlquilerReservas.setOnMouseClicked(mouseEvent ->
        {
            if (mouseEvent.getClickCount() == 1) {
                alquilerSeleccionado = listaAlquilerReservas.getSelectionModel().getSelectedItem();
                if (alquilerSeleccionado != null){
                    cargarFormulario(alquilerSeleccionado);
                }
            }
        });
    }


    private void cargarFormulario(Alquiler alquilerSeleccionado) {
        // Cargar los datos del alquiler en el formulario

        comboBoxCliente.setValue(buscarClientePorId(alquilerSeleccionado.getIdCliente()));
        comboBoxVehiculo.setValue(buscarVehicloPorId(alquilerSeleccionado.getIdVehiculo()));
        comboBoxEmpleadoReserva.setValue(buscarEmpleadoPorId(alquilerSeleccionado.getIdUsuario()));

        datePickerFechaInicio.setValue(LocalDate.parse(alquilerSeleccionado.getFechaInicioReserva().substring(0, 10)));
        datePickerFechaFin.setValue(LocalDate.parse(alquilerSeleccionado.getFechaFinReserva().substring(0, 10)));


    }

    public ClienteInfoDto buscarClientePorId(int idCliente) {
        ObservableList<ClienteInfoDto> clientes = comboBoxCliente.getItems(); // Obtener todos los elementos
        for (ClienteInfoDto cliente : clientes) {
            if (cliente.getIdCliente() == idCliente) {
                return cliente; // Devuelve el cliente si coincide el ID
            }
        }
        return null; // Devuelve null si no encuentra coincidencias
    }

    public Vehiculo buscarVehicloPorId(int idvehiculo) {
        ObservableList<Vehiculo> vehiculos = comboBoxVehiculo.getItems(); // Obtener todos los elementos
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getIdVehiculo() == idvehiculo) {
                return vehiculo; // Devuelve el cliente si coincide el ID
            }
        }
        return null; // Devuelve null si no encuentra coincidencias
    }

    public EmpleadosInfoDto buscarEmpleadoPorId(int idusuario) {
        ObservableList<EmpleadosInfoDto> empleados = comboBoxEmpleadoReserva.getItems(); // Obtener todos los elementos
        for (EmpleadosInfoDto empleado : empleados) {
            if (empleado.getIdUsuario() == idusuario) {
                return empleado; // Devuelve el cliente si coincide el ID
            }
        }
        return null; // Devuelve null si no encuentra coincidencias
    }


    public void clickButtomCrearReserva(ActionEvent actionEvent) {
        try {
            Alquiler nuevoAlquiler = new Alquiler();
            nuevoAlquiler.setIdCliente(comboBoxCliente.getValue().getIdCliente());
            nuevoAlquiler.setIdVehiculo(comboBoxVehiculo.getValue().getIdVehiculo());
            nuevoAlquiler.setIdUsuario(comboBoxEmpleadoReserva.getValue().getIdUsuario());
            nuevoAlquiler.setFechaInicioReserva(datePickerFechaInicio.getValue().toString());
            nuevoAlquiler.setFechaFinReserva(datePickerFechaFin.getValue().toString());
            nuevoAlquiler.setTotalPrecio(comboBoxVehiculo.getValue().getPrecioDia());

            boolean registroAlquiler = alquilerDAO.createAlquiler(nuevoAlquiler);

            if(registroAlquiler){
                mostrarAlerta("Éxito", "ALquiler agregado correctamente", Alert.AlertType.INFORMATION);
                cargarVehiculos(); // Refrescar la lista
                //limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo registrar el alquiler", Alert.AlertType.ERROR);
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cargarTablaReservas();
            limpiarCampos();
        }
    }


    public void cargarTablaReservas() {
        //data  de la tabla reserva
        List<Alquiler> alquilerReserva = alquilerDAO.getAllAlquileres();
        ObservableList<Alquiler> alquilerReservaObservable = FXCollections.observableArrayList(alquilerReserva);

        //col cliente
        columnaCliente.setCellValueFactory(cellData -> {
            String dataCol = "";
            int idCliente = cellData.getValue().getIdCliente();
            Cliente cliente = clienteDAO.getClienteById(idCliente);
            if (cliente.getIdTipoCliente() == TipoCliente.PERSONA.getIdTipoCliente()) {
                Persona persona = personaDAO.getPersonaById(cliente.getIdPersona());
                dataCol = persona.getDni() + " " + persona.getNombres();
            } else if (cliente.getIdTipoCliente() == TipoCliente.EMPRESA.getIdTipoCliente()) {
                Empresa empresa = empresaDAO.getEmpresaById(cliente.getIdEmpresa());
                dataCol = empresa.getRuc() + " " + empresa.getRazonSocial();
            }

            return new SimpleStringProperty(dataCol);
        });

        // col vehiculo
        columnaVehiculo.setCellValueFactory(cellData -> {
            String dataCol = "";
            int idVehiculo = cellData.getValue().getIdVehiculo();
            Vehiculo vehiculo = vehiculoDAO.getVehiculosById(idVehiculo);
            dataCol = vehiculo.getMarca() + " " + vehiculo.getModelo();
            return new SimpleStringProperty(dataCol);
        });
        // col Empleado
        columnaEmpleado.setCellValueFactory(cellData ->{
            String dataCol = "";
            int idEmpleado = cellData.getValue().getIdUsuario();
            Empleado empleado = empleadoDAO.getEmpleadoById(idEmpleado);
            Persona persona = personaDAO.getPersonaById(empleado.getIdPersona());
            dataCol = persona.getNombres() + " " + persona.getApellidoPaterno();
            return new SimpleStringProperty(dataCol);
        });
        // col FechaInicio
        columnaFInicio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaInicioReserva().substring(0, 10)));

        // col Fecha Fin
        columnaFFin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaFinReserva().substring(0, 10)));


        listaAlquilerReservas.setItems(alquilerReservaObservable);
    }

    private void cargarEmpleados() {
        try {
            // Obtener los Clientes desde la base de datos
            List<EmpleadosInfoDto> empleados = empleadoDAO.getEmpleadoInfo();
            ObservableList<EmpleadosInfoDto> empleadosObservable = FXCollections.observableArrayList(empleados);

            // Asignar los Clientes al ComboBox
            comboBoxEmpleadoReserva.setItems(empleadosObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los Clientes", Alert.AlertType.ERROR);
        }
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

    private void cargarClientes() {
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

    public void clickComboBoxCliente(ActionEvent actionEvent) {
        if (comboBoxCliente.getItems().isEmpty()) { // Solo carga si está vacío
            cargarClientes();
        }
    }

    public void clickComboBoxVehiculo(ActionEvent actionEvent) {
        if (comboBoxVehiculo.getItems().isEmpty()) { // Solo carga si está vacío
            cargarVehiculos();
        }
    }

    public void clickEditarReservaExistente(ActionEvent actionEvent) {
        try{
            System.out.println("entra al metodo editar reserva existente");
        ClienteInfoDto cliente = comboBoxCliente.getValue();
        Vehiculo vehiculo = comboBoxVehiculo.getValue();
        EmpleadosInfoDto empleadosInfoDto = comboBoxEmpleadoReserva.getValue();
        String fechaInicio = datePickerFechaInicio.getValue().toString();
        String fechaFin = datePickerFechaFin.getValue().toString();

        if (cliente == null || vehiculo == null || empleadosInfoDto == null || fechaInicio.isEmpty() || fechaFin.isEmpty()){
            mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
            return;
        }

        //crear una nueva reserv
            Alquiler alquiler = new Alquiler();
            alquiler.setIdAlquiler(alquilerSeleccionado.getIdAlquiler());
            alquiler.setIdCliente(cliente.getIdCliente());
            alquiler.setIdVehiculo(vehiculo.getIdVehiculo());
            alquiler.setIdUsuario(empleadosInfoDto.getIdUsuario());
            alquiler.setFechaInicioReserva(fechaInicio);
            alquiler.setFechaFinReserva(fechaFin);
            alquiler.setTotalPrecio(vehiculo.getPrecioDia());

            boolean success = alquilerDAO.updateAlquiler(alquiler);
            if (success) {
                mostrarAlerta("Éxito", "Reserva creada correctamente", Alert.AlertType.INFORMATION);
                cargarVehiculos(); // Refrescar la lista
                limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo modificar la Reserva", Alert.AlertType.ERROR);
            }
        }catch (NumberFormatException e){
            mostrarAlerta("Error inesperado", "Ocurrió un error al intentar crear Reserva", Alert.AlertType.ERROR);
        } finally {
            cargarTablaReservas();
            limpiarCampos();
        }
    }

    public void clickButtomEliminarReserva(ActionEvent actionEvent) {
        try {
            boolean deleteReserva = alquilerDAO.deleteAlquiler(alquilerSeleccionado.getIdAlquiler());
            if(deleteReserva) {
                mostrarAlerta("Éxito", "Reserva Eliminada correctamente", Alert.AlertType.INFORMATION);
                cargarTablaReservas(); // Refrescar la lista
                limpiarCampos(); // Limpiar los campos de entrada
            } else {
                mostrarAlerta("Error", "No se pudo eliminar la Reserva", Alert.AlertType.ERROR);
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            cargarTablaReservas();
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
        if (comboBoxEmpleadoReserva.getItems().isEmpty()) { // Solo carga si está vacío
            cargarEmpleados();
        }
    }

    private void limpiarCampos() {
        comboBoxCliente.getSelectionModel().clearSelection();
        comboBoxVehiculo.getSelectionModel().clearSelection();
        comboBoxEmpleadoReserva.getSelectionModel().clearSelection();
    }
}
