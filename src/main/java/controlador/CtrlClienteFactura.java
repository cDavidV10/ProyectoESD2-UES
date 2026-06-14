package controlador;

import arboles.ArbolBinarioAVL;
import java.awt.Panel;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.FacturaDAO;
import modelo.Factura;
import modelo.Usuario;
import vista.PanelFacturasCliente;

public class CtrlClienteFactura {
    private PanelFacturasCliente pFacturasCliente;
    private FacturaDAO facturaDAO;
    private Usuario usuario;

    public CtrlClienteFactura(PanelFacturasCliente pFacturasCliente, Usuario usuario) {
        this.pFacturasCliente = pFacturasCliente;
        this.usuario = usuario;
        this.facturaDAO = new FacturaDAO();

        cargarDatosCliente();
    }

    private void cargarDatosCliente() {

        DefaultTableModel modelo = new DefaultTableModel();

        String[] titulos = { "Mes de lectura", "Fecha Límite", "Monto Consumo", "Monto Servicio", "Total a Pagar" };

        modelo.setColumnIdentifiers(titulos);


        try {

            ArbolBinarioAVL abl = facturaDAO.obtnerFacturasCliente(usuario);

            ArrayList<Factura> datos = abl.IND();

            datos.forEach(dato -> {
                Object[] obj = {
                        dato.getLectura().getFechaFinal().getMonth().
                        getDisplayName(TextStyle.FULL, 
                            Locale.of("es", "Es")).toUpperCase(),
                        dato.getFechaLimite(),
                        String.format("$%.2f", dato.getMontoConsumo()),
                        String.format("$%.2f", dato.getMontoServicio()),
                        String.format("$%.2f", dato.getMontoTotal())

                };

                modelo.addRow(obj);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay Facturas para mostrar");
            System.out.println(e.getMessage());
        }

        this.pFacturasCliente.getTablaFacturas().setModel(modelo);
    }

}
