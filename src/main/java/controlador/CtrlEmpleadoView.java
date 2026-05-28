/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import funciones.Paneles;
import vista.EmpleadoView;
import vista.Vista;

/**
 *
 * @author danie
 */
public class CtrlEmpleadoView {
    private EmpleadoView view;
    private Vista viewDirec;    

    public CtrlEmpleadoView(EmpleadoView vieew) {
        this.view = vieew;
        this.view.getBtnMedidor().addActionListener(e ->{
            this.viewDirec = new Vista();
            new Paneles().insertarPaneles(viewDirec, view.getBgPanel());
            new DireccionControlador(viewDirec);
        });
    }
}
