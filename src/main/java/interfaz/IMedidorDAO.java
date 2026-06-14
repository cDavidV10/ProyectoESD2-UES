/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import arboles.ArbolBinarioAVL;
import java.util.List;
import modelo.Cliente;
import modelo.Medidor;

/**
 *
 * @author danie
 */
public interface IMedidorDAO {
    public void crearRegistro(Medidor m) throws Exception;
    public boolean modificarRegistro(Medidor m) throws Exception;
    ArbolBinarioAVL listarDisponibles() throws Exception;
    ArbolBinarioAVL buscarPorCodigo(String codigo) throws Exception;
    public List<Medidor> listarMedidores() throws Exception; // para que funcione lo de factura
    public Medidor buscarPorId(int id_medidor) throws Exception;
}
