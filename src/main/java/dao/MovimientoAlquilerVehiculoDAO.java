package dao;

import model.MovimientoAlquilerVehiculo;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoAlquilerVehiculoDAO {

    // Crear un nuevo movimiento de alquiler de vehículo
    public boolean createMovimiento(MovimientoAlquilerVehiculo movimiento) {
        String query = "INSERT INTO MovimientoAlquilerVehiculo (fecha_salida, fecha_ingreso, id_tipoMovimiento) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, movimiento.getFechaSalida());
            ps.setString(2, movimiento.getFechaIngreso());
            ps.setInt(3, movimiento.getIdTipoMovimiento());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los movimientos
    public List<MovimientoAlquilerVehiculo> getAllMovimientos() {
        List<MovimientoAlquilerVehiculo> movimientos = new ArrayList<>();
        String query = "SELECT * FROM MovimientoAlquilerVehiculo";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MovimientoAlquilerVehiculo movimiento = new MovimientoAlquilerVehiculo();
                movimiento.setIdMovimientoAlquilerVehiculo(rs.getInt("id_movimientoAlquilerVehiculo"));
                movimiento.setFechaSalida(rs.getString("fecha_salida"));
                movimiento.setFechaIngreso(rs.getString("fecha_ingreso"));
                movimiento.setIdTipoMovimiento(rs.getInt("id_tipoMovimiento"));
                movimientos.add(movimiento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimientos;
    }

    // Obtener un movimiento por su ID
    public MovimientoAlquilerVehiculo getMovimientoById(int idMovimiento) {
        MovimientoAlquilerVehiculo movimiento = null;
        String query = "SELECT * FROM MovimientoAlquilerVehiculo WHERE id_movimientoAlquilerVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idMovimiento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    movimiento = new MovimientoAlquilerVehiculo();
                    movimiento.setIdMovimientoAlquilerVehiculo(rs.getInt("id_movimientoAlquilerVehiculo"));
                    movimiento.setFechaSalida(rs.getString("fecha_salida"));
                    movimiento.setFechaIngreso(rs.getString("fecha_ingreso"));
                    movimiento.setIdTipoMovimiento(rs.getInt("id_tipoMovimiento"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movimiento;
    }

    // Actualizar un movimiento
    public boolean updateMovimiento(MovimientoAlquilerVehiculo movimiento) {
        String query = "UPDATE MovimientoAlquilerVehiculo SET fecha_salida = ?, fecha_ingreso = ?, id_tipoMovimiento = ? WHERE id_movimientoAlquilerVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, movimiento.getFechaSalida());
            ps.setString(2, movimiento.getFechaIngreso());
            ps.setInt(3, movimiento.getIdTipoMovimiento());
            ps.setInt(4, movimiento.getIdMovimientoAlquilerVehiculo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un movimiento
    public boolean deleteMovimiento(int idMovimiento) {
        String query = "DELETE FROM MovimientoAlquilerVehiculo WHERE id_movimientoAlquilerVehiculo = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idMovimiento);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
