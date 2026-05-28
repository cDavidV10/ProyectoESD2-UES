package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioAVL;
import dao.EmpleadoDAO;
import modelo.Empleado;
import vista.ViewEmpleados;

public class CtrlVerEmpleados {
    private ViewEmpleados verClientesView;
    private ArbolBinarioAVL datos;
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public CtrlVerEmpleados(ViewEmpleados verClientesView) {
        this.verClientesView = verClientesView;

        verDatos();

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
