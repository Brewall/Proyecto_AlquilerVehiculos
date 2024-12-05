package dao;

import model.Devolucion;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevolucionDAO {
    private Connection connection;
    private PreparedStatement ps;

    public DevolucionDAO() {
        this.connection = DBConnection.getConnection(); // Conexión a la base de datos
    }

    // Crear una nueva devolución
    public boolean createDevolucion(Devolucion devolucion) {
        String query = "INSERT INTO Devolucion (fecha_devolucion, observaciones, costos_daño, total_adicional, id_alquiler) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, devolucion.getFechaDevolucion());
            ps.setString(2, devolucion.getObservaciones());
            ps.setDouble(3, devolucion.getCostosDano());
            ps.setDouble(4, devolucion.getTotalAdicional());
            ps.setInt(5, devolucion.getIdAlquiler());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las devoluciones
    public List<Devolucion> getAllDevoluciones() {
        List<Devolucion> devoluciones = new ArrayList<>();
        String query = "SELECT * FROM Devolucion";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Devolucion devolucion = new Devolucion();
                devolucion.setIdDevolucion(rs.getInt("id_devolucion"));
                devolucion.setFechaDevolucion(rs.getString("fecha_devolucion"));
                devolucion.setObservaciones(rs.getString("observaciones"));
                devolucion.setCostosDano(rs.getDouble("costos_daño"));
                devolucion.setTotalAdicional(rs.getDouble("total_adicional"));
                devolucion.setIdAlquiler(rs.getInt("id_alquiler"));
                devoluciones.add(devolucion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devoluciones;
    }

    // Obtener una devolución por su ID
    public Devolucion getDevolucionById(int idDevolucion) {
        Devolucion devolucion = null;
        String query = "SELECT * FROM Devolucion WHERE id_devolucion = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idDevolucion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                devolucion = new Devolucion();
                devolucion.setIdDevolucion(rs.getInt("id_devolucion"));
                devolucion.setFechaDevolucion(rs.getString("fecha_devolucion"));
                devolucion.setObservaciones(rs.getString("observaciones"));
                devolucion.setCostosDano(rs.getDouble("costos_daño"));
                devolucion.setTotalAdicional(rs.getDouble("total_adicional"));
                devolucion.setIdAlquiler(rs.getInt("id_alquiler"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devolucion;
    }

    // Actualizar una devolución
    public boolean updateDevolucion(Devolucion devolucion) {
        String query = "UPDATE Devolucion SET fecha_devolucion = ?, observaciones = ?, costos_daño = ?, total_adicional = ?, id_alquiler = ? WHERE id_devolucion = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, devolucion.getFechaDevolucion());
            ps.setString(2, devolucion.getObservaciones());
            ps.setDouble(3, devolucion.getCostosDano());
            ps.setDouble(4, devolucion.getTotalAdicional());
            ps.setInt(5, devolucion.getIdAlquiler());
            ps.setInt(6, devolucion.getIdDevolucion());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una devolución
    public boolean deleteDevolucion(int idDevolucion) {
        String query = "DELETE FROM Devolucion WHERE id_devolucion = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idDevolucion);

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
