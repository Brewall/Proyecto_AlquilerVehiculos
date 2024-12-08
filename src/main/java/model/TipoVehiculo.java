package model;

public class TipoVehiculo {
    private int idTipoVehiculo;
    private String tipoVehiculo;

    public TipoVehiculo() {
    }

    // Constructor
    public TipoVehiculo(int idTipoVehiculo, String tipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(int idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    // Método toString para mostrar en el ComboBox
    @Override
    public String toString() {
        return tipoVehiculo; // Se mostrará el nombre del tipo en el ComboBox
    }

}
