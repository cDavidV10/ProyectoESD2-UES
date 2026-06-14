/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import arboles.ArbolBinarioAVL;
import dao.ContratoDAO;
import dao.MedidorDAO;
import funciones.Paneles;
import funciones.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Medidor;
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
    private ArbolBinarioAVL arbol;

    public CtrlFormContrato(FormContrato formContrato, JPanel bgContent, Cliente cliente) throws Exception {
        this.formContrato = formContrato;
        this.bgContent = bgContent;
        this.cliente = cliente;

        cargarMedidoresDisponibles();
        onClickGuardarContrato();

        this.formContrato.getBtnRegresar().addActionListener(e -> {
            ViewClientes clientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView, bgContent);
            new Paneles().insertarPaneles(clientesView, bgContent);
        });
    }

    private void cargarMedidoresDisponibles() throws Exception {
        arbol = medidorDAO.listarDisponibles();
        ArrayList<Medidor> medidores = arbol.IND();
        for (Medidor m : medidores) {
            formContrato.getCbMedidoresDisponibles().addItem(m.getCodigo());
        }
    }

    private void onClickGuardarContrato() {
        formContrato.getBtnFinalizarContrato().addActionListener(e -> {
            try {
                String tipo = (String) formContrato.getCbTipoContrato().getSelectedItem();
                String tarifa = (String) formContrato.getTxtTarifaContrato().getText().trim();
    
                Date fechaInicioDate = formContrato.getJdcFechaInicioContrato().getDate();
                Date fechaFinDate = formContrato.getJdcFechaFinalizacionContrato().getDate();
                
                //a Localdate
                LocalDate fechaInicio = fechaInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate fechaFin = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                String codigoMedidor = (String) formContrato.getCbMedidoresDisponibles().getSelectedItem();
                
                ArbolBinarioAVL arbolMedidor = medidorDAO.buscarPorCodigo(codigoMedidor);
                
                Medidor medidor = null;
                if (arbolMedidor.getRaiz() != null) {
                    medidor = (Medidor) arbolMedidor.getRaiz().getDato();
                }
                
                //Validaciones
                
                if (!new Validaciones().validarTarifa(tarifa)) {
                    String mensaje = """
                        La tarifa debe ser un número positivo
                        Hasta 2 decimales y no puede estar vacía
                        """;
                    JOptionPane.showMessageDialog(null, mensaje);
                    return;
                }
                BigDecimal tarif = new BigDecimal(tarifa);
                
                if (!new Validaciones().validarFechaInicio(fechaInicio)) {
                    String mensaje = """
                        La fecha de inicio debe ser actual y/o futura
                        """;
                    JOptionPane.showMessageDialog(null, mensaje);
                    return;
                }
                
                if (new Validaciones().validarFechaFin(fechaInicio, fechaFin)) {
                    String mensaje = """
                            La fecha de finalización debe ser posterior a la fecha de inicio
                            Ademas, no superar los 2 años de plazo
                        """;
                    JOptionPane.showMessageDialog(null, mensaje);
                    return;
                }
                
                Contrato contrato = new Contrato(0, tipo, tarif, fechaInicio, fechaFin, cliente, medidor);
                
                contratoDAO.insertar(contrato);
                JOptionPane.showMessageDialog(null, "Contrato guardado correctamente");
                
                ViewClientes clientesView = new ViewClientes();
                CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView, bgContent);
                new Paneles().insertarPaneles(clientesView, bgContent);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar contrato: " + ex.getMessage());
            }
        });
    }
}
