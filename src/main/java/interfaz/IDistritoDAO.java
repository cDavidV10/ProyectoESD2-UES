package interfaz;

import modelo.Distrito;
import java.util.List;

public interface IDistritoDAO {
    List<Distrito> listarPorMunicipio(int idMunicipio) throws Exception;
}