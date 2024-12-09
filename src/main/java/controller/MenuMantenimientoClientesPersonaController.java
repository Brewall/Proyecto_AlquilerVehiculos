package controller;

import dao.ClienteDAO;
import dao.PersonaDAO;
import dto.ClientePersonaDto;
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
import model.Cliente;
import model.Persona;
import model.TipoCliente;

import java.sql.Timestamp;
import java.util.List;

public class MenuMantenimientoClientesPersonaController {

    //Dao
    private ClienteDAO clienteDAO = new ClienteDAO();
    private PersonaDAO personaDAO = new PersonaDAO();

    //Objetos
    private ClientePersonaDto clienteSeleccionado;


    //Controls Form
    @FXML
    private TextField textFieldNombreCliente;
    @FXML
    private TextField textFieldAppellidoCliente;
    @FXML
    private TextField textFieldAppellido2Cliente;
    @FXML
    private TextField textFieldDniCliente;
    @FXML
    private TextField textFieldDireccionCliente;
    @FXML
    private TextField textFieldTelefonoCliente;
    @FXML
    private TextField textFieldCorreoCliente;
    @FXML
    private ComboBox<String> comboBoxGeneroCliente;
    @FXML
    private TextField textFieldFechaNacimientoCliente;

    //Table ID's


    @FXML
    private TableView<ClientePersonaDto> listaClientesPersona;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaNombres;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaApellidoPaterno;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaApellidoMaterno;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaDireccion;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaTelefono;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaCorreo;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaGenero;
    @FXML
    private TableColumn<ClientePersonaDto, String> columnaFechaNacimiento;

    @FXML
    public void initialize() {
        cargarListaGenero();
        cargarClientes();

        // Evento de selección en la tabla
        listaClientesPersona.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Verificamos si es una sola clic
                clienteSeleccionado = listaClientesPersona.getSelectionModel().getSelectedItem();
                if (clienteSeleccionado != null) {
                    cargarFormulario(clienteSeleccionado);
                }
            }
        });
    }

    private void cargarFormulario(ClientePersonaDto cliente) {
        // Cargar los datos del cliente en el formulario
        textFieldNombreCliente.setText(cliente.getNombres());
        textFieldAppellidoCliente.setText(cliente.getApellidoPaterno());
        textFieldAppellido2Cliente.setText(cliente.getApellidoMaterno());
        textFieldDniCliente.setText(cliente.getDni());
        textFieldDireccionCliente.setText(cliente.getDireccion());
        textFieldTelefonoCliente.setText(cliente.getTelefono());
        textFieldCorreoCliente.setText(cliente.getCorreo());
        comboBoxGeneroCliente.setValue(cliente.getGenero());
        textFieldFechaNacimientoCliente.setText(cliente.getFechaNacimiento());
    }

    private void cargarListaGenero() {
        List<String> listGenero = List.of("Masculino", "Femenino");
        comboBoxGeneroCliente.getItems().addAll(listGenero);
    }

    private void cargarClientes() {
        try {
            List<ClientePersonaDto> listaClientePersona = clienteDAO.obtenerClientesTipoPersona();
            ObservableList<ClientePersonaDto> listaClientePersonaObservable = FXCollections.observableArrayList(listaClientePersona);

            columnaNombres.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombres()));
            columnaApellidoPaterno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidoPaterno()));
            columnaApellidoMaterno.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellidoMaterno()));
            columnaDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
            columnaTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
            columnaCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
            columnaGenero.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));
            columnaFechaNacimiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaNacimiento()));

            listaClientesPersona.setItems(listaClientePersonaObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los vehículos", Alert.AlertType.ERROR);
        }
    }

    public void clickButtomAgregarCliente(ActionEvent actionEvent) {
        try {
            System.out.println("Graba Cliente");
            String nombres = textFieldNombreCliente.getText().trim();
            String apellidoPaterno = textFieldAppellidoCliente.getText().trim();
            String apellidoMaterno = textFieldAppellido2Cliente.getText().trim();
            String dni = textFieldDniCliente.getText().trim();
            String direccion = textFieldDireccionCliente.getText().trim();
            String telefono = textFieldTelefonoCliente.getText().trim();
            String correo = textFieldCorreoCliente.getText().trim();
            String genero = comboBoxGeneroCliente.getValue();
            String fechaNacimiento = textFieldFechaNacimientoCliente.getText().trim();

            if (nombres.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || dni.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty() || genero == null || fechaNacimiento.isEmpty()) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }

            //Crear una validacion si el cliente ya existe con el mismo DNI

            Persona persona = new Persona();
            persona.setNombres(nombres);
            persona.setApellidoPaterno(apellidoPaterno);
            persona.setApellidoMaterno(apellidoMaterno);
            persona.setDni(dni);
            persona.setDireccion(direccion);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setGenero(genero);
            persona.setFechaNacimiento(fechaNacimiento);

            int idpersona = personaDAO.createPersona(persona);
            System.out.println("Recibe el id persona: " + idpersona);

            Cliente cliente = new Cliente();
            cliente.setIdTipoCliente(TipoCliente.PERSONA.getIdTipoCliente());
            cliente.setIdPersona(idpersona);
            cliente.setFechaRegistro(new Timestamp(System.currentTimeMillis()));

            System.out.println(cliente);
            // Guardar en la base de datos
            if (clienteDAO.createCliente(cliente)) {
                mostrarAlerta("Éxito", "Cliente agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el Cliente", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarClientes();
        }
    }

    private void limpiarCampos() {
        textFieldNombreCliente.clear();
        textFieldAppellidoCliente.clear();
        textFieldAppellido2Cliente.clear();
        textFieldDniCliente.clear();
        textFieldDireccionCliente.clear();
        textFieldTelefonoCliente.clear();
        textFieldCorreoCliente.clear();
        textFieldFechaNacimientoCliente.clear();
        comboBoxGeneroCliente.getSelectionModel().clearSelection();
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


    public void clickButtomEditarClientePersona(ActionEvent actionEvent) {

        try {
            System.out.println("Editar Cliente");
            String nombres = textFieldNombreCliente.getText().trim();
            String apellidoPaterno = textFieldAppellidoCliente.getText().trim();
            String apellidoMaterno = textFieldAppellido2Cliente.getText().trim();
            String dni = textFieldDniCliente.getText().trim();
            String direccion = textFieldDireccionCliente.getText().trim();
            String telefono = textFieldTelefonoCliente.getText().trim();
            String correo = textFieldCorreoCliente.getText().trim();
            String genero = comboBoxGeneroCliente.getValue();
            String fechaNacimiento = textFieldFechaNacimientoCliente.getText().trim();

            if (nombres.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || dni.isEmpty() || direccion.isEmpty() || telefono.isEmpty() || correo.isEmpty() || genero == null || fechaNacimiento.isEmpty()) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }


            Persona persona = new Persona();
            persona.setIdPersona(clienteSeleccionado.getIdPersona());
            persona.setNombres(nombres);
            persona.setApellidoPaterno(apellidoPaterno);
            persona.setApellidoMaterno(apellidoMaterno);
            persona.setDni(dni);
            persona.setDireccion(direccion);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setGenero(genero);
            persona.setFechaNacimiento(fechaNacimiento);

            personaDAO.updatePersona(persona);


            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteSeleccionado.getIdCliente());
            cliente.setIdTipoCliente(TipoCliente.PERSONA.getIdTipoCliente());
            cliente.setIdPersona(clienteSeleccionado.getIdPersona());
            cliente.setFechaRegistro(new Timestamp(System.currentTimeMillis()));


            System.out.println(cliente);
            // Guardar en la base de datos
            if (clienteDAO.updateCliente(cliente)) {
                mostrarAlerta("Éxito", "Cliente actualizado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo actualizar el Cliente", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarClientes();
        }


    }

    public void clickButtomEliminarClientePersona(ActionEvent actionEvent) {
        try {
            //eliminamos el cliente
            boolean deleteCliente = clienteDAO.deleteCliente(clienteSeleccionado.getIdCliente());
            //eliminamos la persona
            boolean deletePersona = personaDAO.deletePersona(clienteSeleccionado.getIdPersona());
            if (deleteCliente && deletePersona) {
                mostrarAlerta("Éxito", "cliente eliminado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar al cliente", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarClientes();
        }

    }

}
