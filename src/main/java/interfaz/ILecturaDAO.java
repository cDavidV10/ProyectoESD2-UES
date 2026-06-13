package interfaz;

import arboles.ArbolBinarioAVL;
import java.util.ArrayList;
import modelo.Lectura;
import modelo.Medidor;

public interface ILecturaDAO {
    public ArbolBinarioAVL listarLecturasPendientes() throws Exception;
    public ArrayList<Lectura> buscarLecturaMedidor(int id_medidor) throws Exception;
}