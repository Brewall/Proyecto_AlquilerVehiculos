package dao;

import model.Rol;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {

    // Crear un nuevo rol
    public boolean createRol(Rol rol) {
        String query = "INSERT INTO Rol (rol_empleado) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, rol.getRolEmpleado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los roles
    public List<Rol> getAllRoles() {
        List<Rol> roles = new ArrayList<>();
        String query = "SELECT * FROM Rol";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol();
                rol.setIdRol(rs.getInt("id_rol"));
                rol.setRolEmpleado(rs.getString("rol_empleado"));
                roles.add(rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Obtener un rol por su ID
    public Rol getRolById(int idRol) {
        Rol rol = null;
        String query = "SELECT * FROM Rol WHERE id_rol = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idRol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol();
                    rol.setIdRol(rs.getInt("id_rol"));
                    rol.setRolEmpleado(rs.getString("rol_empleado"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }

    // Actualizar un rol
    public boolean updateRol(Rol rol) {
        String query = "UPDATE Rol SET rol_empleado = ? WHERE id_rol = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, rol.getRolEmpleado());
            ps.setInt(2, rol.getIdRol());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un rol
    public boolean deleteRol(int idRol) {
        String query = "DELETE FROM Rol WHERE id_rol = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idRol);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
