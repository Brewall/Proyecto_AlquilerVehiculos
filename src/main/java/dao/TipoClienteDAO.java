package dao;

import model.TipoCliente;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoClienteDAO {

    // Crear un nuevo tipo de cliente
    public boolean createTipoCliente(TipoCliente tipoCliente) {
        String query = "INSERT INTO TipoCliente (tipo_cliente) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tipoCliente.getTipoCliente());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Leer todos los tipos de cliente
    public List<TipoCliente> getAllTipoClientes() {
        List<TipoCliente> tipoClientes = new ArrayList<>();
        String query = "SELECT * FROM TipoCliente";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                TipoCliente tipoCliente = new TipoCliente();
                tipoCliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                tipoCliente.setTipoCliente(rs.getString("tipo_cliente"));
                tipoClientes.add(tipoCliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoClientes;
    }

    // Obtener un tipo de cliente por ID
    public TipoCliente getTipoClienteById(int idTipoCliente) {
        TipoCliente tipoCliente = null;
        String query = "SELECT * FROM TipoCliente WHERE id_tipoCliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idTipoCliente);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    tipoCliente = new TipoCliente();
                    tipoCliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                    tipoCliente.setTipoCliente(rs.getString("tipo_cliente"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tipoCliente;
    }

    // Actualizar un tipo de cliente
    public boolean updateTipoCliente(TipoCliente tipoCliente) {
        String query = "UPDATE TipoCliente SET tipo_cliente = ? WHERE id_tipoCliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, tipoCliente.getTipoCliente());
            preparedStatement.setInt(2, tipoCliente.getIdTipoCliente());
            return preparedStatement.executeUpdate() > 0; // Mejor usar executeUpdate que execute

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un tipo de cliente por ID
    public boolean deleteTipoCliente(int idTipoCliente) {
        String query = "DELETE FROM TipoCliente WHERE id_tipoCliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idTipoCliente);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
