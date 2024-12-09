package controller;

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
import model.Usuario;


public class LoginController {
    @FXML
    private TextField textFieldUsuario;

    @FXML
    private PasswordField textFieldPasswordUsuario;

    private UsuarioDAO usuarioDAO;
    // private Usuario usuario;

    public LoginController() {
        usuarioDAO = new UsuarioDAO(); // Inicializamos el DAO
    }

    @FXML
    public void clickButtomAccederUsuario(ActionEvent actionEvent) {
        String nombreUsuario = textFieldUsuario.getText();
        String contrasenaUsuario = textFieldPasswordUsuario.getText();

        if (nombreUsuario.isEmpty() || contrasenaUsuario.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        Usuario usuario = usuarioDAO.validarUsuarioConRol(nombreUsuario, contrasenaUsuario);

        if (usuario != null) {
            mostrarAlerta("Bienvenido", "Inicio de sesión exitoso, bienvenido " + usuario.getNombreUsuario(), Alert.AlertType.INFORMATION);
            // Aquí redirigimos al menú principal
            try {
                // Cargamos la vista del menú principal
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menuPrincipal.fxml"));
                Parent root = loader.load();

                // Pasar el objeto Usuario al controlador del menú principal (si es necesario)
                MenuPrincipalController menuController = loader.getController();
                //menuController.equals(usuario);
                menuController.setUsuario(usuario);  // Establecer el usuario en el controlador del menú

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

    /*public Usuario getUsuario() {
        return usuario;
    }*/

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipoAlerta) {
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
