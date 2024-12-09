package dto;

public class EmpleadosInfoDto {
    private int idEmpleado;
    private String empleadoInfo;
    private int idUsuario;

    public EmpleadosInfoDto() {
    }

    public EmpleadosInfoDto(int idEmpleado, String empleadoInfo, int idUsuario) {
        this.idEmpleado = idEmpleado;
        this.empleadoInfo = empleadoInfo;
        this.idUsuario = idUsuario;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEmpleadoInfo() {
        return empleadoInfo;
    }

    public void setEmpleadoInfo(String empleadoInfo) {
        this.empleadoInfo = empleadoInfo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Empleado: " + idEmpleado +
                " " + empleadoInfo;
    }
}
