package interfaz;

import modelo.Empleado;

public interface IEmpleadoDAO {
    public void insertar(Empleado empleado) throws Exception;

    public boolean buscar(String dui) throws Exception;

    public String buscarUsuarioEmpleado(String nombre, String apellido) throws Exception;

    public void insertarUsuario(String user, String password, String dui) throws Exception;
}
