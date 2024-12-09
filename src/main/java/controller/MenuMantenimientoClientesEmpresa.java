package controller;

import dao.ClienteDAO;
import dao.EmpresaDAO;
import dto.ClienteEmpresaDto;
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
import model.Empresa;
import model.TipoCliente;

import java.sql.Timestamp;
import java.util.List;

public class MenuMantenimientoClientesEmpresa {

    //Dao
    private ClienteDAO clienteDAO = new ClienteDAO();
    private EmpresaDAO empresaDAO = new EmpresaDAO();

    //Objetos
    private ClienteEmpresaDto clienteEmpresaSeleccionado;


    //Controls Form
    @FXML
    private TextField textFiedlRazonSocial;
    @FXML
    private TextField textFiedlRUC;
    @FXML
    private TextField textFiedlDireccion;


    //Table ID's


    @FXML
    private TableView<ClienteEmpresaDto> tablaClientesTipoEmpresa;
    @FXML
    private TableColumn<ClienteEmpresaDto, String> columnaRazonSocial;
    @FXML
    private TableColumn<ClienteEmpresaDto, String> columnaRUC;
    @FXML
    private TableColumn<ClienteEmpresaDto, String> columnaDireccion;

    @FXML
    public void initialize() {
        cargarClientesEmpresa();

        // Evento de selección en la tabla
        tablaClientesTipoEmpresa.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Verificamos si es una sola clic
                clienteEmpresaSeleccionado = tablaClientesTipoEmpresa.getSelectionModel().getSelectedItem();
                if (clienteEmpresaSeleccionado != null) {
                    cargarFormulario(clienteEmpresaSeleccionado);
                }
            }
        });
    }

    private void cargarFormulario(ClienteEmpresaDto cliente) {
        // Cargar los datos del cliente en el formulario
        textFiedlRazonSocial.setText(cliente.getRazonSocial());
        textFiedlRUC.setText(cliente.getRuc());
        textFiedlDireccion.setText(cliente.getDireccion());
    }

    private void cargarClientesEmpresa() {
        try {
            List<ClienteEmpresaDto> listaClienteEmpresa = clienteDAO.obtenerClientesTipoEmpresa();
            ObservableList<ClienteEmpresaDto> listaClienteEmpresaObservable = FXCollections.observableArrayList(listaClienteEmpresa);

            columnaRazonSocial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRazonSocial()));
            columnaRUC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRuc()));
            columnaDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));


            tablaClientesTipoEmpresa.setItems(listaClienteEmpresaObservable);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudieron cargar los vehículos", Alert.AlertType.ERROR);
        }
    }

    public void clickButtomAgregarClienteEmpresa(ActionEvent actionEvent) {
        try {
            System.out.println("Graba Cliente");
            String razonSocial = textFiedlRazonSocial.getText().trim();
            String ruc = textFiedlRUC.getText().trim();
            String direccion = textFiedlDireccion.getText().trim();


            if (razonSocial.isEmpty() || ruc.isEmpty() || direccion.isEmpty()) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }

            //Crear una validacion si el cliente ya existe con el mismo DNI

            Empresa empresa = new Empresa();
            empresa.setRazonSocial(razonSocial);
            empresa.setRuc(ruc);
            empresa.setDireccion(direccion);


            int idempresa = empresaDAO.createEmpresa(empresa);
            System.out.println("Recibe el id empresa: " + idempresa);

            Cliente cliente = new Cliente();
            cliente.setIdTipoCliente(TipoCliente.EMPRESA.getIdTipoCliente());
            cliente.setIdEmpresa(idempresa);
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
            cargarClientesEmpresa();
        }
    }

    private void limpiarCampos() {
        textFiedlRazonSocial.clear();
        textFiedlRUC.clear();
        textFiedlDireccion.clear();
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


    public void clickButtomEditarClienteEmpresa(ActionEvent actionEvent) {
        try {
            System.out.println("Graba Cliente");
            String razonSocial = textFiedlRazonSocial.getText().trim();
            String ruc = textFiedlRUC.getText().trim();
            String direccion = textFiedlDireccion.getText().trim();


            if (razonSocial.isEmpty() || ruc.isEmpty() || direccion.isEmpty()) {
                mostrarAlerta("Campos Vacíos", "Todos los campos deben estar llenos", Alert.AlertType.WARNING);
                return;
            }

            //Crear una validacion si el cliente ya existe con el mismo DNI

            Empresa empresa = new Empresa();
            empresa.setIdEmpresa(clienteEmpresaSeleccionado.getIdEmpresa());
            empresa.setRazonSocial(razonSocial);
            empresa.setRuc(ruc);
            empresa.setDireccion(direccion);


            empresaDAO.updateEmpresa(empresa);

            Cliente cliente = new Cliente();
            cliente.setIdCliente(clienteEmpresaSeleccionado.getIdCliente());
            cliente.setIdTipoCliente(TipoCliente.EMPRESA.getIdTipoCliente());
            cliente.setIdEmpresa(clienteEmpresaSeleccionado.getIdEmpresa());
            cliente.setFechaRegistro(new Timestamp(System.currentTimeMillis()));

            System.out.println(cliente);
            // Guardar en la base de datos
            if (clienteDAO.updateCliente(cliente)) {
                mostrarAlerta("Éxito", "Cliente agregado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo agregar el Cliente", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarClientesEmpresa();
        }
    }

    public void clickButtomEliminarClienteEmpresa(ActionEvent actionEvent) {
        try {
            //eliminamos el cliente
            boolean deleteCliente = clienteDAO.deleteCliente(clienteEmpresaSeleccionado.getIdCliente());
            //eliminamos la empresa
            boolean deleteEmpresa = empresaDAO.deleteEmpresa(clienteEmpresaSeleccionado.getIdEmpresa());
            if (deleteCliente && deleteEmpresa) {
                mostrarAlerta("Éxito", "cliente eliminado correctamente", Alert.AlertType.INFORMATION);
                limpiarCampos();
            } else {
                mostrarAlerta("Error", "No se pudo eliminar al cliente", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cargarClientesEmpresa();
        }

    }

}
