/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoestd;

import conexion.Conexion;
import controlador.CtrlLogin;
import vista.Login;

/**
 *
 * @author cdavi
 */
public class ProyectoESTD {

    public static void main(String[] args) {
        Login login = new Login();
        CtrlLogin ctrlLogin = new CtrlLogin(login);
        login.setVisible(true);
    }
}
