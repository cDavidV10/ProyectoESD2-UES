/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import java.time.LocalDate;

import javax.swing.JOptionPane;

import dao.FacturaDAO;
import modelo.Factura;
import modelo.Pago;
import vista.ViewDetalleFactura;

/**
 *
 * @author danie
 */
public class CtrlDetalleFactura {
    private Factura factura;
    private ViewDetalleFactura vista;
    private FacturaDAO facturaDAO;

    public CtrlDetalleFactura(Factura factura, ViewDetalleFactura vista) throws Exception {
        this.factura = factura;
        this.vista = vista;
        this.facturaDAO = new FacturaDAO();
        
        System.out.println("Factura id " + factura.getId());

        vista.getBtnRealizarPago().addActionListener(e-> {
            int respuesta = JOptionPane.showConfirmDialog(vista, "Esta seguro de realizar el pago",
            "Confirmar Pago",
            JOptionPane.YES_NO_OPTION);

            if(respuesta == JOptionPane.YES_OPTION){
                Pago pago = new Pago();
                pago.setFechaPago(LocalDate.now());
                pago.setEstado("Pagado");

                factura.setPago(pago);

                try {
                    facturaDAO.realizarPago(factura);

                    JOptionPane.showMessageDialog(null, "Pago realizado correctamente",
                        "Exito",
                        JOptionPane.OK_OPTION
                    );
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "No se pudo realizar el pago",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }

        });
        // mostrarDatos();
    }
    
    
    private void mostrarDatos() throws Exception{
        //Detalle lectura
        vista.getLblConsumo().setText(String.valueOf(factura.getLectura().getConsumo()));
        vista.getLblFcehaInic().setText(factura.getLectura().getFechaInicial().toString());
        vista.getLblFechaFin().setText(factura.getLectura().getFechaFinal().toString());
        vista.getLblCodigoMedi().setText(factura.getLectura().getMedidor().getCodigo());
        vista.getLblDireccion().setText(factura.getLectura().getMedidor().getDireccion().getZona() 
        + " Casa #" + factura.getLectura().getMedidor().getDireccion().getNumeroCasa() + ", " 
                + factura.getLectura().getMedidor().getDireccion().getDistrito().getNombre() + ", "
        + factura.getLectura().getMedidor().getDireccion().getDistrito().getMunicipio().getNombre());
        vista.getLblPropietario().setText(factura.getLectura().getMedidor().getContrato().getCliente().getNombre() + " "
        + factura.getLectura().getMedidor().getContrato().getCliente().getApellido());
        vista.getLblMora().setText(factura.getMora().toString());
        vista.getLblConsumo().setText(String.valueOf(factura.getMontoConsumo()));
        vista.getLblMontoServ().setText(String.valueOf(factura.getMontoServicio()));
        vista.getLblMontoNeto().setText(String.valueOf(factura.getMontoNeto()));
        vista.getLblLimitePago().setText(String.valueOf(factura.getFechaLimite()));
        vista.getLblTotal().setText(factura.getMontoTotal().toString());
//        vista.getLblPropietario().setText(contrato.getCliente().getNombre());
    }
}
