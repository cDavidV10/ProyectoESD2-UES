package controlador;

import java.awt.Panel;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioBusqueda;
import dao.FacturaDAO;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;
import modelo.Factura;
import modelo.Usuario;
import vista.ClienteView;
import vista.ItemFacturaPanel;
import vista.PanelFacturasCliente;
import vista.ViewDetalleFactura;

public class CtrlClienteFactura {
    private ItemFacturaPanel pFacturasCliente;
    private ViewDetalleFactura viewDetalle;
    private FacturaDAO facturaDAO;
    private Usuario usuario;
    private ArbolBinarioBusqueda ab;
    private ArrayList<Factura> datos;

    public CtrlClienteFactura(ItemFacturaPanel pFacturasCliente, Usuario usuario) {
        this.pFacturasCliente = pFacturasCliente;
        this.usuario = usuario;
        this.facturaDAO = new FacturaDAO();

//        cargarDatosCliente();
    }

//    private void cargarDatosCliente() {
//
//        DefaultTableModel modelo = new DefaultTableModel();
//
//        String[] titulos = { "Mes de lectura", "Fecha Límite", "Monto Consumo", "Monto Servicio", "Total a Pagar" };
//
//        modelo.setColumnIdentifiers(titulos);
//
//
//        try {
//
//            ab = facturaDAO.obtnerFacturasCliente(usuario);
//
//            datos = ab.IND();
//
//            datos.forEach(dato -> {
//                Object[] obj = {
//                        dato.getLectura().getFechaFinal().getMonth().
//                        getDisplayName(TextStyle.FULL, 
//                            Locale.of("es", "Es")).toUpperCase(),
//                        dato.getFechaLimite(),
//                        String.format("$%.2f", dato.getMontoConsumo()),
//                        String.format("$%.2f", dato.getMontoServicio()),
//                        String.format("$%.2f", dato.getMontoTotal())
//
//                };
//
//                modelo.addRow(obj);
//            });
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "No hay Facturas para mostrar");
//            System.out.println(e.getMessage());
//        }
//
//        this.pFacturasCliente.getTablaFacturas().setModel(modelo);
//    }
//    
//    private void buscarFacturasMedidor() throws Exception {
//        ab = new FacturaDAO().obtnerFacturasCliente(usuario);
//        mostrarFacturasMedidor();
//    }

}
