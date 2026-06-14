package interfaz;

import modelo.Direccion;

public interface IDireccionDAO {
    public Direccion buscarDireccionId(int id_direccion) throws Exception;
}