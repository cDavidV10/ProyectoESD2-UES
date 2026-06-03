package interfaz;

import modelo.Factura;

public interface IFacturaDAO {
    public boolean guardar(Factura factura) throws Exception;
}