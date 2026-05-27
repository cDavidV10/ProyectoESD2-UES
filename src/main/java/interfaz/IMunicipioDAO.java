package interfaz;

import java.util.List;
import modelo.Municipio;

public interface IMunicipioDAO {
    List<Municipio> listar() throws Exception;
}
