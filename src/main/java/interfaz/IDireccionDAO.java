package interfaz;

import modelo.Direccion;

public interface IDireccionDAO {
    void insertar(Direccion d) throws Exception;
    public Direccion buscarDireccionId(int id_direccion) throws Exception;
}