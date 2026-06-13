package controlador;

import arboles.ArbolBinarioAVL;
import dao.FacturaDAO;
import dao.MedidorDAO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Empleado;
import modelo.Factura;
import modelo.Lectura;
import modelo.Medidor;
import vista.FacturaView;

public class CtrlFactura {

    private FacturaView vista;
    private MedidorDAO medidorDAO;
    private ArbolBinarioAVL arbolMedidores;

    public CtrlFactura(FacturaView vista) {
        this.vista = vista;
        this.medidorDAO = new MedidorDAO();
        cargarMedidores();
        events();
    }

    private void cargarMedidores() {
        try {
            List<Medidor> lista = medidorDAO.listarMedidores();
            arbolMedidores = new ArbolBinarioAVL();
            for (Medidor m : lista) {
                arbolMedidores.insertar(m);
            }

            vista.getCbMedidores().removeAllItems();
            Medidor vacio = new Medidor();
            vacio.setCodigo("Seleccione...");
            vista.getCbMedidores().addItem(vacio);

            ArrayList listaInd = arbolMedidores.IND();
            if (listaInd != null) {
                for (Object obj : listaInd) {
                    vista.getCbMedidores().addItem((Medidor) obj);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "[ERROR] No se han podido cargar los medidores.\n" + e.getMessage(), "Error BD", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void events() {
        this.vista.getCbMedidores().addActionListener(e -> cargarDatosMedidor());
        this.vista.getBtnCalcular().addActionListener(e -> calcular());
        this.vista.getBtnGenerarRecibo().addActionListener(e -> guardarFactura());
    }

    private void guardarFactura() {
        try {
            // medidor vacio
            if (vista.getCbMedidores().getSelectedIndex() <= 0) {
                JOptionPane.showMessageDialog(vista, "[ERROR] Seleccione un medidor.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Lectura lectura = new Lectura();
            lectura.setConsumo(Integer.parseInt(vista.getTxtConsumo().getText()));
            lectura.setFechaInicial(convertir(vista.getDateInicio().getDate()));
            lectura.setFechaFinal(convertir(vista.getDateFin().getDate()));

            Medidor medidor = (Medidor) vista.getCbMedidores().getSelectedItem();
            lectura.setMedidor(medidor);

            FacturaDAO dao = new FacturaDAO();
            if (dao.existeFactura(medidor.getId(), lectura.getFechaInicial(), lectura.getFechaFinal())) {
                JOptionPane.showMessageDialog(vista, "[ADVERTENCIA] Ya existe una factura generada para este medidor en este periodo.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // si no existe factura en el periodo dado, continua
            Factura factura = new Factura();
            factura.setLectura(lectura);
            factura.setFechaLimite(LocalDate.now().plusDays(30));
            factura.setMora(BigDecimal.ZERO);

            //Por si al calcular automaticamente lo arroja como comas
            factura.setMontoConsumo(new BigDecimal(vista.getTxtMontoConsumo().getText()));
            factura.setMontoServicio(new BigDecimal(vista.getTxtMontoServicio().getText()));

            factura.setMontoNeto(factura.getMontoConsumo().add(factura.getMontoServicio()));
            factura.setMontoTotal(factura.getMontoNeto());

            Empleado emp = new Empleado();
            emp.setId(1); // CAMBIAR POR EL EMPLEADO/USUARIO LOGUEADO EN ESE MOMENTO
            factura.setEmpleado(emp);

            if (dao.guardar(factura)) {
                JOptionPane.showMessageDialog(vista, "[MENSAJE] Factura generada con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "[ERROR]: Ocurrio un error inesperado.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void cargarDatosMedidor() {
        Medidor m = (Medidor) vista.getCbMedidores().getSelectedItem();

        vista.getTxtNombreCliente().setText("");
        vista.getTxtDireccion().setText("");
        vista.getTxtCodigoMedidor().setText("");

        if (m != null && !"Seleccione...".equals(m.getCodigo())) {
            vista.getTxtNombreCliente().setText(m.getContrato().getCliente().getNombre() + " " + m.getContrato().getCliente().getApellido());
            actualizarTooltip(vista.getTxtNombreCliente());

            vista.getTxtDireccion().setText(m.getDireccion().getZona());
            actualizarTooltip(vista.getTxtDireccion());

            vista.getTxtCodigoMedidor().setText(m.getCodigo());
            actualizarTooltip(vista.getTxtCodigoMedidor());

            vista.getCbMedidores().setToolTipText(m.toString());
        } else {
            vista.getCbMedidores().setToolTipText(null); // limpia el tooltip
        }
    }

    private void calcular() {
        try {
            Medidor m = (Medidor) vista.getCbMedidores().getSelectedItem();

            // medidor vacio
            if (m == null || "Seleccione...".equals(m.getCodigo())) {
                JOptionPane.showMessageDialog(vista, "[ERROR] Seleccione un medidor.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String textoConsumo = vista.getTxtConsumo().getText().trim();
            // consumo vacio
            if (textoConsumo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "[ERROR] Ingrese un consumo.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int consumo = Integer.parseInt(textoConsumo);
            // consumo negativo
            if (consumo < 0) {
                JOptionPane.showMessageDialog(vista, "[ERROR] El consumo no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Date fechaInicio = limpiarHora(vista.getDateInicio().getDate());
            Date fechaFin = limpiarHora(vista.getDateFin().getDate());

            // fechas vacias
            if (fechaInicio == null || fechaFin == null) {
                JOptionPane.showMessageDialog(vista, "[ERROR] Seleccione ambas fechas.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // orden de fechas
            if (fechaFin.getTime() <= fechaInicio.getTime()) {
                JOptionPane.showMessageDialog(vista, "[ERROR] La fecha fin debe ser mayor a la inicio.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // minimo 20 dias
            long diferencia = fechaFin.getTime() - fechaInicio.getTime();
            long dias = diferencia / (1000 * 60 * 60 * 24);

            if (dias < 20) {
                JOptionPane.showMessageDialog(vista, "[ERROR] El periodo es muy corto (" + dias + " dias).\nDebe ser de al menos 20 dias.", "Error de Periodo", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double montoConsumo = consumo * 0.50;
            double montoServicio = 10.00;
            double total = montoConsumo + montoServicio;

            vista.getTxtMontoConsumo().setText(String.format("%.2f", montoConsumo));
            actualizarTooltip(vista.getTxtMontoConsumo());

            vista.getTxtMontoServicio().setText(String.format("%.2f", montoServicio));
            actualizarTooltip(vista.getTxtMontoServicio());

            vista.getTxtTotalPagar().setText(String.format("%.2f", total));
            actualizarTooltip(vista.getTxtTotalPagar());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "[ERROR] Ingrese numeros validos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        vista.getCbMedidores().setSelectedIndex(0);
        vista.getTxtConsumo().setText("");
        vista.getTxtMontoConsumo().setText("");
        vista.getTxtMontoServicio().setText("");
        vista.getTxtTotalPagar().setText("");
        vista.getDateInicio().setDate(null);
        vista.getDateFin().setDate(null);
    }

    private LocalDate convertir(Date date) { // para convertir fechas tipo Date a LocalDate (por el JDateChooser que retorna Dates)
        if (date == null) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void actualizarTooltip(JTextField campo) { // para el tooltip
        campo.setToolTipText(campo.getText());
    }

    private Date limpiarHora(Date date) { // para manejar las horas dentro de la validacion de los 20 dias
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public void iniciar() {
        JFrame frame = new JFrame("Factura");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(this.vista);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
