/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import modelo.Contrato;

/**
 *
 * @author Yonathan
 */
public interface IContratoDAO {
    void insertar(Contrato contrato) throws Exception;
    public Contrato buscarContratoMedidor(String cod_medidor) throws Exception; 
}
