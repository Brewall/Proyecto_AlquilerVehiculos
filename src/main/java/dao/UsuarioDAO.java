package dao;

import model.Usuario;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Crear un nuevo usuario
    public boolean createUsuario(Usuario usuario) {
        String query = "INSERT INTO Usuario (nombre_usuario, contraseña_usuario, fecha_creacion, id_empleado) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenaUsuario());
            ps.setString(3, usuario.getFechaCreacion());
            ps.setInt(4, usuario.getIdEmpleado());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarioList = new ArrayList<>();
        String query = "SELECT * FROM Usuario";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasenaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                usuario.setIdEmpleado(rs.getInt("id_empleado"));
                usuarioList.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioList;
    }

    // Obtener un usuario por su ID
    public Usuario getUsuarioById(int idUsuario) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE id_usuario = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setContrasenaUsuario(rs.getString("contraseña_usuario"));
                    usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                    usuario.setIdEmpleado(rs.getInt("id_empleado"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Actualizar un usuario
    public boolean updateUsuario(Usuario usuario) {
        String query = "UPDATE Usuario SET nombre_usuario = ?, contraseña_usuario = ?, fecha_creacion = ?, id_empleado = ? WHERE id_usuario = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenaUsuario());
            ps.setString(3, usuario.getFechaCreacion());
            ps.setInt(4, usuario.getIdEmpleado());
            ps.setInt(5, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un usuario
    public boolean deleteUsuario(int idUsuario) {
        String query = "DELETE FROM Usuario WHERE id_usuario = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validar usuario con rol
    public Usuario validarUsuarioConRol(String nombreUsuario, String contrasenaUsuario) {
        String sql = """
            SELECT u.id_usuario, u.nombre_usuario, r.rol_empleado 
            FROM Usuario u
            INNER JOIN Empleado e ON u.id_empleado = e.id_empleado
            INNER JOIN Rol r ON e.id_rol = r.id_rol
            WHERE u.nombre_usuario = ? AND u.contraseña_usuario = ?;
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    // Aquí se coloca el rol en nombreUsuario, pero quizás querías setearlo a otra propiedad
                    // Por coherencia, asumiendo que quieres retornar el rol también, se podría crear un atributo extra
                    // o usar un método setter apropiado. Aquí se utiliza setNombreUsuario nuevamente para simplificar.
                    usuario.setNombreUsuario(rs.getString("rol_empleado"));
                    return usuario;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*// Validar usuario y contraseña
    public Usuario validarUsuario(String nombreUsuario, String contrasenaUsuario) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contraseña_usuario = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                    usuario.setContrasenaUsuario(rs.getString("contraseña_usuario"));
                    usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                    usuario.setIdEmpleado(rs.getInt("id_empleado"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }*/
}
