package model;

import java.sql.Timestamp;
import java.util.Date;

public class Cliente {
    private int idCliente;
    private int idTipoCliente;
    private int idPersona;
    private int idEmpresa;
    private Timestamp fechaRegistro;

    public Cliente() {
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(int idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }


    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", idTipoCliente=" + idTipoCliente +
                ", idPersona=" + idPersona +
                ", idEmpresa=" + idEmpresa +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
