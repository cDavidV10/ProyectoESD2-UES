/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

import vista.AdminView;
import vista.Login;
import vista.Registro;

/**
 *
 * @author cdavi
 */
public class CtrlLogin {

    private Login loginView;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    public CtrlLogin(Login loginView) {
        this.loginView = loginView;

        this.loginView.setLocationRelativeTo(null);
        this.loginView.getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceder();
            }

        });

        this.loginView.getBtnRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Registro registroView = new Registro();

                CtrlRegistro ctrlRegistro = new CtrlRegistro(registroView, loginView);

                registroView.setVisible(true);
                registroView.setLocationRelativeTo(null);

                loginView.dispose();
            }
        });
        this.loginView.getTxtUser().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (loginView.getTxtUser().getText().equals("Ingrese su nombre de usuario")) {
                    loginView.getTxtUser().setText("");
                    loginView.getTxtUser().setForeground(new Color(0, 0, 0));
                }

                if (String.valueOf(loginView.getTxtPassword().getPassword()).isEmpty()) {
                    loginView.getTxtPassword().setText("****");
                    loginView.getTxtPassword().setForeground(new Color(170, 170, 170));
                }
            }

        });

        this.loginView.getTxtPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(loginView.getTxtPassword().getPassword()).equals("****")) {
                    loginView.getTxtPassword().setText("");
                    loginView.getTxtPassword().setForeground(new Color(0, 0, 0));
                }

                if (String.valueOf(loginView.getTxtUser().getText()).isEmpty()) {
                    loginView.getTxtUser().setText("Ingrese su nombre de usuario");
                    loginView.getTxtUser().setForeground(new Color(170, 170, 170));
                }
            }

        });
    }

    private void acceder() {

        String username = this.loginView.getTxtUser().getText();
        String password = String.valueOf(this.loginView.getTxtPassword().getPassword());

        try {
            String result = usuarioDAO.buscar(username, password);
            Usuario usuario = usuarioDAO.getUsuario();

            if (result.equalsIgnoreCase("Administrador")) {
                AdminView adminView = new AdminView();
                CtrlAdmin ctrlAdmin = new CtrlAdmin(adminView, usuario, loginView);
                adminView.setVisible(true);
                this.loginView.dispose();
            }

            if (result.equalsIgnoreCase("No")) {
                JOptionPane.showMessageDialog(loginView, "Usuario y/o contraseña incorrectos");
            }

            limpiarForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(loginView, e.getMessage());
        }

    }

    private void limpiarForm() {
        loginView.getTxtUser().setText("Ingrese su nombre de usuario");
        loginView.getTxtPassword().setText("****");

        loginView.getTxtUser().setForeground(new Color(170, 170, 170));
        loginView.getTxtPassword().setForeground(new Color(170, 170, 170));
    }

}
