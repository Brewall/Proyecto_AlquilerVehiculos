package dao;

import model.Empresa;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {
    private Connection connection;
    private PreparedStatement ps;

    public EmpresaDAO(){
        this.connection = DBConnection.getConnection();
    }

    //CREATE EMP
    public boolean createEmpresa(Empresa empresa){
        String query = "INSERT INTO Empresa(razon_social, ruc, direccion) VALUES (?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getDireccion());

            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //leer - imprimir
    public List<Empresa> getAllEmpresas(){
        List<Empresa> empresas = new ArrayList<>();
        String query ="SELECT * FROM Empresa";
        try {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()){
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setRazonSocial(rs.getString("razon_social"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setDireccion(rs.getString("direccion"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return empresas;
    }

    //Buscar
    public Empresa getEmpresaById(int idEmpresa){
        Empresa empresa = null;
        String query = "SELECT * FROM Empresa WHERE id_empresa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1,idEmpresa);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                empresa = new Empresa();
                empresa.setIdEmpresa(rs.getInt("id_empresa"));
                empresa.setRazonSocial(rs.getString("razon_social"));
                empresa.setRuc(rs.getString("ruc"));
                empresa.setDireccion(rs.getString("direccion"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return empresa;
    }

    //actualizar
    public boolean updateEmpresa(Empresa empresa){
        String query = "UPDATE Empresa SET razon_social = ?, ruc = ?, direccion = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, empresa.getRazonSocial());
            ps.setString(2, empresa.getRuc());
            ps.setString(3, empresa.getDireccion());

            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //eliminar
    public boolean deleteEmpresa(int idEmpresa){
        String query = "DELETE FROM Empresa WHERE id_empresa = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idEmpresa);
            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
