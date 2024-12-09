package controller;

import dao.EmpleadoDAO;
import dao.RolDAO;
import javafx.event.ActionEvent;
import dao.UsuarioDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Empleado;
import model.Rol;
import model.Usuario;
import util.UserSession;


public class LoginController {
    //Dao
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private RolDAO rolDAO = new RolDAO();


    @FXML
    private TextField textFieldUsuario;

    @FXML
    private PasswordField textFieldPasswordUsuario;






    @FXML
    public void clickButtomAccederUsuario(ActionEvent actionEvent) {
        String nombreUsuario = textFieldUsuario.getText();
        String contrasenaUsuario = textFieldPasswordUsuario.getText();

        if (nombreUsuario.isEmpty() || contrasenaUsuario.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        Usuario usuario = usuarioDAO.validarUsuario(nombreUsuario, contrasenaUsuario);
        System.out.println(usuario);

        if (usuario != null) {

            //Buscar Empleado
            Empleado empleado = empleadoDAO.getEmpleadoById(usuario.getIdEmpleado());
            //Buscar Rol
            Rol rol = rolDAO.getRolById(empleado.getIdRol());
            // Iniciar la sesión del usuario
            UserSession.startSession(usuario,empleado,rol);

            mostrarAlerta("Bienvenido", "Inicio de sesión exitoso, bienvenido " + usuario.getNombreUsuario(), Alert.AlertType.INFORMATION);
            // Aquí redirigimos al menú principal
            try {
                // Cargamos la vista del menú principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuPrincipal.fxml"));
                Parent root = loader.load();

                // Pasar el objeto Usuario al controlador del menú principal (si es necesario)
                MenuPrincipalController menuController = loader.getController();


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
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }


    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
