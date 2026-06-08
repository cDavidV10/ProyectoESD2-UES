package controlador;

import java.awt.Panel;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioBusqueda;
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

        String[] titulos = { "Fecha Límite", "Monto Consumo", "Monto Servicio", "Monto Total" };

        modelo.setColumnIdentifiers(titulos);

        System.out.println(usuario.getCliente().getId());

        try {

            ArbolBinarioBusqueda ab = facturaDAO.obtnerFacturasCliente(usuario);

            ArrayList<Factura> datos = ab.IND();

            datos.forEach(dato -> {
                Object[] obj = {
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
