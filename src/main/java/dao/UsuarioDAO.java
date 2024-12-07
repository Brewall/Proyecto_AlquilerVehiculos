package dao;

import model.Usuario;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;
    private PreparedStatement ps;

    public UsuarioDAO() {
        this.connection = DBConnection.getConnection(); // Obtener la conexión a la base de datos
    }

    // Crear un nuevo usuario
    public boolean createUsuario(Usuario usuario) {
        String query = "INSERT INTO Usuario (nombre_usuario, contraseña_usuario, fecha_creacion, id_empleado) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenaUsuario());
            ps.setString(3, usuario.getFechaCreacion());
            ps.setInt(4, usuario.getIdEmpleado());
            return ps.executeUpdate() > 0; // Retorna true si se inserta correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        List<Usuario> usuarioList = new ArrayList<>();
        String query = "SELECT * FROM Usuario";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
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
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasenaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                usuario.setIdEmpleado(rs.getInt("id_empleado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Actualizar un usuario
    public boolean updateUsuario(Usuario usuario) {
        String query = "UPDATE Usuario SET nombre_usuario = ?, contraseña_usuario = ?, fecha_creacion = ?, id_empleado = ? WHERE id_usuario = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContrasenaUsuario());
            ps.setString(3, usuario.getFechaCreacion());
            ps.setInt(4, usuario.getIdEmpleado());
            ps.setInt(5, usuario.getIdUsuario());
            return ps.executeUpdate() > 0; // Retorna true si se actualiza correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un usuario
    public boolean deleteUsuario(int idUsuario) {
        String query = "DELETE FROM Usuario WHERE id_usuario = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0; // Retorna true si se elimina correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validar usuario y contraseña
    public Usuario validarUsuario(String nombreUsuario, String contrasenaUsuario) {
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE nombre_usuario = ? AND contraseña_usuario = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nombreUsuario);
            ps.setString(2, contrasenaUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasenaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaCreacion(rs.getString("fecha_creacion"));
                usuario.setIdEmpleado(rs.getInt("id_empleado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario; // Devuelve null si no se encontró usuario
    }
}
