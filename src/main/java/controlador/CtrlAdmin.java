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
import dao.ClienteDAO;

import java.awt.event.WindowEvent;
import modelo.Usuario;
import vista.AdminView;
import vista.AgregarEmpleadoView;
import vista.ContratosView;
import vista.Login;
import vista.MedidorView;
import vista.ViewClientes;
import vista.ViewEmpleados;
import vista.Vista;

/**
 *
 * @author cdavi
 */
public class CtrlAdmin {
    private AdminView adminView;
    private Usuario usuario;
    private Login loginView;
    private Paneles paneles;
    private ViewEmpleados viewEmpleados;
    private CtrlVerEmpleados ctrlVerEmpleados;

    public CtrlAdmin(AdminView adminView, Usuario usuario, Login loginView) {
        this.adminView = adminView;
        this.usuario = usuario;
        this.loginView = loginView;
        this.paneles = new Paneles();
        this.viewEmpleados = new ViewEmpleados();
        this.ctrlVerEmpleados = new CtrlVerEmpleados(viewEmpleados, this.adminView.getBgPanel());

        paneles.insertarPaneles(viewEmpleados, this.adminView.getBgPanel());

        this.adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new UsuarioActivo().cambiarLabelUsuario(adminView.getTxtUser(), usuario);
            }

        });

        this.adminView.getBtnVerEmpleado().addActionListener(e -> {
            ctrlVerEmpleados = new CtrlVerEmpleados(viewEmpleados, this.adminView.getBgPanel());
            paneles.insertarPaneles(viewEmpleados, this.adminView.getBgPanel());
        });

        this.adminView.getBtnVerCliente().addActionListener(e -> {
            ViewClientes verClientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(verClientesView,
                    this.adminView.getBgPanel());
            paneles.insertarPaneles(verClientesView, this.adminView.getBgPanel());
        });

        this.adminView.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                loginView.setVisible(true);
            }
        });

        this.adminView.getBtnVerEmpleado().addActionListener(e -> {
            ViewEmpleados viewEmpleados = new ViewEmpleados();
            CtrlVerEmpleados ctrlVerEmpleados = new CtrlVerEmpleados(viewEmpleados, adminView.getBgPanel());
            paneles.insertarPaneles(viewEmpleados, adminView.getBgPanel());
        });

        this.adminView.getBtnMedidores().addActionListener(e -> {
            Vista viewDirec = new Vista();
            new Paneles().insertarPaneles(viewDirec, adminView.getBgPanel());
            new CtrlDireccion(viewDirec);
        });

        onClickVerClientes();
        onClickVerContratos();
    }
    
    private void onClickVerClientes() {
        adminView.getBtnVerCliente().addActionListener(e -> {
            ViewClientes verClientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(verClientesView, adminView.getBgPanel());
            paneles.insertarPaneles(verClientesView, adminView.getBgPanel());
        });
    }

    private void onClickVerContratos() {
        adminView.getBtnContratos().addActionListener(e -> {
            ContratosView contratosView = new ContratosView();
            CtrlContratosView ctrlContratos = new CtrlContratosView(contratosView);
            paneles.insertarPaneles(contratosView, adminView.getBgPanel());
        });
    }
}
