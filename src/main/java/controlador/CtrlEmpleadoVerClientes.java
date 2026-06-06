package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioAVL;
import dao.ClienteDAO;
import funciones.Paneles;
import modelo.Cliente;
import vista.FormCliente;
import vista.ViewClientes;

public class CtrlEmpleadoVerClientes {
    private ViewClientes verClientesView;
    private ArbolBinarioAVL datos;
    private ClienteDAO dao = new ClienteDAO();
    private JPanel bgContent;

    public CtrlEmpleadoVerClientes(ViewClientes verClientesView, JPanel bgContent) {
        this.verClientesView = verClientesView;
        this.bgContent = bgContent;

        verDatos();
        onClickAgregarCliente();
    }

    private void verDatos() {
        try {
            // Aquí inicializas datos con lo que devuelve el DAO
            datos = dao.listar();

            if (datos == null) {
                JOptionPane.showMessageDialog(null, "No hay datos en la BD");
                return;
            }

            ArrayList<Cliente> lista = datos.IND();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[] { "DUI", "Nombre", "Apellido", "Edad", "Teléfono", "Correo" });

            lista.forEach(c -> modelo.addRow(new Object[] {
                    c.getDui(), c.getNombre(), c.getApellido(), c.CalcularEdad(), c.getTelefono(), c.getCorreo()
            }));

            verClientesView.getJtClientes().setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
        }
    }

    public void onClickAgregarCliente() {
        verClientesView.getBtnAgregarCliente().addActionListener(e -> {

            FormCliente formCliente = new FormCliente();
            CtrlFormCliente ctrlFormCliente = new CtrlFormCliente(formCliente, bgContent, verClientesView);
            new Paneles().insertarPaneles(formCliente, bgContent);
        });
    }
}
