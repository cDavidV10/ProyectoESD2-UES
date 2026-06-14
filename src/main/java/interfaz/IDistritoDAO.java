package interfaz;

import arboles.ArbolBinarioAVL;

public interface IDistritoDAO {
    ArbolBinarioAVL listarPorMunicipio(int idMunicipio) throws Exception;
}