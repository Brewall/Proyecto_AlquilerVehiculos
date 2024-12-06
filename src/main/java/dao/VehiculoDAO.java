package dao;

import model.Vehiculo;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {
    private Connection connection;
    private PreparedStatement ps;

    public VehiculoDAO(){
        this.connection = DBConnection.getConnection(); //obtiene la conexion
    }

    // Crear un nuevo vehiculo
    public boolean createVehiculo(Vehiculo vehiculo){
        String query = "INSERT INTO Vehiculo (marca,modelo, anio_vehiculo, placa, precio_hora, disponible,tipo_vehiculo) VALUES (?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1,vehiculo.getMarca());
            ps.setString(2,vehiculo.getModelo());
            ps.setString(3,vehiculo.getAnioVehiculo());
            ps.setString(4,vehiculo.getPlaca());
            ps.setDouble(5,vehiculo.getPrecioHora());
            ps.setBoolean(6,vehiculo.isDisponible());
            ps.setInt(7,vehiculo.getIdTipoVehiculo());

            return ps.execute();
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los vehiculos
    public List<Vehiculo> getAllVehiculos(){
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehiculo";
        try {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setAnioVehiculo(rs.getString("anio_vehiculo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setPrecioHora(rs.getDouble("precio_hora"));
                vehiculo.setDisponible(rs.getBoolean("disponible"));
                vehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehiculos;
    }

    // Obtener un vehiculo por su ID
    public Vehiculo getVehiculosById(int idVehiculo) {
        Vehiculo vehiculo = null;
        String query = "SELECT * FROM Vehiculo WHERE id_vehiculo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1,idVehiculo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                vehiculo = new Vehiculo();
                vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setAnioVehiculo(rs.getString("anio_vehiculo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setPrecioHora(rs.getDouble("precio_hora"));
                vehiculo.setDisponible(rs.getBoolean("disponible"));
                vehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return vehiculo;
    }

    // Actualizar un vehiculo
    public boolean updateVehiculo(Vehiculo vehiculo){
        String query = "UPDATE Vehiculo SET marca = ?, modelo = ?, anio_vehiculo = ?, placa = ?, precio_hora = ?, disponible = ?, id_tipoVehiculo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, vehiculo.getMarca());
            ps.setString(2,vehiculo.getModelo());
            ps.setString(3, vehiculo.getAnioVehiculo());
            ps.setString(4, vehiculo.getPlaca());
            ps.setDouble(5, vehiculo.getPrecioHora());
            ps.setBoolean(6, vehiculo.isDisponible());
            ps.setInt(7, vehiculo.getIdTipoVehiculo());

            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un vehiculo
    public boolean deleteVehiculo(int idVehiculo){
        String query = "DELETE FROM Vehiculo WHERE id_vehiculo = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idVehiculo);
            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
