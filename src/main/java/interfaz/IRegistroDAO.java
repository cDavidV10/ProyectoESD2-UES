/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import modelo.Usuario;

public interface IRegistroDAO {

    boolean validarClienteExistente(String medidor) throws Exception;

    boolean registrarNuevoUsuario(Usuario usr) throws Exception;
}
