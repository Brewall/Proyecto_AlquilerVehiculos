package model;

public class TipoCliente {
    private int idTipoCliente;
    private String tipoCliente;

    public static final TipoCliente PERSONA = new TipoCliente(1,"Persona");
    public static final TipoCliente EMPRESA = new TipoCliente(2,"Empresa");

    public TipoCliente() {
    }

    public TipoCliente(int idTipoCliente, String tipoCliente) {
        this.idTipoCliente = idTipoCliente;
        this.tipoCliente = tipoCliente;
    }

    public int getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(int idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    @Override
    public String toString() {
        return "TipoCliente{" +
                "idTipoCliente=" + idTipoCliente +
                ", tipoCliente='" + tipoCliente + '\'' +
                '}';
    }
}
