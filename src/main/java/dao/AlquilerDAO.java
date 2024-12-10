package dao;

import dto.AlquilerReporteDto;
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
            if (alquiler.getIdMovimientoVehiculo() == 0) {
                ps.setNull(6, Types.INTEGER);
            } else {
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
            if (alquiler.getIdMovimientoVehiculo() == 0) {
                ps.setNull(6, Types.INTEGER);
            } else {
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

    public List<AlquileresinfoDto> obtenerAlquileresInfo() {
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

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlquileresinfoDto dto = new AlquileresinfoDto(
                            rs.getInt("id_alquiler"),
                            rs.getString("cliente_info"),
                            rs.getString("marca"),
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

    public List<AlquilerReporteDto> obtenerReporteAlquileres() {
        List<AlquilerReporteDto> alquileres = new ArrayList<>();
        String sql = "select a.id_alquiler,\n" +
                "       a.id_cliente,\n" +
                "       a.id_vehiculo,\n" +
                "       CASE\n" +
                "           WHEN c.id_tipoCliente = 1 THEN p.nombres\n" +
                "           WHEN c.id_tipoCliente = 2 THEN e.razon_social\n" +
                "           ELSE '-'\n" +
                "       END AS nombre_cliente,\n" +
                "\n" +
                "       tp.tipo_cliente,\n" +
                "       CASE\n" +
                "           WHEN c.id_tipoCliente = 1 THEN p.dni\n" +
                "           WHEN c.id_tipoCliente = 2 THEN e.ruc\n" +
                "           ELSE '-'\n" +
                "       END AS doc_cliente,\n" +
                "        v.marca,\n" +
                "        v.modelo,\n" +
                "        v.anio_vehiculo,\n" +
                "        CAST(a.fecha_inicioReserva as DATE ) as fecha_inicio,\n" +
                "        CAST(a.fecha_finReserva as DATE )as fecha_fin\n" +
                "from alquiler a\n" +
                "         inner join cliente c on a.id_cliente = c.id_cliente\n" +
                "         inner join vehiculo v on a.id_vehiculo = v.id_vehiculo\n" +
                "         inner join tipocliente tp on c.id_tipoCliente = tp.id_tipoCliente\n" +
                "         left join persona p on c.id_persona = p.id_persona\n" +
                "         left join empresa e on c.id_empresa = e.id_empresa";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                AlquilerReporteDto alquiler = new AlquilerReporteDto();
                alquiler.setIdAlquiler(resultSet.getInt("id_alquiler"));
                alquiler.setIdCliente(resultSet.getInt("id_cliente"));
                alquiler.setIdVehiculo(resultSet.getInt("id_vehiculo"));
                alquiler.setNombreCliente(resultSet.getString("nombre_cliente"));
                alquiler.setTipoCliente(resultSet.getString("tipo_cliente"));
                alquiler.setDocCliente(resultSet.getString("doc_cliente"));
                alquiler.setMarca(resultSet.getString("marca"));
                alquiler.setModelo(resultSet.getString("modelo"));
                alquiler.setAnioVehiculo(resultSet.getInt("anio_vehiculo"));
                alquiler.setFechaInicio(resultSet.getDate("fecha_inicio").toLocalDate());
                alquiler.setFechaFin(resultSet.getDate("fecha_fin").toLocalDate());

                alquileres.add(alquiler);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alquileres;
    }


    public List<AlquilerReporteDto> obtenerReporteAlquileresFiltrado(Integer idCliente, Integer idVehiculo) {
        List<AlquilerReporteDto> alquileres = new ArrayList<>();

        // Consulta SQL con parámetros para filtrar por id_cliente o id_vehiculo
        String sql = "SELECT a.id_alquiler, " +
                "       a.id_cliente, " +
                "       a.id_vehiculo, " +
                "       CASE " +
                "           WHEN c.id_tipoCliente = 1 THEN p.nombres " +
                "           WHEN c.id_tipoCliente = 2 THEN e.razon_social " +
                "           ELSE '-' " +
                "       END AS nombre_cliente, " +
                "       tp.tipo_cliente, " +
                "       CASE " +
                "           WHEN c.id_tipoCliente = 1 THEN p.dni " +
                "           WHEN c.id_tipoCliente = 2 THEN e.ruc " +
                "           ELSE '-' " +
                "       END AS doc_cliente, " +
                "       v.marca, " +
                "       v.modelo, " +
                "       v.anio_vehiculo, " +
                "       CAST(a.fecha_inicioReserva AS DATE) AS fecha_inicio, " +
                "       CAST(a.fecha_finReserva AS DATE) AS fecha_fin " +
                "FROM alquiler a " +
                "INNER JOIN cliente c ON a.id_cliente = c.id_cliente " +
                "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
                "INNER JOIN tipocliente tp ON c.id_tipoCliente = tp.id_tipoCliente " +
                "LEFT JOIN persona p ON c.id_persona = p.id_persona " +
                "LEFT JOIN empresa e ON c.id_empresa = e.id_empresa " +
                "WHERE (a.id_vehiculo = ? OR a.id_cliente = ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, idVehiculo);
            statement.setObject(2, idCliente);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AlquilerReporteDto alquiler = new AlquilerReporteDto();
                    alquiler.setIdAlquiler(resultSet.getInt("id_alquiler"));
                    alquiler.setIdCliente(resultSet.getInt("id_cliente"));
                    alquiler.setIdVehiculo(resultSet.getInt("id_vehiculo"));
                    alquiler.setNombreCliente(resultSet.getString("nombre_cliente"));
                    alquiler.setTipoCliente(resultSet.getString("tipo_cliente"));
                    alquiler.setDocCliente(resultSet.getString("doc_cliente"));
                    alquiler.setMarca(resultSet.getString("marca"));
                    alquiler.setModelo(resultSet.getString("modelo"));
                    alquiler.setAnioVehiculo(resultSet.getInt("anio_vehiculo"));
                    alquiler.setFechaInicio(resultSet.getDate("fecha_inicio").toLocalDate());
                    alquiler.setFechaFin(resultSet.getDate("fecha_fin").toLocalDate());

                    alquileres.add(alquiler);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alquileres;
    }

}
