package interfaz;

import arboles.ArbolBinarioAVL;
import java.util.List;
import modelo.Cliente;

public interface IClienteDAO {
    ArbolBinarioAVL listar() throws Exception;
    void insertar(Cliente cliente) throws Exception;
    void eliminar(String dui) throws Exception;
    Cliente buscarPorDui(String dui) throws Exception;
}
