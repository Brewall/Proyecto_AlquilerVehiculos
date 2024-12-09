package dao;

import model.Vehiculo;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // Crear un nuevo vehiculo
    public boolean createVehiculo(Vehiculo vehiculo) {
        String query = "INSERT INTO Vehiculo (marca, modelo, anio_vehiculo, placa, precio_dia, disponible, id_tipoVehiculo) VALUES (?,?,?,?,?,?,?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setString(3, vehiculo.getAnioVehiculo());
            ps.setString(4, vehiculo.getPlaca());
            ps.setDouble(5, vehiculo.getPrecioDia());
            ps.setBoolean(6, vehiculo.isDisponible());
            ps.setInt(7, vehiculo.getIdTipoVehiculo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los vehiculos
    public List<Vehiculo> getAllVehiculos() {
        List<Vehiculo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehiculo";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vehiculo vehiculo = new Vehiculo();
                vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
                vehiculo.setMarca(rs.getString("marca"));
                vehiculo.setModelo(rs.getString("modelo"));
                vehiculo.setAnioVehiculo(rs.getString("anio_vehiculo"));
                vehiculo.setPlaca(rs.getString("placa"));
                vehiculo.setPrecioDia(rs.getDouble("precio_dia"));
                vehiculo.setDisponible(rs.getBoolean("disponible"));
                vehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
                vehiculos.add(vehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }

    // Obtener un vehiculo por su ID
    public Vehiculo getVehiculosById(int idVehiculo) {
        Vehiculo vehiculo = null;
        String query = "SELECT * FROM Vehiculo WHERE id_vehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idVehiculo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vehiculo = new Vehiculo();
                    vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
                    vehiculo.setMarca(rs.getString("marca"));
                    vehiculo.setModelo(rs.getString("modelo"));
                    vehiculo.setAnioVehiculo(rs.getString("anio_vehiculo"));
                    vehiculo.setPlaca(rs.getString("placa"));
                    vehiculo.setPrecioDia(rs.getDouble("precio_dia"));
                    vehiculo.setDisponible(rs.getBoolean("disponible"));
                    vehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehiculo;
    }

    // Actualizar un vehiculo
    public boolean updateVehiculo(Vehiculo vehiculo) {
        String query = "UPDATE Vehiculo SET marca = ?, modelo = ?, anio_vehiculo = ?, placa = ?, precio_dia = ?, disponible = ?, id_tipoVehiculo = ? WHERE id_vehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, vehiculo.getMarca());
            ps.setString(2, vehiculo.getModelo());
            ps.setString(3, vehiculo.getAnioVehiculo());
            ps.setString(4, vehiculo.getPlaca());
            ps.setDouble(5, vehiculo.getPrecioDia());
            ps.setBoolean(6, vehiculo.isDisponible());
            ps.setInt(7, vehiculo.getIdTipoVehiculo());
            ps.setInt(8, vehiculo.getIdVehiculo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un vehiculo
    public boolean deleteVehiculo(int idVehiculo) {
        String query = "DELETE FROM Vehiculo WHERE id_vehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idVehiculo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
