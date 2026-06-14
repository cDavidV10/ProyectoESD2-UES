/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import arboles.ArbolBinarioAVL;
import dao.ContratoDAO;
import funciones.Paneles;
import funciones.Validaciones;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.Contrato;
import vista.ContratosView;
import vista.EmpleadoView;


/**
 *
 * @author Yonathan
 */
public class CtrlContratosView {
    private ContratosView contratosView;
    private ArbolBinarioAVL arbol;
    private ContratoDAO contratoDAO = new ContratoDAO();
    
    public CtrlContratosView(ContratosView contratosView) {
        this.contratosView = contratosView;
      
        cargarContratos();
        onClickBuscarContrato();
    }

    private void cargarContratos() {
       try {
            arbol = contratoDAO.listar();
            if (arbol == null) {
                JOptionPane.showMessageDialog(null, "No hay datos en la BD");
                return;
            }
            ArrayList<Contrato> lista = arbol.IND();
            
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new String[] { "TIPO", "TARIFA", "FECHA INICIO", "FECHA FINALIZACIÓN", 
                "ESTADO", "NOMBRE CLIENTE", "CÓDIGO MEDIDOR" });

            lista.forEach(c -> modelo.addRow(new Object[] {
                c.getTipo(), 
                c.getTarifa(), 
                c.getFechaInicio(), 
                c.getFechaFin(), 
                c.getEstado(), 
                c.getCliente().getNombre(),
                c.getMedidor().getCodigo()
            }));

            contratosView.getTablaContratos().setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los contratos: " + e.getMessage());
        }
    }
    
    private void onClickBuscarContrato() {
        contratosView.getBtnBuscarContrato().addActionListener( e -> {
            String dui = contratosView.getTxtDuiBuscarContrato().getText().trim();

            if (!new Validaciones().validarDui(dui)) {
                String mensaje = """
                            DUI invalido o vacío, ingrese un DUI válido
                            """;
                JOptionPane.showMessageDialog(null, mensaje);
                return;
            }
            
            try {
                arbol = contratoDAO.buscarPorDui(dui);

                if (arbol == null) {
                    JOptionPane.showMessageDialog(null, "No se encontraron contratos para ese DUI");
                    cargarContratos();
                    return;
                }

                ArrayList<Contrato> lista = arbol.IND();

                DefaultTableModel modelo = new DefaultTableModel();
                modelo.setColumnIdentifiers(new String[]{ "TIPO", "TARIFA", "FECHA INICIO", "FECHA FINALIZACIÓN",
                    "ESTADO", "NOMBRE CLIENTE", "CÓDIGO MEDIDOR" });

                lista.forEach(c -> modelo.addRow(new Object[]{
                    c.getTipo(),
                    c.getTarifa(),
                    c.getFechaInicio(),
                    c.getFechaFin(),
                    c.getEstado(),
                    c.getCliente().getNombre(),
                    c.getMedidor().getCodigo()
                }));

                contratosView.getTablaContratos().setModel(modelo);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al buscar el contrato: " + ex.getMessage());
            }
        });
    }
}