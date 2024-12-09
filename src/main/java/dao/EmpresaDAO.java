package dao;

import model.Empresa;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    // Crear una nueva empresa
    public int createEmpresa(Empresa empresa) {
        String query = "INSERT INTO Empresa (razon_social, ruc, direccion) VALUES (?, ?, ?)";
        int idGenerado = -1; // Valor por defecto si no se genera ID

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getDireccion());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idGenerado;
    }


    // Obtener todas las empresas
    public List<Empresa> getAllEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        String query = "SELECT * FROM Empresa";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setRazonSocial(rs.getString("razon_social"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setDireccion(rs.getString("direccion"));
                empresas.add(empresa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empresas;
    }

    // Obtener una empresa por su ID
    public Empresa getEmpresaById(int idEmpresa) {
        Empresa empresa = null;
        String query = "SELECT * FROM Empresa WHERE id_empresa = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    empresa = new Empresa();
                    empresa.setIdEmpresa(rs.getInt("id_empresa"));
                    empresa.setRazonSocial(rs.getString("razon_social"));
                    empresa.setRuc(rs.getString("ruc"));
                    empresa.setDireccion(rs.getString("direccion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empresa;
    }

    // Actualizar una empresa
    public boolean updateEmpresa(Empresa empresa) {
        String query = "UPDATE Empresa SET razon_social = ?, ruc = ?, direccion = ? WHERE id_empresa = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getDireccion());
            ps.setInt(4, empresa.getIdEmpresa());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar una empresa
    public boolean deleteEmpresa(int idEmpresa) {
        String query = "DELETE FROM Empresa WHERE id_empresa = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, idEmpresa);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
