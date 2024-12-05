package dao;

import model.TipoMovimiento;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoMovimientoDAO {
    private Connection connection;
    private PreparedStatement ps;

    public TipoMovimientoDAO() {
        this.connection = DBConnection.getConnection(); // Conexión a la base de datos
    }

    // Crear un nuevo tipo de movimiento
    public boolean createTipoMovimiento(TipoMovimiento tipoMovimiento) {
        String query = "INSERT INTO TipoMovimiento (tipo_movimiento) VALUES (?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, tipoMovimiento.getTipoMovimiento());
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los tipos de movimiento
    public List<TipoMovimiento> getAllTipoMovimientos() {
        List<TipoMovimiento> tipoMovimientoList = new ArrayList<>();
        String query = "SELECT * FROM TipoMovimiento";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TipoMovimiento tipoMovimiento = new TipoMovimiento();
                tipoMovimiento.setIdTipoMovimiento(rs.getInt("id_tipoMovimiento"));
                tipoMovimiento.setTipoMovimiento(rs.getString("tipo_movimiento"));
                tipoMovimientoList.add(tipoMovimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoMovimientoList;
    }

    // Obtener un tipo de movimiento por su ID
    public TipoMovimiento getTipoMovimientoById(int idTipoMovimiento) {
        TipoMovimiento tipoMovimiento = null;
        String query = "SELECT * FROM TipoMovimiento WHERE id_tipoMovimiento = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idTipoMovimiento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tipoMovimiento = new TipoMovimiento();
                tipoMovimiento.setIdTipoMovimiento(rs.getInt("id_tipoMovimiento"));
                tipoMovimiento.setTipoMovimiento(rs.getString("tipo_movimiento"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoMovimiento;
    }

    // Actualizar un tipo de movimiento
    public boolean updateTipoMovimiento(TipoMovimiento tipoMovimiento) {
        String query = "UPDATE TipoMovimiento SET tipo_movimiento = ? WHERE id_tipoMovimiento = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, tipoMovimiento.getTipoMovimiento());
            ps.setInt(2, tipoMovimiento.getIdTipoMovimiento());
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un tipo de movimiento
    public boolean deleteTipoMovimiento(int idTipoMovimiento) {
        String query = "DELETE FROM TipoMovimiento WHERE id_tipoMovimiento = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idTipoMovimiento);
            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
