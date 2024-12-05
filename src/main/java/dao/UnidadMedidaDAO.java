package dao;

import model.UnidadMedida;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadMedidaDAO {
    private Connection connection;
    private PreparedStatement ps;

    public UnidadMedidaDAO() {
        this.connection = DBConnection.getConnection(); // Conexión a la base de datos
    }

    // Crear una nueva unidad de medida
    public boolean createUnidadMedida(UnidadMedida unidadMedida) {
        String query = "INSERT INTO UnidadMedida (unidad_medida, descripcion) VALUES (?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, unidadMedida.getUnidadMedida());
            ps.setString(2, unidadMedida.getDescripcion());
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las unidades de medida
    public List<UnidadMedida> getAllUnidadMedidas() {
        List<UnidadMedida> unidadMedidaList = new ArrayList<>();
        String query = "SELECT * FROM UnidadMedida";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UnidadMedida unidadMedida = new UnidadMedida();
                unidadMedida.setIdUnidadMedida(rs.getInt("id_unidadMedida"));
                unidadMedida.setUnidadMedida(rs.getString("unidad_medida"));
                unidadMedida.setDescripcion(rs.getString("descripcion"));
                unidadMedidaList.add(unidadMedida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidadMedidaList;
    }

    // Obtener una unidad de medida por su ID
    public UnidadMedida getUnidadMedidaById(int idUnidadMedida) {
        UnidadMedida unidadMedida = null;
        String query = "SELECT * FROM UnidadMedida WHERE id_unidadMedida = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUnidadMedida);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                unidadMedida = new UnidadMedida();
                unidadMedida.setIdUnidadMedida(rs.getInt("id_unidadMedida"));
                unidadMedida.setUnidadMedida(rs.getString("unidad_medida"));
                unidadMedida.setDescripcion(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unidadMedida;
    }

    // Actualizar una unidad de medida
    public boolean updateUnidadMedida(UnidadMedida unidadMedida) {
        String query = "UPDATE UnidadMedida SET unidad_medida = ?, descripcion = ? WHERE id_unidadMedida = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, unidadMedida.getUnidadMedida());
            ps.setString(2, unidadMedida.getDescripcion());
            ps.setInt(3, unidadMedida.getIdUnidadMedida());
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una unidad de medida
    public boolean deleteUnidadMedida(int idUnidadMedida) {
        String query = "DELETE FROM UnidadMedida WHERE id_unidadMedida = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUnidadMedida);
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
