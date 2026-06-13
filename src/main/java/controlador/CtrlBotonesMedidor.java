/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import funciones.Paneles;
import vista.ViewBotonesMedidor;
import vista.ViewRegistroMedidor;

/**
 *
 * @author danie
 */
public class CtrlBotonesMedidor {
    private ViewBotonesMedidor vista;
    private ViewRegistroMedidor regMedidor;

    public CtrlBotonesMedidor(ViewBotonesMedidor vista_) {
        this.vista = vista_;
        
        vista.getBtnAgregarMed().addActionListener(e ->{
            this.regMedidor = new ViewRegistroMedidor();
            new Paneles().insertarPaneles(regMedidor, vista.getJpnViewOpcMedidor(), 1045, 535);
            new CtrlRegistroMedidor(regMedidor);
        });
    }
}
