package view;

import dao.VehiculoDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Vehiculo;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml")); // Asegúrate de que el FXML esté en la ruta correcta
        primaryStage.setTitle("Aplicación JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {

           /* VehiculoDAO vehiculoDAO = new VehiculoDAO();

            Vehiculo nuevoVehiculo = new Vehiculo();
            nuevoVehiculo.setMarca("MG");
            nuevoVehiculo.setModelo("MG5");
            nuevoVehiculo.setAnioVehiculo("2020");
            nuevoVehiculo.setPlaca("A1B-783");
            nuevoVehiculo.setPrecioDia(12.50);
            nuevoVehiculo.setDisponible(true);
            nuevoVehiculo.setIdTipoVehiculo(1); // Suponiendo que el tipo de vehículo es correcto

            boolean resultado = vehiculoDAO.createVehiculo(nuevoVehiculo);
            if (resultado) {
                System.out.println("Vehículo agregado correctamente");
            } else {
                System.out.println("Error al agregar el vehículo");
            }
*/


        launch(args);
    }
}
