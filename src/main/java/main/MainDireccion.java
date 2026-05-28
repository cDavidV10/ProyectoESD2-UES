package main;

import controlador.CtrlEmpleadoView;
import controlador.DireccionControlador;
import vista.EmpleadoView;
import vista.Vista;

public class MainDireccion {
    
    public static void main(String[] args) {
        EmpleadoView vista = new EmpleadoView();
        CtrlEmpleadoView controlador = new CtrlEmpleadoView(vista);
        vista.setVisible(true);
    }
}