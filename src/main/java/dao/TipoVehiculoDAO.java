package dao;

import model.TipoVehiculo;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoVehiculoDAO {

    // Crear un nuevo tipo de vehículo
    public boolean createTipoVehiculo(TipoVehiculo tipoVehiculo) {
        String query = "INSERT INTO TipoVehiculo (tipo_vehiculo) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, tipoVehiculo.getTipoVehiculo());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los tipos de vehículo
    public List<TipoVehiculo> getAllTipoVehiculo() {
        List<TipoVehiculo> tipoVehiculos = new ArrayList<>();
        String query = "SELECT * FROM TipoVehiculo";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoVehiculo tipoVehiculo = new TipoVehiculo();
                tipoVehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
                tipoVehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                tipoVehiculos.add(tipoVehiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoVehiculos;
    }

    // Obtener un tipo de vehículo por ID
    public TipoVehiculo getTipoVehiculoById(int idTipoVehiculo) {
        TipoVehiculo tipoVehiculo = null;
        String query = "SELECT * FROM TipoVehiculo WHERE id_tipoVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idTipoVehiculo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tipoVehiculo = new TipoVehiculo();
                    tipoVehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
                    tipoVehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoVehiculo;
    }

    // Actualizar un tipo de vehículo
    public boolean updateTipoVehiculo(TipoVehiculo tipoVehiculo) {
        String query = "UPDATE TipoVehiculo SET tipo_vehiculo = ? WHERE id_tipoVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, tipoVehiculo.getTipoVehiculo());
            ps.setInt(2, tipoVehiculo.getIdTipoVehiculo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un tipo de vehículo
    public boolean deleteTipoVehiculo(int idTipoVehiculo) {
        String query = "DELETE FROM TipoVehiculo WHERE id_tipoVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idTipoVehiculo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
