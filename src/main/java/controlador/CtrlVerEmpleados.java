package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioAVL;
import dao.EmpleadoDAO;
import funciones.Paneles;
import modelo.Empleado;
import vista.AgregarEmpleadoView;
import vista.ViewEmpleados;

public class CtrlVerEmpleados {
    private ViewEmpleados verClientesView;
    private ArbolBinarioAVL datos;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private JPanel bgContent;

    public CtrlVerEmpleados(ViewEmpleados verClientesView, JPanel bgContent) {
        this.verClientesView = verClientesView;
        this.bgContent = bgContent;

        verDatos();

        if (this.verClientesView.getBtnAgregarEmpleado().getActionListeners().length == 0) { // verifica si ya fue accionado este boton. si ya lo fue, ignora dicha accion
            
            this.verClientesView.getBtnAgregarEmpleado().addActionListener(e -> {
                AgregarEmpleadoView agregarEmpleadoView = new AgregarEmpleadoView();
                CtrlAgregarEmpleado ctrlAgregarEmpleado = new CtrlAgregarEmpleado(agregarEmpleadoView, verClientesView, bgContent);
                
                new Paneles().insertarPaneles(agregarEmpleadoView, bgContent);
            });
        }

        if (this.verClientesView.getBtnEditarEmpleado().getActionListeners().length == 0) { // la misma funcionalidad que el de arriba
            
            this.verClientesView.getBtnEditarEmpleado().addActionListener(e -> {
                int filaSeleccionada = this.verClientesView.getJtClientes().getSelectedRow();
                
                if (filaSeleccionada == -1) {
                    JOptionPane.showMessageDialog(null, "[ERROR] Seleccione un empleado de la tabla para editar.", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String dui = this.verClientesView.getJtClientes().getValueAt(filaSeleccionada, 0).toString();
                
                ArrayList<Empleado> lista = datos.IND();
                Empleado empEdit = null;
                
                for (Empleado emp : lista) { // para encontrar el empleado a editar
                    if (emp.getDui().equals(dui)) {
                        empEdit = emp;
                        break;
                    }
                }

                if (empEdit != null) {
                    AgregarEmpleadoView editarView = new AgregarEmpleadoView();
                    CtrlAgregarEmpleado ctrlNuevo = new CtrlAgregarEmpleado(editarView, verClientesView, bgContent, empEdit);
                    
                    new Paneles().insertarPaneles(editarView, bgContent);
                }
            });
        }
    }

    private void verDatos() {

        try {
            datos = empleadoDAO.listar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay datos Para mostrar");
        }

        ArrayList<Empleado> datoList = datos.IND();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        String[] title = { "DUI", "Nombre", "Apellido", "Edad", "Contratacion", "Telefono", "Correo", "Sueldo",
                "Estado" };
        modeloTabla.setColumnIdentifiers(title);

        datoList.forEach(dato -> {
            Object[] obj = {
                    dato.getDui(),
                    dato.getNombre(),
                    dato.getApellido(),
                    dato.CalcularEdad(),
                    dato.getFechaContrato(),
                    dato.getTelefono(),
                    dato.getCorreo(),
                    dato.getSueldo(),
                    dato.getEstado()

            };

            modeloTabla.addRow(obj);
        });

        this.verClientesView.getJtClientes().setModel(modeloTabla);

    }

}
