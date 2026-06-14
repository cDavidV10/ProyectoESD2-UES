/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import dao.PagoDAO;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import modelo.Factura;
import modelo.Pago;
import vista.ViewBotonesMedidor;
import vista.ViewDetalleFactura;

/**
 *
 * @author danie
 */
public class CtrlDetalleFactura {
    private Factura factura;
    private ViewDetalleFactura vista;

    public CtrlDetalleFactura(Factura facturaa, ViewDetalleFactura vista, ViewBotonesMedidor viewBtnes) throws Exception {
        this.factura = facturaa;
        this.vista = vista;
        
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
        
        this.vista.getBtnRealizarPago().addActionListener(e ->{
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de realizar el pago?", "¿Confirmar pago?", JOptionPane.YES_NO_OPTION);
            boolean pagoRealizado;
            if (respuesta == JOptionPane.YES_OPTION){
                Pago pago = factura.getPago();
                pago.setEstado("Pagado");
                pago.setFechaPago(LocalDate.now());
                pago.getFactura().setId(facturaa.getId());
                try {
                    pagoRealizado = new PagoDAO().modificarPago(pago);
                    if (pagoRealizado){
                        JOptionPane.showMessageDialog(null, "Pago Realizado!");
                    }
                    vista.setVisible(false);
                } catch (Exception ex) {
                    System.getLogger(CtrlDetalleFactura.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
