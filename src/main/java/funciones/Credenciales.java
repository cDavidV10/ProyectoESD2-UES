package funciones;

import java.security.SecureRandom;
import java.time.LocalDate;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.EmpleadoDAO;

public class Credenciales {
    public void registrarCredenciales(String nombre, String apellido, String dui, String correo) {

        try {
            String user = new EmpleadoDAO().buscarUsuarioEmpleado(nombre, apellido);

            String nuevoUsuario = crearUsuario(user, nombre, apellido);
            String password = crearContraseña();
            String bdPassword = BCrypt.withDefaults().hashToString(12,
                    password.toCharArray());

            new EmpleadoDAO().insertarUsuario(nuevoUsuario, bdPassword, dui);

            new Correos().correoCredenciales(nombre, apellido, nuevoUsuario, password, correo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String crearUsuario(String ultimoUser, String nombre, String apellido) {
        String usuario = "user";
        String anio = String.valueOf(LocalDate.now().getYear());

        try {
            if (ultimoUser.equalsIgnoreCase("No hay resultado")) {
                usuario = "E"
                        + nombre.charAt(0)
                        + apellido.charAt(0)
                        + anio.charAt(2)
                        + anio.charAt(3)
                        + "001";
            } else {
                String cod = ultimoUser.substring(ultimoUser.length() - 3);
                int sigCod = Integer.parseInt(cod) + 1;
                String codCorrelativo = String.format("%03d", sigCod);

                usuario = ultimoUser.substring(0, ultimoUser.length() - 3) + codCorrelativo;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return usuario;
    }

    private String crearContraseña() {
        String alfabeto = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 6; i++) {
            int indiceAlfabet = random.nextInt(alfabeto.length());
            sb.append(alfabeto.charAt(indiceAlfabet));
        }
        return sb.toString();
    }
}
