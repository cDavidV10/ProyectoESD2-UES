/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import modelo.Medidor;

/**
 *
 * @author danie
 */
public interface IMedidorDAO {
    public void crearRegistro(Medidor m) throws Exception;
    public boolean modificarRegistro(Medidor m) throws Exception;
}
