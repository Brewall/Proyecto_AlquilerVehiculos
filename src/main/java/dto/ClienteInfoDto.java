package dto;

public class ClienteInfoDto {
    private int idCliente;
    private int idTipoCliente;
    private String tipoCliente;
    private String clienteInfo;

    // Constructor vacío
    public ClienteInfoDto() {
    }

    // Constructor con parámetros
    public ClienteInfoDto(int idCliente, int idTipoCliente, String tipoCliente, String clienteInfo) {
        this.idCliente = idCliente;
        this.idTipoCliente = idTipoCliente;
        this.tipoCliente = tipoCliente;
        this.clienteInfo = clienteInfo;
    }

    // Getters y Setters
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

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getClienteInfo() {
        return clienteInfo;
    }

    public void setClienteInfo(String clienteInfo) {
        this.clienteInfo = clienteInfo;
    }

    // Método toString() para depuración
    @Override
    public String toString() {
        return "Cliente: " + idCliente + " Tipo: " + tipoCliente + " " + clienteInfo;
    }
}
