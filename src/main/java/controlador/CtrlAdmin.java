/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import funciones.Paneles;
import funciones.UsuarioActivo;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.View;

import arboles.ArbolBinarioAVL;
import dao.VerClientesDAO;

import java.awt.event.WindowEvent;
import modelo.Usuario;
import vista.AdminView;
import vista.AgregarEmpleadoView;
import vista.Login;
import vista.ViewClientes;

/**
 *
 * @author cdavi
 */
public class CtrlAdmin {
    private AdminView adminView;
    private Usuario usuario;
    private Login loginView;

    public CtrlAdmin(AdminView adminView, Usuario usuario, Login loginView) {
        this.adminView = adminView;
        this.usuario = usuario;
        this.loginView = loginView;

        this.adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(adminView.getTxtUser(), usuario);
            }

        });

        this.adminView.getBtnEmpleado().addActionListener(e -> {
            AgregarEmpleadoView agregarEmpleadoView = new AgregarEmpleadoView();

            CtrlAgregarEmpleado ctrlAgregarEmpleado = new CtrlAgregarEmpleado(agregarEmpleadoView);
            new Paneles().insertarPaneles(agregarEmpleadoView, this.adminView.getBgPanel());
        });

        this.adminView.getBtnVerCliente().addActionListener(e -> {
            ViewClientes verClientesView = new ViewClientes();
            CtrlVerClientes ctrlVerClientes = new CtrlVerClientes(verClientesView);
            new Paneles().insertarPaneles(verClientesView, this.adminView.getBgPanel());

        });

        this.adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });

    }

}
