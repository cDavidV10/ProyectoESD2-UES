package controlador;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.JOptionPane;

import dao.EmpleadoDAO;
import funciones.Credenciales;
import funciones.Validaciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        // ? PlaceHolder
        this.agregarEmpleadoView.getTxtDui().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtDui().getText().equals("12345678-9")) {
                    agregarEmpleadoView.getTxtDui().setText("");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtNombre().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtNombre().setText("Juan Antonio");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtApellido().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtApellido().setText("Perez Martinez");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtTelefono().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtTelefono().setText("1234-5678");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtCorreo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtCorreo().setText("correo@dominio.com");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtSueldo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtSueldo().setText("2000.00");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.agregarEmpleadoView.getTxtNombre().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtNombre().getText().equals("Juan Antonio")) {
                    agregarEmpleadoView.getTxtNombre().setText("");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtDui().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtDui().setText("12345678-9");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtApellido().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtApellido().setText("Perez Martinez");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtTelefono().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtTelefono().setText("1234-5678");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtCorreo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtCorreo().setText("correo@dominio.com");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtSueldo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtSueldo().setText("2000.00");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.agregarEmpleadoView.getTxtApellido().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtApellido().getText().equals("Perez Martinez")) {
                    agregarEmpleadoView.getTxtApellido().setText("");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtNombre().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtNombre().setText("Juan Antonio");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtDui().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtDui().setText("12345678-9");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtTelefono().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtTelefono().setText("1234-5678");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtCorreo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtCorreo().setText("correo@dominio.com");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtSueldo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtSueldo().setText("2000.00");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.agregarEmpleadoView.getTxtTelefono().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtTelefono().getText().equals("1234-5678")) {
                    agregarEmpleadoView.getTxtTelefono().setText("");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtNombre().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtNombre().setText("Juan Antonio");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtApellido().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtApellido().setText("Perez Martinez");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtDui().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtDui().setText("12345678-9");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtCorreo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtCorreo().setText("correo@dominio.com");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtSueldo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtSueldo().setText("2000.00");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.agregarEmpleadoView.getTxtCorreo().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtCorreo().getText().equals("correo@dominio.com")) {
                    agregarEmpleadoView.getTxtCorreo().setText("");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtNombre().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtNombre().setText("Juan Antonio");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtApellido().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtApellido().setText("Perez Martinez");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtTelefono().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtTelefono().setText("1234-5678");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtDui().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtDui().setText("12345678-9");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtSueldo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtSueldo().setText("2000.00");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.agregarEmpleadoView.getTxtSueldo().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (agregarEmpleadoView.getTxtSueldo().getText().equals("2000.00")) {
                    agregarEmpleadoView.getTxtSueldo().setText("");
                    agregarEmpleadoView.getTxtSueldo().setForeground(new Color(0, 0, 0));
                }

                if (agregarEmpleadoView.getTxtNombre().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtNombre().setText("Juan Antonio");
                    agregarEmpleadoView.getTxtNombre().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtApellido().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtApellido().setText("Perez Martinez");
                    agregarEmpleadoView.getTxtApellido().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtTelefono().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtTelefono().setText("1234-5678");
                    agregarEmpleadoView.getTxtTelefono().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtCorreo().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtCorreo().setText("correo@dominio.com");
                    agregarEmpleadoView.getTxtCorreo().setForeground(new Color(170, 170, 170));
                }

                if (agregarEmpleadoView.getTxtDui().getText().isEmpty()) {
                    agregarEmpleadoView.getTxtDui().setText("12345678-9");
                    agregarEmpleadoView.getTxtDui().setForeground(new Color(170, 170, 170));
                }
            }

        });

    }

    private void enviarDatos() {
        String dui = this.agregarEmpleadoView.getTxtDui().getText();
        String nombre = this.agregarEmpleadoView.getTxtNombre().getText();
        String apellido = this.agregarEmpleadoView.getTxtApellido().getText();
        String correo = this.agregarEmpleadoView.getTxtCorreo().getText();
        String telefono = this.agregarEmpleadoView.getTxtTelefono().getText();
        String sueldoTxt = this.agregarEmpleadoView.getTxtSueldo().getText();

        if (!new Validaciones().validarSueldo(sueldoTxt)) {
            String mensaje = """
                    Sueldo Invalido
                    El sueldo debe contener 2 decimales
                    El sueldo no puede contener letras
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }
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
            fechaContrato = dateChooser.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        java.util.Date dateChooser2 = this.agregarEmpleadoView.getJcNacimiento().getDate();

        if (dateChooser2 != null) {
            fechaNacimiento = dateChooser2.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        Empleado empleado = new Empleado(dui, nombre, apellido, fechaNacimiento, fechaContrato,
                sueldo, genero, correo, telefono);

        if (!new Validaciones().validarDui(empleado.getDui())) {
            String mensaje = """
                    DUI Invalido
                    Ingrese un Dui valido
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarNombres(empleado.getNombre())) {
            String mensaje = """
                    Nombre Invalido
                    El nombre no puede contener numeros ni estar vacio
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarNombres(empleado.getApellido())) {
            String mensaje = """
                    Apellido Invalido
                    El apellido no puede contener numeros ni estar vacio
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarCorreo(empleado.getCorreo())) {
            String mensaje = """
                    Correo Invalido
                    Digite un correo valido
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarTelefono(empleado.getTelefono())) {
            String mensaje = """
                    Telefono Invalido
                    Digite un telefono valido
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarFechas(fechaNacimiento)) {
            String mensaje = """
                    Fecha Invalida
                    La fecha de nacimiento no puede ser nula ni tampoco puede ser superior al dia de hoy
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (fechaNacimiento.isEqual(fechaContrato)) {
            String mensaje = """
                    La fecha de contrato y de nacimiento no pueden ser la misma
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        if (!new Validaciones().validarFechas(fechaContrato)) {
            String mensaje = """
                    Fecha Invalida
                    La fecha de contrato no puede ser nula ni tampoco puede ser superior al dia de hoy
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        try {
            if (empleadoDAO.buscar(empleado.getDui())) {
                System.out.println("entra aqui");

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
