package interfaz;

import java.time.LocalDate;
import modelo.Factura;
import modelo.Usuario;

public interface IFacturaDAO {
    public boolean guardar(Factura factura) throws Exception;
    public boolean existeFactura(int idMedidor, LocalDate inicio, LocalDate fin) throws Exception;
}