package util;

import model.Empleado;
import model.Rol;
import model.Usuario;

public class UserSession {
    private static UserSession instance;
    private Usuario usuario;
    private Empleado empleado;
    private Rol rol;

    // Constructor privado para evitar instanciación externa
    private UserSession(Usuario usuario, Empleado empleado, Rol rol) {
        this.usuario = usuario;
        this.empleado = empleado;
        this.rol = rol;
    }

    // Método para iniciar sesión y establecer el usuario, empleado y rol
    public static void startSession(Usuario usuario, Empleado empleado, Rol rol) {
        if (instance == null) {
            instance = new UserSession(usuario, empleado, rol);
        }
    }

    // Método para obtener el usuario actual
    public static Usuario getUsuario() {
        return (instance != null) ? instance.usuario : null;
    }

    // Método para obtener el empleado actual
    public static Empleado getEmpleado() {
        return (instance != null) ? instance.empleado : null;
    }

    // Método para obtener el rol actual
    public static Rol getRol() {
        return (instance != null) ? instance.rol : null;
    }

    // Método para cerrar sesión
    public static void closeSession() {
        instance = null;
    }

    // Verificar si hay una sesión activa
    public static boolean isSessionActive() {
        return instance != null;
    }
}
