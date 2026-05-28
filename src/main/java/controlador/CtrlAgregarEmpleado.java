package controlador;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JOptionPane;

import dao.EmpleadoDAO;
import funciones.Credenciales;
import modelo.Empleado;
import vista.AgregarEmpleadoView;

public class CtrlAgregarEmpleado {
    private AgregarEmpleadoView agregarEmpleadoView;

    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public CtrlAgregarEmpleado(AgregarEmpleadoView agregarEmpleadoView) {
        this.agregarEmpleadoView = agregarEmpleadoView;

        this.agregarEmpleadoView.getBtnEnviar().addActionListener(e -> {
            enviarDatos();
        });
    }

    private void enviarDatos() {
        String dui = this.agregarEmpleadoView.getTxtDui().getText();
        String nombre = this.agregarEmpleadoView.getTxtNombre().getText();
        String apellido = this.agregarEmpleadoView.getTxtApellido().getText();
        String correo = this.agregarEmpleadoView.getTxtCorreo().getText();
        String telefono = this.agregarEmpleadoView.getTxtTelefono().getText();
        String sueldoTxt = this.agregarEmpleadoView.getTxtSueldo().getText();
        BigDecimal sueldo = new BigDecimal(sueldoTxt);
        String genero = "";
        LocalDate fechaNacimiento = null;
        LocalDate fechaContrato = null;

        if (this.agregarEmpleadoView.getRbMasculino().isSelected()) {
            genero = "Masculino";
        }

        if (this.agregarEmpleadoView.getRbFemenino().isSelected()) {
            genero = "Femenino";
        }

        java.util.Date dateChooser = this.agregarEmpleadoView.getJcContrato().getDate();

        if (dateChooser != null) {
            fechaNacimiento = dateChooser.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        java.util.Date dateChooser2 = this.agregarEmpleadoView.getJcNacimiento().getDate();

        if (dateChooser2 != null) {
            fechaContrato = dateChooser2.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        Empleado empleado = new Empleado(dui, nombre, apellido, fechaNacimiento, fechaContrato,
                sueldo, genero, correo, telefono);

        try {
            if (empleadoDAO.buscar(empleado.getDui())) {

                JOptionPane.showMessageDialog(null, "Empleado ya se encuentra almacenado");
                return;
            }

            empleadoDAO.insertar(empleado);
            JOptionPane.showMessageDialog(null, "Empleado guardado Correctamente");
            new Credenciales().registrarCredenciales(empleado.getNombre(), empleado.getApellido(),
                    empleado.getDui(), empleado.getCorreo());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
