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
import vista.ContratosView;
import vista.EmpleadoView;
import vista.FacturaView;
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
    private Paneles paneles;

    public CtrlEmpleadoView(EmpleadoView empleadoView, Usuario usuario, Login loginView) {
        this.empleadoView = empleadoView;
        this.usuario = usuario;
        this.loginView = loginView;
        this.paneles = new Paneles();

        this.empleadoView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(empleadoView.getTxtUser(), usuario);
            }

        });

        onClickVerClientes();
        onClickVerContratos();

        this.empleadoView.getBtnMedidor().addActionListener(e -> {
            this.viewFuncMedidor = new ViewBotonesMedidor();
            paneles.insertarPaneles(viewFuncMedidor, empleadoView.getBgPanel());
            new CtrlBotonesMedidor(viewFuncMedidor);
        });

        this.empleadoView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });

        this.empleadoView.getBtnFactura().addActionListener(e->{
            FacturaView panel = new FacturaView();
            CtrlFactura controlador = new CtrlFactura(panel);

            paneles.insertarPaneles(panel, empleadoView.getBgPanel());
        });
    }

    public void onClickVerClientes() {
        empleadoView.getBtnCliente().addActionListener(e -> {
            ViewClientes clientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView, empleadoView.getBgPanel());

            paneles.insertarPaneles(clientesView, this.empleadoView.getBgPanel());
        });
    }
    

    public void onClickVerContratos() {
        empleadoView.getBtnContrato().addActionListener(e -> {
            ContratosView contratosView = new ContratosView();

            CtrlContratosView ctrlContratos = new CtrlContratosView(contratosView);
            
            new Paneles().insertarPaneles(contratosView, empleadoView.getBgPanel());
        });
    }
}
