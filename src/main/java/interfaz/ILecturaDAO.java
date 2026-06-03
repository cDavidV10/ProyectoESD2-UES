package interfaz;

import arboles.ArbolBinarioAVL;

public interface ILecturaDAO {
    public ArbolBinarioAVL listarLecturasPendientes() throws Exception;
}