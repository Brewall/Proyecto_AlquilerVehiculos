package dao;

import model.TipoVehiculo;
import util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoVehiculoDAO {
    private Connection connection;
    private PreparedStatement ps;

    public TipoVehiculoDAO(){
        this.connection = DBConnection.getConnection();
    }

    // Crear un nuevo tipo vehiculo
    public boolean createTipoVehiculo(TipoVehiculo tipoVehiculo){
        String query = "INSERT INTO TipoVehiculo (tipo_vehiculo) VALUES (?)";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, tipoVehiculo.getTipoVehiculo());

            return ps.executeUpdate()>0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los tipos vehiculo
    public List<TipoVehiculo> getAllTipoVehiculo(){
        List<TipoVehiculo> tipoVehiculos = new ArrayList<>();
        String query = "";

        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                TipoVehiculo tipoVehiculo = new TipoVehiculo();
                tipoVehiculo.setIdTipoVehiculo(rs.getInt("id_tipoVehiculo"));
                tipoVehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
                tipoVehiculos.add(tipoVehiculo);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tipoVehiculos;
    }

    // Obtener un tipo de vehiculo por ID
    public TipoVehiculo getTipoVehiculoById(int idTipoVehiculo){
        TipoVehiculo tipoVehiculo = null;
        String query = "SELECT * FROM TipoVehiculo WHERE id_tipoVehiculo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1,idTipoVehiculo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                tipoVehiculo = new TipoVehiculo();
                tipoVehiculo.setIdTipoVehiculo(rs.getInt("Id_tipoVehiculo"));
                tipoVehiculo.setTipoVehiculo(rs.getString("tipo_vehiculo"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tipoVehiculo;
    }


    // Actualizar un tipo Vehiculo
    public boolean updateTipoVehiculo(TipoVehiculo tipoVehiculo){
        String query ="";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1,tipoVehiculo.getTipoVehiculo());
            ps.setInt(2, tipoVehiculo.getIdTipoVehiculo());

            return ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un tipoVehiculo
    public boolean deleteTipoVehiculo(int idTipoVehiculo){
        String query = "DELETE FROM TipoVehiculo WHERE id_tipoVehiculo = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, idTipoVehiculo);

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
