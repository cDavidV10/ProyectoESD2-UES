/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;



import modelo.Pago;
import vista.ViewBotonesMedidor;

import java.time.LocalDate;

import javax.swing.JOptionPane;
import dao.FacturaDAO;
import java.time.LocalDate;
import modelo.Factura;
import vista.ViewDetalleFactura;

/**
 *
 * @author danie
 */
public class CtrlDetalleFactura {
    private Factura factura;
    private ViewDetalleFactura vista;
    private FacturaDAO facturaDAO;

    public CtrlDetalleFactura(Factura facturaa, ViewDetalleFactura vista, ViewBotonesMedidor viewBtnes) throws Exception {
        this.factura = facturaa;
        this.vista = vista;
        this.facturaDAO = new FacturaDAO();
        
        vista.setLocation(100, 0);
        mostrarDatos();
        
        if (!factura.getPago().getEstado().equalsIgnoreCase("Pendiente")){
            vista.getBtnRealizarPago().setEnabled(false);
        }
        
        this.vista.getBtnRegresar().addActionListener(e ->{
            vista.setVisible(false);
            new CtrlBotonesMedidor(viewBtnes);
            viewBtnes.repaint();
        });
  

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
                        JOptionPane.INFORMATION_MESSAGE
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
    }
    
    
    private void mostrarDatos() throws Exception{
        //Detalle lectura
        vista.getLblConsumoDiam().setText(factura.getLectura().getConsumo() + "");
        vista.getLblFcehaInic().setText(factura.getLectura().getFechaInicial().toString());
        vista.getLblFechaFin().setText(factura.getLectura().getFechaFinal().toString());
        vista.getLblCodigoMedi().setText(factura.getLectura().getMedidor().getCodigo());
        vista.getLblDireccion().setText(factura.getLectura().getMedidor().getDireccion().getZona() 
        + ", Casa #" + factura.getLectura().getMedidor().getDireccion().getNumeroCasa() + ", " 
        + factura.getLectura().getMedidor().getDireccion().getDistrito().getNombre() + ", "
        + factura.getLectura().getMedidor().getDireccion().getDistrito().getMunicipio().getNombre());
        vista.getLblPropietario().setText(factura.getLectura().getMedidor().getContrato().getCliente().getNombre() + " "
        + factura.getLectura().getMedidor().getContrato().getCliente().getApellido());
        vista.getLblMora().setText("$" + factura.getMora());
        vista.getLblConsumoDin().setText("$" + factura.getMontoConsumo());
        vista.getLblMontoServ().setText("$" + factura.getMontoServicio());
        vista.getLblMontoNeto().setText("$" + factura.getMontoNeto());
        vista.getLblLimitePago().setText(factura.getFechaLimite().toString());
        vista.getLblTotal().setText("$" + factura.getMontoTotal());
    }
}
