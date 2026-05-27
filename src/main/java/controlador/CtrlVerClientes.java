package controlador;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioAVL;
import dao.VerClientesDAO;
import modelo.Cliente;
import vista.ViewVerClientes;

public class CtrlVerClientes {
    private ViewVerClientes verClientesView;
    private ArbolBinarioAVL datos;
    private VerClientesDAO verClientesDAO = new VerClientesDAO();

    public CtrlVerClientes(ViewVerClientes verClientesView) {
        this.verClientesView = verClientesView;

        verDatos();

    }

    private void verDatos() {

        try {
            datos = verClientesDAO.listar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No hay datos Para mostrar");
        }

        ArrayList<Cliente> datoList = datos.IND();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        String[] title = { "DUI", "Nombre", "Apellido", "Edad", "Telefono", "Correo" };
        modeloTabla.setColumnIdentifiers(title);

        datoList.forEach(dato -> {
            Object[] obj = {
                    dato.getDui(),
                    dato.getNombre(),
                    dato.getApellido(),
                    dato.CalcularEdad(),
                    dato.getTelefono(),
                    dato.getCorreo()
            };

            modeloTabla.addRow(obj);
        });

        this.verClientesView.getJtClientes().setModel(modeloTabla);

    }

}
