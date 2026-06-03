package tests;

import controlador.CtrlFactura;
import vista.VistaFactura;

public class testFactura {

    public static void main(String[] args) {
        VistaFactura panel = new VistaFactura();
        CtrlFactura controlador = new CtrlFactura(panel);
        
        controlador.iniciar();
    }
}
