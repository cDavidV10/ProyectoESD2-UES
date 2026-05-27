/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import funciones.UsuarioActivo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.event.WindowEvent;
import modelo.Usuario;
import vista.AdminView;
import vista.Login;

/**
 *
 * @author cdavi
 */
public class CtrlAdmin {
    AdminView adminView;
    Usuario usuario;
    Login loginView;

    public CtrlAdmin(AdminView adminView, Usuario usuario, Login loginView) {
        this.adminView = adminView;
        this.usuario = usuario;
        this.loginView = loginView;

        adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(adminView.getTxtUser(), usuario);
            }

        });

        this.adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });

    }

}
