/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import modelo.Factura;
import vista.ViewDetalleFactura;

/**
 *
 * @author danie
 */
public class CtrlDetalleFactura {
    private Factura factura;
    private ViewDetalleFactura vista;

    public CtrlDetalleFactura(Factura facturaa, ViewDetalleFactura vista) throws Exception {
        this.factura = facturaa;
        this.vista = vista;
        
        mostrarDatos();
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
