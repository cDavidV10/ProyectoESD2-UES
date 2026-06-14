package controlador;

import java.awt.Color;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.postgresql.translation.messages_nl;

import dao.EmpleadoDAO;
import funciones.Credenciales;
import funciones.Paneles;
import funciones.Validaciones;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import modelo.Empleado;
import vista.AgregarEmpleadoView;
import vista.ViewEmpleados;

public class CtrlAgregarEmpleado {
    private AgregarEmpleadoView agregarEmpleadoView;
    private ViewEmpleados viewEmpleados;
    private JPanel bgContent;
    private Empleado empleadoEdit = null; // para el modo edicion

    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public CtrlAgregarEmpleado(AgregarEmpleadoView agregarEmpleadoView, ViewEmpleados viewEmpleados,
            JPanel bgContent) {
        this.agregarEmpleadoView = agregarEmpleadoView;
        this.viewEmpleados = viewEmpleados;
        this.bgContent = bgContent;

        this.agregarEmpleadoView.getBtnEnviar().addActionListener(e -> {
            enviarDatos();
        });

        this.agregarEmpleadoView.getBtnRegresar().addActionListener(e -> {
            new Paneles().insertarPaneles(viewEmpleados, bgContent);
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
    
    // para editar
    public CtrlAgregarEmpleado(AgregarEmpleadoView agregarEmpleadoView, ViewEmpleados viewEmpleados, JPanel bgContent, Empleado empEdit) {
        this(agregarEmpleadoView, viewEmpleados, bgContent); // llamada al constructor de arriba
        
        this.empleadoEdit = empEdit;
        cargarDatosEdicion();
    }

    private void cargarDatosEdicion() {
        agregarEmpleadoView.getTxtDui().setText(empleadoEdit.getDui());
        agregarEmpleadoView.getTxtDui().setEnabled(false); // deshabilitar sui porque es llave primaria, no puede ser editable
        agregarEmpleadoView.getTxtDui().setForeground(Color.BLACK);

        agregarEmpleadoView.getTxtNombre().setText(empleadoEdit.getNombre());
        agregarEmpleadoView.getTxtNombre().setForeground(Color.BLACK);

        agregarEmpleadoView.getTxtApellido().setText(empleadoEdit.getApellido());
        agregarEmpleadoView.getTxtApellido().setForeground(Color.BLACK);

        agregarEmpleadoView.getTxtCorreo().setText(empleadoEdit.getCorreo());
        agregarEmpleadoView.getTxtCorreo().setForeground(Color.BLACK);

        agregarEmpleadoView.getTxtTelefono().setText(empleadoEdit.getTelefono());
        agregarEmpleadoView.getTxtTelefono().setForeground(Color.BLACK);

        agregarEmpleadoView.getTxtSueldo().setText(empleadoEdit.getSueldo().toString());
        agregarEmpleadoView.getTxtSueldo().setForeground(Color.BLACK);

        if (empleadoEdit.getGenero().equals("Masculino")) {
            agregarEmpleadoView.getRbMasculino().setSelected(true);
        } else {
            agregarEmpleadoView.getRbFemenino().setSelected(true);
        }

        Date dateNac = java.util.Date.from(empleadoEdit.getFechaNacimiento().atStartOfDay(ZoneId.systemDefault()).toInstant());
        agregarEmpleadoView.getJcNacimiento().setDate(dateNac);

        Date dateCont = java.util.Date.from(empleadoEdit.getFechaContrato().atStartOfDay(ZoneId.systemDefault()).toInstant());
        agregarEmpleadoView.getJcContrato().setDate(dateCont);

        agregarEmpleadoView.getBtnEnviar().setText("Actualizar");
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

        if (sueldo.compareTo(BigDecimal.valueOf(350.00)) < 0) {
            String mensaje = """
                    Sueldo invalido
                    El sueldo debe ser mayor a $350
                    """;

            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }
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

        if (ChronoUnit.YEARS.between(fechaNacimiento, fechaContrato) < 18) {
            String mensaje = """
                    Menor de edad
                    Lo sentimos pero no puedes contratar a una persona menor de 18 años
                    """;
            JOptionPane.showMessageDialog(null, mensaje);
            return;
        }

        try {
            if (this.empleadoEdit == null) { // guardar 
                if (empleadoDAO.buscar(empleado.getDui())) {
                    JOptionPane.showMessageDialog(null, "Empleado ya se encuentra almacenado");
                    return;
                }
                empleadoDAO.insertar(empleado);
                new Credenciales().registrarCredenciales(empleado.getNombre(), empleado.getApellido(),
                        empleado.getDui(), empleado.getCorreo());
                JOptionPane.showMessageDialog(null, "Empleado guardado Correctamente");

            } else { // actualizar
                empleadoDAO.actualizar(empleado);
                JOptionPane.showMessageDialog(null, "Empleado actualizado Correctamente");
            }
            
            CtrlVerEmpleados ctrlVerEmpleados = new CtrlVerEmpleados(viewEmpleados, bgContent); // para recargar
            new Paneles().insertarPaneles(viewEmpleados, bgContent);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[ERROR]: Ocurrio un error inesperado.\n" + e.getMessage());
        }
    }
}
