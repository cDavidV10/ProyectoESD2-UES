/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import funciones.UsuarioActivo;
import modelo.Usuario;
import vista.ViewRegistroMedidor;
import dao.ClienteDAO;
import funciones.Paneles;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import vista.EmpleadoView;
import vista.Login;
import vista.ViewBotonesMedidor;
import vista.ViewClientes;

/**
 *
 * @author Yonathan
 */
public class CtrlEmpleadoView {
    private EmpleadoView empleadoView;
    private ClienteDAO dao = new ClienteDAO();
    private ViewBotonesMedidor viewFuncMedidor;
    private Usuario usuario;
    private Login loginView;

    public CtrlEmpleadoView(EmpleadoView empleadoView, Usuario usuario, Login loginView) {
        this.empleadoView = empleadoView;
        this.usuario = usuario;
        this.loginView = loginView;

        this.empleadoView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(empleadoView.getTxtUser(), usuario);
            }

        });

        onClickVerClientes();

        this.empleadoView.getBtnMedidor().addActionListener(e -> {
            this.viewFuncMedidor = new ViewBotonesMedidor();
            new Paneles().insertarPaneles(viewFuncMedidor, empleadoView.getBgPanel());
            new CtrlBotonesMedidor(viewFuncMedidor);
        });

        this.empleadoView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });
    }

    public void onClickVerClientes() {
        empleadoView.getBtnCliente().addActionListener(e -> {
            ViewClientes clientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView, empleadoView.getBgPanel());

            new Paneles().insertarPaneles(clientesView, this.empleadoView.getBgPanel());
        });
    }

}
