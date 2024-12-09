package model;

public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;
    private String anioVehiculo;
    private String placa;
    private double precioDia;
    private boolean disponible;
    private int idTipoVehiculo;

    public Vehiculo() {
    }

    public Vehiculo(int idVehiculo, String marca, String modelo, String anioVehiculo, String placa, double precioDia, boolean disponible, int idTipoVehiculo) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.anioVehiculo = anioVehiculo;
        this.placa = placa;
        this.precioDia = precioDia;
        this.disponible = disponible;
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() { return modelo; }

    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getAnioVehiculo() {
        return anioVehiculo;
    }

    public void setAnioVehiculo(String anioVehiculo) {
        this.anioVehiculo = anioVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(double precioDia) {
        this.precioDia = precioDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(int idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    @Override
    public String toString() {
        return "Vehiculo: " + idVehiculo +
                " Marca: " + marca + ' ' +
                " Modelo: " + modelo;
    }
}
