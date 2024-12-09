package dao;

import dto.ClienteEmpresaDto;
import dto.ClienteInfoDto;
import dto.ClientePersonaDto;
import model.Cliente;
import model.TipoCliente;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Crear un nuevo cliente
    public boolean createCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (id_tipoCliente, id_persona, id_empresa, fecha_registro) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, cliente.getIdTipoCliente());

            // id_persona
            if (cliente.getIdPersona() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, cliente.getIdPersona());
            }

            // id_empresa
            if (cliente.getIdEmpresa() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, cliente.getIdEmpresa());
            }

            ps.setTimestamp(4, cliente.getFechaRegistro());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Cliente";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                cliente.setIdPersona(rs.getInt("id_persona"));
                cliente.setIdEmpresa(rs.getInt("id_empresa"));
                cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // Obtener un cliente por su ID
    public Cliente getClienteById(int idCliente) {
        Cliente cliente = null;
        String query = "SELECT * FROM Cliente WHERE id_cliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                    cliente.setIdPersona(rs.getInt("id_persona"));
                    cliente.setIdEmpresa(rs.getInt("id_empresa"));
                    cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // Actualizar un cliente
    public boolean updateCliente(Cliente cliente) {
        String query = "UPDATE Cliente SET id_tipoCliente = ?, id_persona = ?, id_empresa = ?, fecha_registro = ? WHERE id_cliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, cliente.getIdTipoCliente());

            // id_persona
            if (cliente.getIdPersona() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, cliente.getIdPersona());
            }

            // id_empresa
            if (cliente.getIdEmpresa() == 0) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, cliente.getIdEmpresa());
            }

            ps.setTimestamp(4, cliente.getFechaRegistro());
            ps.setInt(5, cliente.getIdCliente());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un cliente
    public boolean deleteCliente(int idCliente) {
        String query = "DELETE FROM Cliente WHERE id_cliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Consultas personalizadas: obtener clientes tipo persona
    public List<ClientePersonaDto> obtenerClientesTipoPersona() {
        List<ClientePersonaDto> clientes = new ArrayList<>();
        String sql = "SELECT c.id_cliente, c.id_persona, p.nombres, p.apellido_paterno, p.apellido_materno, p.dni, " +
                "p.direccion, p.telefono, p.correo, p.genero, p.fecha_nacimiento " +
                "FROM cliente c " +
                "INNER JOIN persona p ON c.id_persona = p.id_persona " +
                "WHERE c.id_tipoCliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, TipoCliente.PERSONA.getIdTipoCliente());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClientePersonaDto cliente = new ClientePersonaDto(
                            rs.getInt("id_cliente"),
                            rs.getInt("id_persona"),
                            rs.getString("nombres"),
                            rs.getString("apellido_paterno"),
                            rs.getString("apellido_materno"),
                            rs.getString("dni"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("correo"),
                            rs.getString("genero"),
                            rs.getString("fecha_nacimiento")
                    );
                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }


    public List<ClienteEmpresaDto> obtenerClientesTipoEmpresa() {
        List<ClienteEmpresaDto> lista = new ArrayList<>();

        String sql = "SELECT c.id_cliente, c.id_empresa, e.razon_social, e.ruc, e.direccion\n" +
                "            FROM cliente c\n" +
                "            INNER JOIN empresa e ON c.id_empresa = e.id_empresa\n" +
                "            WHERE c.id_tipoCliente = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, TipoCliente.EMPRESA.getIdTipoCliente());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClienteEmpresaDto dto = new ClienteEmpresaDto();
                    dto.setIdCliente(rs.getInt("id_cliente"));
                    dto.setIdEmpresa(rs.getInt("id_empresa"));
                    dto.setRazonSocial(rs.getString("razon_social"));
                    dto.setRuc(rs.getString("ruc"));
                    dto.setDireccion(rs.getString("direccion"));

                    lista.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<ClienteInfoDto> getClienteInfo() {
        List<ClienteInfoDto> clienteInfoList = new ArrayList<>();
        String sql = """
                SELECT
                    c.id_cliente,
                    c.id_tipoCliente,
                    tp.tipo_cliente,
                    CASE
                        WHEN c.id_tipoCliente = 1 THEN CONCAT(' DNI: ', p.dni, ' Nombre: ', p.nombres)
                        WHEN c.id_tipoCliente = 2 THEN CONCAT(' RUC: ', e.ruc, ' Nombre: ', e.razon_social)
                        ELSE '-'
                    END AS cliente_info
                FROM
                    cliente c
                INNER JOIN
                    tipocliente tp ON c.id_tipoCliente = tp.id_tipoCliente
                LEFT JOIN
                    persona p ON c.id_persona = p.id_persona
                LEFT JOIN
                    empresa e ON c.id_empresa = e.id_empresa
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClienteInfoDto dto = new ClienteInfoDto(
                            rs.getInt("id_cliente"),
                            rs.getInt("id_tipoCliente"),
                            rs.getString("tipo_cliente"),
                            rs.getString("cliente_info")
                    );
                    clienteInfoList.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clienteInfoList;
    }
}
