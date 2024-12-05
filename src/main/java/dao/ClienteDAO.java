package dao;

import model.Cliente;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;
    private PreparedStatement ps;

    public ClienteDAO() {
        this.connection = DBConnection.getConnection(); // Obtiene la conexión
    }

    // Crear un nuevo cliente
    public boolean createCliente(Cliente cliente) {
        String query = "INSERT INTO Cliente (id_tipoCliente, id_persona, id_empresa, fecha_registro) VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, cliente.getIdTipoCliente());
            ps.setInt(2, cliente.getIdPersona());
            ps.setInt(3, cliente.getIdEmpresa());
            ps.setString(4, cliente.getFechaRegistro());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM Cliente";
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                cliente.setIdPersona(rs.getInt("id_persona"));
                cliente.setIdEmpresa(rs.getInt("id_empresa"));
                cliente.setFechaRegistro(rs.getString("fecha_registro"));
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
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setIdTipoCliente(rs.getInt("id_tipoCliente"));
                cliente.setIdPersona(rs.getInt("id_persona"));
                cliente.setIdEmpresa(rs.getInt("id_empresa"));
                cliente.setFechaRegistro(rs.getString("fecha_registro"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // Actualizar un cliente
    public boolean updateCliente(Cliente cliente) {
        String query = "UPDATE Cliente SET id_tipoCliente = ?, id_persona = ?, id_empresa = ?, fecha_registro = ? WHERE id_cliente = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, cliente.getIdTipoCliente());
            ps.setInt(2, cliente.getIdPersona());
            ps.setInt(3, cliente.getIdEmpresa());
            ps.setString(4, cliente.getFechaRegistro());
            ps.setInt(5, cliente.getIdCliente());

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un cliente
    public boolean deleteCliente(int idCliente) {
        String query = "DELETE FROM Cliente WHERE id_cliente = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idCliente);

            return ps.executeUpdate() > 0; // Retorna true si se afecta una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
