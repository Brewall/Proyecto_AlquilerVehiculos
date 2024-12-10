package dto;

import java.time.LocalDate;

public class AlquilerReporteDto {
    private int idAlquiler;
    private int idCliente;
    private int idVehiculo;
    private String nombreCliente;
    private String tipoCliente;
    private String docCliente;
    private String marca;
    private String modelo;
    private int anioVehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public AlquilerReporteDto() {
    }

    public AlquilerReporteDto(int idAlquiler, int idCliente, int idVehiculo, String nombreCliente, String tipoCliente, String docCliente, String marca, String modelo, int anioVehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idAlquiler = idAlquiler;
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.nombreCliente = nombreCliente;
        this.tipoCliente = tipoCliente;
        this.docCliente = docCliente;
        this.marca = marca;
        this.modelo = modelo;
        this.anioVehiculo = anioVehiculo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDocCliente() {
        return docCliente;
    }

    public void setDocCliente(String docCliente) {
        this.docCliente = docCliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioVehiculo() {
        return anioVehiculo;
    }

    public void setAnioVehiculo(int anioVehiculo) {
        this.anioVehiculo = anioVehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
