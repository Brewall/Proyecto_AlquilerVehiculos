package dao;

import model.DetalleAlquiler;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetalleAlquilerDAO {
    private Connection connection;
    private PreparedStatement ps;

    public DetalleAlquilerDAO() {
        this.connection = DBConnection.getConnection(); // Obtiene la conexión
    }

    // Crear un nuevo detalle de alquiler
    public boolean createDetalleAlquiler(DetalleAlquiler detalleAlquiler) {
        String query = "INSERT INTO DetalleAlquiler (id_alquiler, id_servicio, cantidad, sub_total) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, detalleAlquiler.getIdAlquiler());
            ps.setInt(2, detalleAlquiler.getIdServicio());
            ps.setInt(3, detalleAlquiler.getCantidad());
            ps.setDouble(4, detalleAlquiler.getSubTotal());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los detalles de alquiler
    public List<DetalleAlquiler> getAllDetallesAlquiler() {
        List<DetalleAlquiler> detalles = new ArrayList<>();
        String query = "SELECT * FROM DetalleAlquiler";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleAlquiler detalle = new DetalleAlquiler();
                detalle.setIdDetalleAlquiler(rs.getInt("id_detalleAlquiler"));
                detalle.setIdAlquiler(rs.getInt("id_alquiler"));
                detalle.setIdServicio(rs.getInt("id_servicio"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setSubTotal(rs.getDouble("sub_total"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }

    // Obtener un detalle de alquiler por su ID
    public DetalleAlquiler getDetalleAlquilerById(int idDetalleAlquiler) {
        DetalleAlquiler detalle = null;
        String query = "SELECT * FROM DetalleAlquiler WHERE id_detalleAlquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idDetalleAlquiler);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                detalle = new DetalleAlquiler();
                detalle.setIdDetalleAlquiler(rs.getInt("id_detalleAlquiler"));
                detalle.setIdAlquiler(rs.getInt("id_alquiler"));
                detalle.setIdServicio(rs.getInt("id_servicio"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setSubTotal(rs.getDouble("sub_total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalle;
    }

    // Actualizar un detalle de alquiler
    public boolean updateDetalleAlquiler(DetalleAlquiler detalleAlquiler) {
        String query = "UPDATE DetalleAlquiler SET id_alquiler = ?, id_servicio = ?, cantidad = ?, sub_total = ? WHERE id_detalleAlquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, detalleAlquiler.getIdAlquiler());
            ps.setInt(2, detalleAlquiler.getIdServicio());
            ps.setInt(3, detalleAlquiler.getCantidad());
            ps.setDouble(4, detalleAlquiler.getSubTotal());
            ps.setInt(5, detalleAlquiler.getIdDetalleAlquiler());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un detalle de alquiler
    public boolean deleteDetalleAlquiler(int idDetalleAlquiler) {
        String query = "DELETE FROM DetalleAlquiler WHERE id_detalleAlquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idDetalleAlquiler);

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
