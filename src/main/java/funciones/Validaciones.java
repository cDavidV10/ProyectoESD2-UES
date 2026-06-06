package funciones;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public boolean validarDui(String dui) {
        String regex = "^\\d{8}-\\d$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dui);
        return matcher.matches();
    }

    public boolean validarCorreo(String correo) {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    public boolean validarNombres(String nombre) {
        String regex = "^[[\\p{L}]]+(?: [[\\p{L}]]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();
    }

    public boolean validarTelefono(String telefono) {
        String regex = "^\\d{4}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }

    public boolean validarSueldo(String sueldo) {
        String regex = "^[0-9]+\\.[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sueldo);
        return matcher.matches();
    }

    public boolean validarFechas(LocalDate fecha) {
        if (fecha == null || fecha.isAfter(LocalDate.now())) {
            return false;
        }

        return true;
    }
}
