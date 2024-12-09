package dao;

import model.Empleado;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // Crear un nuevo empleado
    public boolean createEmpleado(Empleado empleado) {
        String query = "INSERT INTO Empleado (fecha_contratacion, id_persona, id_rol) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, empleado.getFechaContratacion());
            ps.setInt(2, empleado.getIdPersona());
            ps.setInt(3, empleado.getIdRol());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los empleados
    public List<Empleado> getAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM Empleado";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(rs.getInt("id_empleado"));
                empleado.setFechaContratacion(rs.getString("fecha_contratacion"));
                empleado.setIdPersona(rs.getInt("id_persona"));
                empleado.setIdRol(rs.getInt("id_rol"));
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    // Obtener un empleado por su ID
    public Empleado getEmpleadoById(int idEmpleado) {
        Empleado empleado = null;
        String query = "SELECT * FROM Empleado WHERE id_empleado = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEmpleado);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    empleado = new Empleado();
                    empleado.setIdEmpleado(rs.getInt("id_empleado"));
                    empleado.setFechaContratacion(rs.getString("fecha_contratacion"));
                    empleado.setIdPersona(rs.getInt("id_persona"));
                    empleado.setIdRol(rs.getInt("id_rol"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleado;
    }

    // Actualizar un empleado
    public boolean updateEmpleado(Empleado empleado) {
        String query = "UPDATE Empleado SET fecha_contratacion = ?, id_persona = ?, id_rol = ? WHERE id_empleado = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, empleado.getFechaContratacion());
            ps.setInt(2, empleado.getIdPersona());
            ps.setInt(3, empleado.getIdRol());
            ps.setInt(4, empleado.getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un empleado
    public boolean deleteEmpleado(int idEmpleado) {
        String query = "DELETE FROM Empleado WHERE id_empleado = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
