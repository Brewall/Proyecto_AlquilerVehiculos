package dto;

public class ClienteEmpresaDto {
    private int idCliente;
    private int idEmpresa;
    private String razonSocial;
    private String ruc;
    private String direccion;

    public ClienteEmpresaDto() {
    }

    public ClienteEmpresaDto(int idCliente, int idEmpresa, String razonSocial, String ruc, String direccion) {
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.razonSocial = razonSocial;
        this.ruc = ruc;
        this.direccion = direccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
