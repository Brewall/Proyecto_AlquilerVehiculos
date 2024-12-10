package dto;

public class AlquileresinfoDto {
    private int idAlquiler;
    private String clienteInfo;
    private String marcaVehiculo;
    private String modeloVehiculo;
    private String placaVehiculo;
    private String fechaInicioReserva;
    private String fechaFinReserva;
    private double totalPrecio;

    public AlquileresinfoDto(){

    }

    public AlquileresinfoDto(int idAlquiler, String clienteInfo, String marcaVehiculo, String modeloVehiculo, String placaVehiculo, String fechaInicioReserva, String fechaFinReserva, double totalPrecio) {
        this.idAlquiler = idAlquiler;
        this.clienteInfo = clienteInfo;
        this.marcaVehiculo = marcaVehiculo;
        this.modeloVehiculo = modeloVehiculo;
        this.placaVehiculo = placaVehiculo;
        this.fechaInicioReserva = fechaInicioReserva;
        this.fechaFinReserva = fechaFinReserva;
        this.totalPrecio = totalPrecio;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getClienteInfo() {
        return clienteInfo;
    }

    public void setClienteInfo(String clienteInfo) {
        this.clienteInfo = clienteInfo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
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

    public double getTotalPrecio() {
        return totalPrecio;
    }

    public void setTotalPrecio(double totalPrecio) {
        this.totalPrecio = totalPrecio;
    }

    @Override
    public String toString() {
        return "Alquiler: " + idAlquiler +
                " " + clienteInfo +
                " Vehiculo: " + marcaVehiculo +
                " - " + modeloVehiculo +
                " - " + placaVehiculo;

    }
}
