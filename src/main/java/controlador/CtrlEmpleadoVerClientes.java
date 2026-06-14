package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioAVL;
import dao.ClienteDAO;
import funciones.Paneles;
import funciones.Validaciones;
import modelo.Cliente;
import vista.FormCliente;
import vista.FormContrato;
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
        onClickAgregarContrato();
        onClickModificarCliente();
        onClickBuscarCliente();
    }

    private void verDatos() {
        try {
            datos = dao.listar();

            if (datos == null) {
                JOptionPane.showMessageDialog(null, "No hay datos en la BD");
                return;
            }

            ArrayList<Cliente> lista = datos.IND();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[] { "DUI", "Nombre", "Apellido", "Edad", "Teléfono", "Correo" });

            lista.forEach(c -> modelo.addRow(new Object[] {
                c.getDui(), 
                c.getNombre(), 
                c.getApellido(), 
                c.CalcularEdad(), 
                c.getTelefono(), 
                c.getCorreo()
            }));

            verClientesView.getJtClientes().setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
        }
    }

    public void onClickAgregarCliente() {
        verClientesView.getBtnAgregarCliente().addActionListener(e -> {
            FormCliente formCliente = new FormCliente();
            CtrlFormCliente ctrlFormCliente = new CtrlFormCliente(formCliente, bgContent, verClientesView, new Cliente(),
             "Agregar");
            new Paneles().insertarPaneles(formCliente, bgContent);
        });
    }
    
    public void onClickAgregarContrato() {
        verClientesView.getBtnAgregarContrato().addActionListener(e -> {
            int fila = verClientesView.getJtClientes().getSelectedRow();
            if (fila >= 0) {            
                String dui = verClientesView.getJtClientes().getValueAt(fila, 0).toString();
                try {
                    ArbolBinarioAVL arbolCliente = dao.buscarPorDui(dui);
                    Cliente clienteSeleccionado = (Cliente) arbolCliente.getRaiz().getDato();

                    FormContrato formContrato = new FormContrato();
                    CtrlFormContrato ctrlContrato = new CtrlFormContrato(formContrato, bgContent, clienteSeleccionado);
                    new Paneles().insertarPaneles(formContrato, bgContent);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al abrir contrato: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente primero.");
            }
        });
    }
    
    public void onClickModificarCliente() {
        verClientesView.getBtnModificarCliente().addActionListener(e -> {
   
            int fila = verClientesView.getJtClientes().getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un cliente de la tabla");
                return;
            }

            String dui = verClientesView.getJtClientes().getValueAt(fila, 0).toString();
            try {        
                ArbolBinarioAVL arbolCliente = dao.buscarPorDui(dui);

                if (arbolCliente == null || arbolCliente.getRaiz() == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró el cliente con DUI: " + dui);
                    return;
                }

                // Extraer el objeto Cliente desde el árbol
                Cliente cliente = (Cliente) arbolCliente.getRaiz().getDato();
                
                FormCliente formCliente = new FormCliente();
                CtrlFormCliente ctrlFormCliente = new CtrlFormCliente(formCliente, bgContent, verClientesView, cliente, 
                    "Actualizar");
                new Paneles().insertarPaneles(formCliente, bgContent);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar cliente: " + ex.getMessage());
            }
        });
    }
    
    public void onClickBuscarCliente() {
        verClientesView.getBtnBuscarCliente().addActionListener(e -> {
            String dui = verClientesView.getTxtDuiBuscarCliente().getText().trim();

            if (!new Validaciones().validarDui(dui)) {
                String mensaje = "DUI invalido o vacío, ingrese un DUI válido";
                JOptionPane.showMessageDialog(null, mensaje);
                return;
            }
            
            
            if (dui.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un DUI para buscar");
                return;
            }

            try {
                ArbolBinarioAVL arbolCliente = dao.buscarPorDui(dui);

                if (arbolCliente == null || arbolCliente.getRaiz() == null) {
                    JOptionPane.showMessageDialog(null, "No se encontró el cliente con DUI: " + dui);
                    return;
                }

                Cliente cliente = (Cliente) arbolCliente.getRaiz().getDato();

                // Mostrar solo ese cliente en la tabla
                DefaultTableModel modelo = new DefaultTableModel();
                modelo.setColumnIdentifiers(new String[]{"DUI", "Nombre", "Apellido", "Edad", "Teléfono", "Correo"});

                modelo.addRow(new Object[]{
                    cliente.getDui(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.CalcularEdad(),
                    cliente.getTelefono(),
                    cliente.getCorreo()
                });

                verClientesView.getJtClientes().setModel(modelo);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar cliente: " + ex.getMessage());
            }
        });
    }
}
