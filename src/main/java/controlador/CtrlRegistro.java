/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.RegistroDAO;
import funciones.Validaciones;
import modelo.Usuario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import vista.Login;
import vista.Registro;

public class CtrlRegistro {

    private Registro registroView;
    private RegistroDAO registroDAO = new RegistroDAO();
    private Login loginView;

    public CtrlRegistro(Registro registroView, Login loginView) {
        this.registroView = registroView;
        this.loginView = loginView;

        this.registroView.setLocationRelativeTo(null);

        this.registroView.getTxtUser().setText("Ingrese nuevo usuario");
        this.registroView.getTxtUser().setForeground(new Color(170, 170, 170));
        this.registroView.getTxtUser().setFont(new Font("Segoe UI", Font.PLAIN, 16));

        this.registroView.getTxtPassword().setText("****");
        this.registroView.getTxtPassword().setForeground(new Color(170, 170, 170));

        this.registroView.getTxtCodigo().setText("Ingrese código de medidor");
        this.registroView.getTxtCodigo().setForeground(new Color(170, 170, 170));
        this.registroView.getTxtCodigo().setEchoChar((char) 0);

        this.registroView.getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });

        this.registroView.getTxtUser().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (registroView.getTxtUser().getText().equals("Ingrese nuevo usuario")) {
                    registroView.getTxtUser().setText("");
                    registroView.getTxtUser().setForeground(new Color(0, 0, 0));
                    registroView.getTxtUser().setFont(new Font("Segoe UI", Font.BOLD, 16)); 
                }

                if (String.valueOf(registroView.getTxtPassword().getPassword()).isEmpty()) {
                    registroView.getTxtPassword().setText("****");
                    registroView.getTxtPassword().setForeground(new Color(170, 170, 170));
                }

                if (String.valueOf(registroView.getTxtCodigo().getPassword()).isEmpty()) {
                    registroView.getTxtCodigo().setEchoChar((char) 0);
                    registroView.getTxtCodigo().setText("Ingrese código de medidor");
                    registroView.getTxtCodigo().setForeground(new Color(170, 170, 170));
                }
            }
        });

        this.registroView.getTxtPassword().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(registroView.getTxtPassword().getPassword()).equals("****")) {
                    registroView.getTxtPassword().setText("");
                    registroView.getTxtPassword().setForeground(new Color(0, 0, 0));
                }

                if (registroView.getTxtUser().getText().isEmpty()) {
                    registroView.getTxtUser().setText("Ingrese nuevo usuario");
                    registroView.getTxtUser().setForeground(new Color(170, 170, 170));
                    registroView.getTxtUser().setFont(new Font("Segoe UI", Font.PLAIN, 16));
                }

                if (String.valueOf(registroView.getTxtCodigo().getPassword()).isEmpty()) {
                    registroView.getTxtCodigo().setEchoChar((char) 0);
                    registroView.getTxtCodigo().setText("Ingrese código de medidor");
                    registroView.getTxtCodigo().setForeground(new Color(170, 170, 170));
                }
            }
        });

        this.registroView.getTxtCodigo().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (String.valueOf(registroView.getTxtCodigo().getPassword()).equals("Ingrese código de medidor")) {
                    registroView.getTxtCodigo().setText("");
                    registroView.getTxtCodigo().setForeground(new Color(0, 0, 0));

                }

                if (registroView.getTxtUser().getText().isEmpty()) {
                    registroView.getTxtUser().setText("Ingrese nuevo usuario");
                    registroView.getTxtUser().setForeground(new Color(170, 170, 170));
                    registroView.getTxtUser().setFont(new Font("Segoe UI", Font.PLAIN, 16));
                }

                if (String.valueOf(registroView.getTxtPassword().getPassword()).isEmpty()) {
                    registroView.getTxtPassword().setText("****");
                    registroView.getTxtPassword().setForeground(new Color(170, 170, 170));
                }
            }
        });

        this.registroView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });
    }

    private void registrarUsuario() {

        String emailInput = this.registroView.getTxtUser().getText().trim();
        String passwordInput = String.valueOf(this.registroView.getTxtPassword().getPassword());
        String codigoMedidorInput = String.valueOf(this.registroView.getTxtCodigo().getPassword()).trim();

        if (emailInput.equals("Ingrese nuevo usuario") || emailInput.isEmpty()) {
            JOptionPane.showMessageDialog(registroView, "Por favor, ingrese un nombre de usuario.", "Campo Requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (passwordInput.equals("****") || passwordInput.isEmpty()) {
            JOptionPane.showMessageDialog(registroView, "Por favor, ingrese una contraseña.", "Campo Requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (codigoMedidorInput.equals("Ingrese código de medidor") || codigoMedidorInput.isEmpty()) {
            JOptionPane.showMessageDialog(registroView, "Por favor, ingrese el código de su medidor.",
                    "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!new Validaciones().validarCorreo(emailInput)) {
            JOptionPane.showMessageDialog(registroView,
                    "El usuario debe ser un correo electrónico válido.",
                    "Formato Inválido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            boolean existeCliente = registroDAO.validarClienteExistente(emailInput, codigoMedidorInput);

            if (!existeCliente) {
                JOptionPane.showMessageDialog(registroView,
                        "No se puede realizar el registro.\n\n"
                                + "Verifique que:\n"
                                + "- El número de medidor sea correcto.\n"
                                + "- El correo coincida con el registrado físicamente en su contrato.",
                        "Error de Verificación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(emailInput);
            nuevoUsuario.setPassword(passwordInput);
            nuevoUsuario.setCodigoMedidor(codigoMedidorInput);

            boolean registrado = registroDAO.registrarNuevoUsuario(nuevoUsuario);

            if (registrado) {
                JOptionPane.showMessageDialog(registroView, "¡Usuario registrado con éxito! Ya puede iniciar sesión.");

                loginView.setVisible(true);

                registroView.dispose();
            } else {
                JOptionPane.showMessageDialog(registroView, "No se pudo registrar el usuario. Inténtelo más tarde.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(registroView, "Error al conectar con la Base de Datos: " + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}