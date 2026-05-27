package main;

import controlador.DireccionControlador;
import vista.Vista;

public class MainDireccion {
    
    public static void main(String[] args) {
        Vista vista = new Vista();
        DireccionControlador controlador = new DireccionControlador(vista);
        controlador.iniciar(); 
    }
}