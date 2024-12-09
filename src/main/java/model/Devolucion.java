package model;

public class Devolucion {
    private int idDevolucion;
    private String fechaDevolucion;
    private String observaciones;
    private double costosDano;
    private int idAlquiler;

    public Devolucion() {
    }

    public double getCostosDano() {
        return costosDano;
    }

    public void setCostosDano(double costosDano) {
        this.costosDano = costosDano;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public int getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(int idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


}
