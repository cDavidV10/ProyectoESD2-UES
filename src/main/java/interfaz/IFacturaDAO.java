package interfaz;

import arboles.ArbolBinarioBusqueda;
import modelo.Factura;
import modelo.Usuario;

public interface IFacturaDAO {
    public boolean guardar(Factura factura) throws Exception;

    public ArbolBinarioBusqueda obtnerFacturasCliente(Usuario usuario) throws Exception;
}