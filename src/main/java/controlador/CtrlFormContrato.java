/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ContratoDAO;
import dao.MedidorDAO;
import funciones.Paneles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Medidor;
import vista.ClienteView;
import vista.EmpleadoView;
import vista.FormCliente;
import vista.FormContrato;
import vista.ViewClientes;

/**
 *
 * @author Yonathan
 */
public class CtrlFormContrato {
    private FormContrato formContrato;
    private ContratoDAO contratoDAO = new ContratoDAO();
    private MedidorDAO medidorDAO = new MedidorDAO();
    private JPanel bgContent;
    private Cliente cliente; // viene del CtrlFormCliente

    public CtrlFormContrato(FormContrato formContrato, JPanel bgContent, Cliente cliente) throws Exception {
        this.formContrato = formContrato;
        this.bgContent = bgContent;
        this.cliente = cliente;

        cargarMedidoresDisponibles();
        onClickGuardarContrato();

        this.formContrato.getBtnRegresar().addActionListener(e -> {
            new Paneles().insertarPaneles(new FormCliente(), bgContent);
        });
    }

    private void cargarMedidoresDisponibles() throws Exception {
        List<Medidor> medidores = medidorDAO.listarDisponibles();
        for (Medidor m : medidores) {
            formContrato.getCbMedidoresDisponibles().addItem(m.getCodigo());
        }
    }

    private void onClickGuardarContrato() {
        formContrato.getBtnFinalizarContrato().addActionListener(e -> {
            try {
                String tipo = (String) formContrato.getCbTipoContrato().getSelectedItem();
                BigDecimal tarifa = new BigDecimal(formContrato.getTxtTarifaContrato().getText().trim());
    
                Date fechaInicioDate = formContrato.getJdcFechaInicioContrato().getDate();
                Date fechaFinDate = formContrato.getJdcFechaFinalizacionContrato().getDate();

                if (fechaInicioDate == null || fechaFinDate == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas");
                    return;
                }
                
                //a Localdate
                LocalDate fechaInicio = fechaInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaFin = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                // Obtener medidor seleccionado
                String codigoMedidor = (String) formContrato.getCbMedidoresDisponibles().getSelectedItem();

                Medidor medidor = medidorDAO.buscarPorCodigo(codigoMedidor);
                
                Contrato contrato = new Contrato(0, tipo, tarifa, fechaInicio, fechaFin, cliente, medidor);

                contratoDAO.insertar(contrato);
                JOptionPane.showMessageDialog(null, "Contrato guardado correctamente");
                
                EmpleadoView empleadoView = new EmpleadoView();
                ViewClientes clientesView = new ViewClientes();
                CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView, empleadoView.getBgPanel());

                new Paneles().insertarPaneles(clientesView, empleadoView.getBgPanel());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar contrato: " + ex.getMessage());
            }
        });
    }
}
