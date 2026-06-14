/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import arboles.ArbolB;
import arboles.ArbolBinarioAVL;
import dao.ContratoDAO;
import dao.FacturaDAO;
import dao.MedidorDAO;
import funciones.Paneles;
import funciones.Validaciones;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.Contrato;
import modelo.Factura;
import modelo.Medidor;
import vista.ItemFacturaPanel;
import vista.ViewBotonesMedidor;
import vista.ViewDetalleFactura;
import vista.ViewRegistroMedidor;

/**
 *
 * @author danie
 */
public class CtrlBotonesMedidor {

    private ViewBotonesMedidor vista;
    private ViewRegistroMedidor regMedidor;
    private ViewDetalleFactura viewDetalle;
    private ArbolBinarioAVL resultFact;
    private Medidor resultMedidor;
    private Contrato resultContrato;
    private List<Factura> facturas;

    public CtrlBotonesMedidor(ViewBotonesMedidor vista_) {
        this.vista = vista_;

        resultMedidor = new Medidor();
        resultFact = new ArbolBinarioAVL();
        facturas = new ArrayList<>();

        vista.getBtnAgregarMed().addActionListener(e -> {
            this.regMedidor = new ViewRegistroMedidor();
            new Paneles().insertarPaneles(regMedidor, vista.getJpnViewOpcMedidor(), 1045, 535);
            new CtrlRegistroMedidor(regMedidor);
        });

        vista.getBtnBuscarMed().addActionListener(e -> {
            try {
                buscarMedidor();
            } catch (Exception ex) {
                System.getLogger(CtrlBotonesMedidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

    }

    private void buscarLecturasMedidor() {

    }

    private void buscarFacturasMedidor() throws Exception {
        resultFact = new FacturaDAO().obtenerFacturasMedidor(resultMedidor);
        mostrarFacturasMedidor();
    }

    private void mostrarFacturasMedidor() {
        facturas = resultFact.IND();

        JPanel panelFacturas = new JPanel();
        panelFacturas = vista.getJpnViewOpcMedidor();
        panelFacturas.removeAll();

        if (!facturas.isEmpty()) {
            for (Factura f : facturas) {
                ItemFacturaPanel fila = new ItemFacturaPanel(f);
                panelFacturas.add(fila);
//                panelFacturas.add(Box.createRigidArea(new Dimension(0, 5)));
                fila.getBtnDetalleFact().addActionListener(e -> {
                    viewDetalle = new ViewDetalleFactura();
                    new Paneles().insertarPaneles(viewDetalle, vista.getJpnViewOpcMedidor(), 720, 480);
                    try {
                        new CtrlDetalleFactura(f, viewDetalle);
                    } catch (Exception ex) {
                        System.getLogger(CtrlBotonesMedidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
            }
        }

        panelFacturas.revalidate();
        panelFacturas.repaint();
    }

    private void buscarMedidor() throws Exception {
        String codigo = vista.getTxtCodMedidor().getText();

        resultContrato = new ContratoDAO().buscarContratoMedidor(codigo);
        if (resultContrato != null) {
//        boolean valido = new Validaciones().validarNombres(codigo);
//        if (valido) {   
            try {
                resultMedidor = new MedidorDAO().buscarPorCodigo(codigo);
                if (resultMedidor != null) {
                    buscarFacturasMedidor();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay resultado");
                }
            } catch (Exception ex) {
                System.getLogger(CtrlBotonesMedidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Codigo no valido");
//        }
        }else{
            JOptionPane.showMessageDialog(null, "No existe un contrato con el medidor");
        }

    }
}
