package dao;

import model.Servicios;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiciosDAO {

    // Crear un nuevo servicio
    public boolean createServicio(Servicios servicio) {
        String query = "INSERT INTO Servicios (nombre_servicio, descripcion, precio_servicio, id_unidadMedida) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecioServicio());
            ps.setInt(4, servicio.getIdUnidadMedida());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los servicios
    public List<Servicios> getAllServicios() {
        List<Servicios> serviciosList = new ArrayList<>();
        String query = "SELECT * FROM Servicios";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicios servicio = new Servicios();
                servicio.setIdServicios(rs.getInt("id_servicios"));
                servicio.setNombreServicio(rs.getString("nombre_servicio"));
                servicio.setDescripcion(rs.getString("descripcion"));
                servicio.setPrecioServicio(rs.getDouble("precio_servicio"));
                servicio.setIdUnidadMedida(rs.getInt("id_unidadMedida"));
                serviciosList.add(servicio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serviciosList;
    }

    // Obtener un servicio por su ID
    public Servicios getServicioById(int idServicios) {
        Servicios servicio = null;
        String query = "SELECT * FROM Servicios WHERE id_servicios = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idServicios);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    servicio = new Servicios();
                    servicio.setIdServicios(rs.getInt("id_servicios"));
                    servicio.setNombreServicio(rs.getString("nombre_servicio"));
                    servicio.setDescripcion(rs.getString("descripcion"));
                    servicio.setPrecioServicio(rs.getDouble("precio_servicio"));
                    servicio.setIdUnidadMedida(rs.getInt("id_unidadMedida"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return servicio;
    }

    // Actualizar un servicio
    public boolean updateServicio(Servicios servicio) {
        String query = "UPDATE Servicios SET nombre_servicio = ?, descripcion = ?, precio_servicio = ?, id_unidadMedida = ? WHERE id_servicios = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecioServicio());
            ps.setInt(4, servicio.getIdUnidadMedida());
            ps.setInt(5, servicio.getIdServicios());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un servicio
    public boolean deleteServicio(int idServicios) {
        String query = "DELETE FROM Servicios WHERE id_servicios = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idServicios);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
