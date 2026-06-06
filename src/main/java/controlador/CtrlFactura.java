package controlador;

import dao.FacturaDAO;
import dao.LecturaDAO;
import interfaz.IFacturaDAO;
import interfaz.ILecturaDAO;
import arboles.ArbolBinarioAVL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.Empleado;
import modelo.Factura;
import modelo.Lectura;
import vista.VistaFactura;

public class CtrlFactura {

    private VistaFactura vista;
    private ILecturaDAO lecturaDAO;
    private IFacturaDAO facturaDAO;
    private ArbolBinarioAVL arbolLecturas;

    private double montoConsumoCalc = 0.0;
    private final double cargoServicioCalc = 2.50;
    private double totalCalc = 0.0;

    public CtrlFactura(VistaFactura vista) {
        this.vista = vista;
        this.lecturaDAO = new LecturaDAO();
        this.facturaDAO = new FacturaDAO();

        cargarLecturasPendientes();
        events();
    }

    public void iniciar() {
        JFrame ventana = new JFrame("Generacion de Factura");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(650, 480);
        ventana.setLocationRelativeTo(null);
        ventana.add(this.vista);
        ventana.setVisible(true);
    }

    private void cargarLecturasPendientes() {
        try {
            arbolLecturas = lecturaDAO.listarLecturasPendientes();
            vista.getCbLecturasPendientes().removeAllItems();

            Lectura vacio = new Lectura();
            vacio.setId(0);
            vista.getCbLecturasPendientes().addItem(vacio);

            if (arbolLecturas != null) {
                ArrayList lista = arbolLecturas.IND();

                if (lista != null) {
                    for (Object obj : lista) {
                        Lectura lec = (Lectura) obj;
                        vista.getCbLecturasPendientes().addItem(lec);
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "[ERROR] No se han podido cargar las lecturas pendientes.\n" + ex.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void events() {
        this.vista.getCbLecturasPendientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDatosLectura();
            }
        });

        this.vista.getBtnCalcular().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarCalculos();
            }
        });

        this.vista.getBtnGenerarRecibo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
    }

    private void mostrarDatosLectura() {
        if (vista.getCbLecturasPendientes().getSelectedItem() == null) {
            return;
        }

        Lectura lecturaSeleccionada = (Lectura) vista.getCbLecturasPendientes().getSelectedItem();

        if (lecturaSeleccionada == null || lecturaSeleccionada.getId() == 0) {
            limpiarSeccionCampos();
            limpiarSeccionCalculos();
            return;
        }

        try {
            String nombreCompleto = lecturaSeleccionada.getMedidor().getContrato().getCliente().getNombre() + " " + lecturaSeleccionada.getMedidor().getContrato().getCliente().getApellido(); 
            String direccionCompleta = "Zona: " + lecturaSeleccionada.getMedidor().getDireccion().getZona() + ", Casa #" + lecturaSeleccionada.getMedidor().getDireccion().getNumeroCasa();
            String periodo = lecturaSeleccionada.getFechaInicial() + " al " + lecturaSeleccionada.getFechaFinal();

            vista.getTxtNombreCliente().setText(nombreCompleto);
            vista.getTxtCodigoMedidor().setText(lecturaSeleccionada.getMedidor().getCodigo());
            vista.getTxtDireccion().setText(direccionCompleta);
            vista.getTxtPeriodo().setText(periodo);

            limpiarSeccionCalculos();

        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(vista, "[ADVERTENCIA]: Seleccione una lectura valida para mostrar sus detalles.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarCalculos() {
        Lectura lecturaSeleccionada = (Lectura) vista.getCbLecturasPendientes().getSelectedItem();

        if (lecturaSeleccionada == null || lecturaSeleccionada.getId() == 0) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Seleccione una lectura válida para realizar el calculo.", "Lectura no seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double cuota = 0.86;
        double consumoM3 = lecturaSeleccionada.getConsumo();
        
        montoConsumoCalc = consumoM3 * cuota;
        totalCalc = montoConsumoCalc + cargoServicioCalc;

        vista.getTxtConsumo().setText(consumoM3 + " m³");
        vista.getTxtMontoConsumo().setText("$" + String.format("%.2f", montoConsumoCalc));
        vista.getTxtMontoServicio().setText("$" + String.format("%.2f", cargoServicioCalc));
        vista.getTxtTotalPagar().setText("$" + String.format("%.2f", totalCalc));
    }

    private void guardar() {
        Lectura lecturaSeleccionada = (Lectura) vista.getCbLecturasPendientes().getSelectedItem();

        if (lecturaSeleccionada == null || lecturaSeleccionada.getId() == 0) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Seleccione una lectura valida de la lista.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (totalCalc == 0.0 || vista.getTxtTotalPagar().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Debe procesar los calculos antes de guardar la factura.", "Falta Calcular", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Factura f = new Factura();
        f.setMontoConsumo(montoConsumoCalc);
        f.setMontoServicio(cargoServicioCalc);
        f.setMontoNeto(montoConsumoCalc + cargoServicioCalc);
        f.setMora(0.0);
        f.setMontoTotal(totalCalc);
        
        // Los 15 dias posteriores al fin del periodo. Cambiar a otro dia si es necesario, no me acuerdo cuantos eran xd
        LocalDate fechaVencimiento = lecturaSeleccionada.getFechaFinal().plusDays(15);
        f.setFechaLimite(fechaVencimiento);
        f.setLectura(lecturaSeleccionada);

        // 1 asumiendo que ese es el del empleado. Asumo que esto lo debe cambiar Edwin para registrar que empleado hizo la lectura
        Empleado emp = new Empleado();
        emp.setId(1);
        f.setEmpleado(emp);

        try {
            boolean exito = facturaDAO.guardar(f);

            if (exito) {
                JOptionPane.showMessageDialog(vista, "[MENSAJE]: Factura creada y procesada exitosamente.", "Creacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                limpiarTodo();
                cargarLecturasPendientes();
            } else {
                JOptionPane.showMessageDialog(vista, "[ERROR]: No se pudo crear la factura.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("fk_factura_lectura")) {
                JOptionPane.showMessageDialog(vista, "[ADVERTENCIA] Esta lectura ya ha sido facturad.", "Factura Duplicada", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "[ERROR]: Ocurrio un error inesperado.\n" + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarSeccionCampos() {
        vista.getTxtNombreCliente().setText("");
        vista.getTxtCodigoMedidor().setText("");
        vista.getTxtDireccion().setText("");
        vista.getTxtPeriodo().setText("");
    }

    private void limpiarSeccionCalculos() {
        vista.getTxtConsumo().setText("");
        vista.getTxtMontoConsumo().setText("");
        vista.getTxtMontoServicio().setText("");
        vista.getTxtTotalPagar().setText("");
        montoConsumoCalc = 0.0;
        totalCalc = 0.0;
    }

    private void limpiarTodo() {
        limpiarSeccionCampos();
        limpiarSeccionCalculos();
        if (vista.getCbLecturasPendientes().getItemCount() > 0) {
            vista.getCbLecturasPendientes().setSelectedIndex(0);
        }
    }
}