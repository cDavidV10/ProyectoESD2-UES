/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import arboles.ArbolBinarioAVL;
import dao.ClienteDAO;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Cliente;
import vista.FormCliente;

/**
 *
 * @author Yonathan
 */
public class CtrlFormCliente {
    private FormCliente formCliente;
    private ClienteDAO dao = new ClienteDAO();

    public CtrlFormCliente(FormCliente formCliente) {
        this.formCliente = formCliente;
        this.dao = dao;
        
        aplicarPlaceholder(formCliente.getTxtDuiCliente(), "00000000-0");
        aplicarPlaceholder(formCliente.getTxtNombreCliente(), "Ej. Carlos Bladimir");
        aplicarPlaceholder(formCliente.getTxtApellidoCliente(), "Ej. Acevedo Aleman");
        aplicarPlaceholder(formCliente.getTxtCorreoCliente(), "nombre@ejemplo.com");
        aplicarPlaceholder(formCliente.getTxtTelefonoCliente(), "0000-0000");
        
        onClickGuardar();
        onClickCancelar();
    }
    
    private void onClickGuardar() {
        formCliente.getBtnGuardarCliente().addActionListener(e -> {
              try {
                String dui = formCliente.getTxtDuiCliente().getText().trim();
                String nombre = formCliente.getTxtNombreCliente().getText().trim();
                String apellido = formCliente.getTxtApellidoCliente().getText().trim();
                String correo = formCliente.getTxtCorreoCliente().getText().trim();
                String telefono = formCliente.getTxtTelefonoCliente().getText().trim(); 
                
                // Capturar fecha desde JDateChooser
                java.util.Date fechaSeleccionada = formCliente.getJdcFechaNacimientoCliente().getDate();
                LocalDate fechaNacimiento = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); //Por si se usa LOCal

                Cliente cliente = new Cliente(0, dui, nombre, apellido, fechaNacimiento, correo, telefono);

                // Validar y guardar
                //validar(docente);
                
                if (dao.existeDui(cliente.getDui())) {
                    JOptionPane.showMessageDialog(null, "El DUI ya está registrado.");
                    return;
                }

                dao.insertar(cliente); 
                JOptionPane.showMessageDialog(null, "Docente guardado correctamente");

                // Refrescar tabla en la vista principal
                //CtrlEmpleadoVerClientes.verDatos();

                // Cerrar formulario
                formCliente.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }

    private void onClickCancelar(){
        formCliente.getBtnCancelarCliente().addActionListener(e -> formCliente.dispose());
    }
   
    private void aplicarPlaceholder(JTextField campo, String placeholder) {
        campo.setText(placeholder);
        campo.setForeground(Color.GRAY);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });
    }
}
