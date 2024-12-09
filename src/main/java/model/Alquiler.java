package model;

public class Alquiler {
    private int idAlquiler;
    private int idCliente;
    private int idVehiculo;
    private int idUsuario;
    private String fechaInicioReserva;
    private String fechaFinReserva;
    private int idMovimientoVehiculo;
    private double totalPrecio;

    public Alquiler() {
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdMovimientoVehiculo() {
        return idMovimientoVehiculo;
    }

    public void setIdMovimientoVehiculo(int idMovimientoVehiculo) {
        this.idMovimientoVehiculo = idMovimientoVehiculo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    public String getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(String fechaInicioReserva) {
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public String getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(String fechaFinReserva) {
        this.fechaFinReserva = fechaFinReserva;
    }

    @Override
    public String toString() {
        return "Alquiler{" +
                "idAlquiler=" + idAlquiler +
                ", idCliente=" + idCliente +
                ", idVehiculo=" + idVehiculo +
                ", idUsuario=" + idUsuario +
                ", fechaInicioReserva='" + fechaInicioReserva + '\'' +
                ", fechaFinReserva='" + fechaFinReserva + '\'' +
                ", idMovimientoVehiculo=" + idMovimientoVehiculo +
                ", totalPrecio=" + totalPrecio +
                '}';
    }
}
