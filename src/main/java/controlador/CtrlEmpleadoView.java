/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import funciones.Paneles;
import vista.EmpleadoView;
import vista.Vista;
import dao.ClienteDAO;
import funciones.Paneles;
import vista.EmpleadoView;
import vista.FormCliente;
import vista.ViewClientes;

/**
 *
 * @author Yonathan
 */
public class CtrlEmpleadoView {
    private EmpleadoView empleadoView;
    private ClienteDAO dao = new ClienteDAO();
    private Vista viewDirec;

    public CtrlEmpleadoView(EmpleadoView empleadoView) {
        this.empleadoView = empleadoView;

        onClickVerClientes();

        this.empleadoView.getBtnMedidor().addActionListener(e -> {
            this.viewDirec = new Vista();
            new Paneles().insertarPaneles(viewDirec, empleadoView.getBgPanel());
            new CtrlDireccion(viewDirec);
        });
    }

    public void onClickVerClientes() {
        empleadoView.getBtnCliente().addActionListener(e -> {
            ViewClientes clientesView = new ViewClientes();
            CtrlEmpleadoVerClientes ctrlVerClientes = new CtrlEmpleadoVerClientes(clientesView);
            new Paneles().insertarPaneles(clientesView, this.empleadoView.getBgPanel());
        });
    }

}
