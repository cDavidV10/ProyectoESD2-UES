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

        this.verClientesView.getBtnAgregarEmpleado().addActionListener(e -> {
            AgregarEmpleadoView agregarEmpleadoView = new AgregarEmpleadoView();
            CtrlAgregarEmpleado ctrlAgregarEmpleado = new CtrlAgregarEmpleado(agregarEmpleadoView, verClientesView,
                    bgContent);
            new Paneles().insertarPaneles(agregarEmpleadoView, bgContent);
        });

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
