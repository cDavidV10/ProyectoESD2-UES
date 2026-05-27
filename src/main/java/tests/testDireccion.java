package tests;

import controlador.CtrlDireccion;
import vista.Vista;

public class testDireccion {
    
    public static void main(String[] args) {
        Vista vista = new Vista();
        CtrlDireccion controlador = new CtrlDireccion(vista);
        controlador.iniciar(); 
    }
}