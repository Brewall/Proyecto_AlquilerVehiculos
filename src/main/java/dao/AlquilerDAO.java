package dao;

import dto.AlquileresinfoDto;
import dto.ClientePersonaDto;
import model.Alquiler;
import model.TipoCliente;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlquilerDAO {

    // Crear un nuevo alquiler
    public boolean createAlquiler(Alquiler alquiler) {
        System.out.println(alquiler);

        String query = "INSERT INTO Alquiler (id_cliente, id_vehiculo, id_usuario, fecha_inicioReserva, fecha_finReserva, id_movimientoVehiculo, total_precio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, alquiler.getIdCliente());
            ps.setInt(2, alquiler.getIdVehiculo());
            ps.setInt(3, alquiler.getIdUsuario());
            ps.setString(4, alquiler.getFechaInicioReserva());
            ps.setString(5, alquiler.getFechaFinReserva());
            if(alquiler.getIdMovimientoVehiculo() == 0){
                ps.setNull(6,Types.INTEGER);
            }else {
                ps.setInt(6, alquiler.getIdMovimientoVehiculo());
            }

            ps.setDouble(7, alquiler.getTotalPrecio());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los alquileres
    public List<Alquiler> getAllAlquileres() {
        List<Alquiler> alquileres = new ArrayList<>();
        String query = "SELECT * FROM Alquiler";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alquiler alquiler = new Alquiler();
                alquiler.setIdAlquiler(rs.getInt("id_alquiler"));
                alquiler.setIdCliente(rs.getInt("id_cliente"));
                alquiler.setIdVehiculo(rs.getInt("id_vehiculo"));
                alquiler.setIdUsuario(rs.getInt("id_usuario"));
                alquiler.setFechaInicioReserva(rs.getString("fecha_inicioReserva"));
                alquiler.setFechaFinReserva(rs.getString("fecha_finReserva"));
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

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idAlquiler);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    alquiler = new Alquiler();
                    alquiler.setIdAlquiler(rs.getInt("id_alquiler"));
                    alquiler.setIdCliente(rs.getInt("id_cliente"));
                    alquiler.setIdVehiculo(rs.getInt("id_vehiculo"));
                    alquiler.setIdUsuario(rs.getInt("id_usuario"));
                    alquiler.setIdMovimientoVehiculo(rs.getInt("id_movimientoVehiculo"));
                    alquiler.setTotalPrecio(rs.getDouble("total_precio"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquiler;
    }

    // Actualizar un alquiler
    public boolean updateAlquiler(Alquiler alquiler) {
        String query = "UPDATE Alquiler SET id_cliente = ?, id_vehiculo = ?, id_usuario = ?,fecha_inicioReserva = ?, fecha_finReserva = ? , id_movimientoVehiculo = ?, total_precio = ? WHERE id_alquiler = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, alquiler.getIdCliente());
            ps.setInt(2, alquiler.getIdVehiculo());
            ps.setInt(3, alquiler.getIdUsuario());
            ps.setString(4, alquiler.getFechaInicioReserva());
            ps.setString(5, alquiler.getFechaFinReserva());
            if(alquiler.getIdMovimientoVehiculo() == 0){
                ps.setNull(6,Types.INTEGER);
            }else {
                ps.setInt(6, alquiler.getIdMovimientoVehiculo());
            }
            ps.setDouble(7, alquiler.getTotalPrecio());
            ps.setInt(8, alquiler.getIdAlquiler());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un alquiler
    public boolean deleteAlquiler(int idAlquiler) {
        String query = "DELETE FROM Alquiler WHERE id_alquiler = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idAlquiler);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AlquileresinfoDto> obtenerAlquileres(){
        List<AlquileresinfoDto> alquileresinfoDtos = new ArrayList<>();
        String sql = "SELECT\n" +
                "    a.id_alquiler,\n" +
                "    CASE\n" +
                "        WHEN c.id_persona IS NOT NULL THEN\n" +
                "            CONCAT('Cliente: ', p.nombres, ' ', p.apellido_paterno, ' - DNI: ', p.dni)\n" +
                "        WHEN c.id_empresa IS NOT NULL THEN\n" +
                "            CONCAT('Empresa: ', e.razon_social, ' - RUC: ', e.ruc)\n" +
                "        ELSE\n" +
                "            'Cliente desconocido'\n" +
                "    END AS cliente_info,\n" +
                "    v.marca, v.modelo, v.placa,\n" +
                "    a.fecha_inicioReserva,\n" +
                "    a.fecha_finReserva,\n" +
                "    a.total_precio\n" +
                "FROM\n" +
                "    Alquiler a\n" +
                "INNER JOIN\n" +
                "    Cliente c ON a.id_cliente = c.id_cliente\n" +
                "LEFT JOIN\n" +
                "    Persona p ON c.id_persona = p.id_persona\n" +
                "LEFT JOIN\n" +
                "    Empresa e ON c.id_empresa = e.id_empresa\n" +
                "INNER JOIN\n" +
                "    Vehiculo v ON a.id_vehiculo = v.id_vehiculo;";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, TipoCliente.PERSONA.getIdTipoCliente());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlquileresinfoDto dto = new AlquileresinfoDto(
                            rs.getInt("id_alquiler"),
                            rs.getString("cliente_info"),
                            rs.getString("narca"),
                            rs.getString("modelo"),
                            rs.getString("placa"),
                            rs.getString("fecha_inicioReserva"),
                            rs.getString("fecha_finReserva"),
                            rs.getDouble("total_precio")
                    );
                    alquileresinfoDtos.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alquileresinfoDtos;
    }
}
