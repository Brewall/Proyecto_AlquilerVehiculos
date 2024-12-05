package dao;

import model.Alquiler;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDAO {
    private Connection connection;
    private PreparedStatement ps;

    public AlquilerDAO() {
        this.connection = DBConnection.getConnection(); // Obtiene la conexión
    }

    // Crear un nuevo alquiler
    public boolean createAlquiler(Alquiler alquiler) {
        String query = "INSERT INTO Alquiler (id_cliente, id_vehiculo, id_usuario, id_movimientoVehiculo, total_precio) VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, alquiler.getIdCliente());
            ps.setInt(2, alquiler.getIdVehiculo());
            ps.setInt(3, alquiler.getIdUsuario());
            ps.setInt(4, alquiler.getIdMovimientoVehiculo());
            ps.setDouble(5, alquiler.getTotalPrecio());

            return ps.executeUpdate() > 0; // Retorna true si se afectó una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los alquileres
    public List<Alquiler> getAllAlquileres() {
        List<Alquiler> alquileres = new ArrayList<>();
        String query = "SELECT * FROM Alquiler";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alquiler alquiler = new Alquiler();
                alquiler.setIdAlquiler(rs.getInt("id_alquiler"));
                alquiler.setIdCliente(rs.getInt("id_cliente"));
                alquiler.setIdVehiculo(rs.getInt("id_vehiculo"));
                alquiler.setIdUsuario(rs.getInt("id_usuario"));
                alquiler.setIdMovimientoVehiculo(rs.getInt("id_movimientoVehiculo"));
                alquiler.setTotalPrecio(rs.getDouble("total_precio"));
                alquileres.add(alquiler);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquileres;
    }

    // Obtener un alquiler por su ID
    public Alquiler getAlquilerById(int idAlquiler) {
        Alquiler alquiler = null;
        String query = "SELECT * FROM Alquiler WHERE id_alquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idAlquiler);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alquiler = new Alquiler();
                alquiler.setIdAlquiler(rs.getInt("id_alquiler"));
                alquiler.setIdCliente(rs.getInt("id_cliente"));
                alquiler.setIdVehiculo(rs.getInt("id_vehiculo"));
                alquiler.setIdUsuario(rs.getInt("id_usuario"));
                alquiler.setIdMovimientoVehiculo(rs.getInt("id_movimientoVehiculo"));
                alquiler.setTotalPrecio(rs.getDouble("total_precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquiler;
    }

    // Actualizar un alquiler
    public boolean updateAlquiler(Alquiler alquiler) {
        String query = "UPDATE Alquiler SET id_cliente = ?, id_vehiculo = ?, id_usuario = ?, id_movimientoVehiculo = ?, total_precio = ? WHERE id_alquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, alquiler.getIdCliente());
            ps.setInt(2, alquiler.getIdVehiculo());
            ps.setInt(3, alquiler.getIdUsuario());
            ps.setInt(4, alquiler.getIdMovimientoVehiculo());
            ps.setDouble(5, alquiler.getTotalPrecio());
            ps.setInt(6, alquiler.getIdAlquiler());

            return ps.executeUpdate() > 0; // Retorna true si se afectó una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un alquiler
    public boolean deleteAlquiler(int idAlquiler) {
        String query = "DELETE FROM Alquiler WHERE id_alquiler = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idAlquiler);
            return ps.executeUpdate() > 0; // Retorna true si se afectó una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
