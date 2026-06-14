/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import arboles.ArbolBinarioAVL;
import modelo.Contrato;
import modelo.Usuario;

/**
 *
 * @author Yonathan
 */
public interface IContratoDAO {
    void insertar(Contrato contrato) throws Exception;
    public Contrato buscarContratoMedidor(String cod_medidor) throws Exception; 
    public Contrato buscarContratoCliente(Usuario usuario) throws Exception;
    ArbolBinarioAVL listar() throws Exception;
    ArbolBinarioAVL buscarPorDui(String dui) throws Exception;
    public Contrato buscarContratoMedidor(String cod_medidor) throws Exception; 
}
