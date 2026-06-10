package tests;

import controlador.CtrlFactura;
import vista.FacturaView;

public class testFactura {

    public static void main(String[] args) {
        FacturaView panel = new FacturaView();
        CtrlFactura controlador = new CtrlFactura(panel);
        
        controlador.iniciar();
    }
}
