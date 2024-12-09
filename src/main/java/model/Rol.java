package model;

public class Rol {
    private int idRol;
    private String rolEmpleado;

    public static final Rol ADMINISTRADOR = new Rol(1,"Administrador");
    public static final Rol OFICINISTA = new Rol(2,"Oficinista");

    public Rol() {}

    public Rol(int idRol, String rolEmpleado) {
        this.idRol = idRol;
        this.rolEmpleado = rolEmpleado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRolEmpleado() {
        return rolEmpleado;
    }

    public void setRolEmpleado(String rolEmpleado) {
        this.rolEmpleado = rolEmpleado;
    }

}
