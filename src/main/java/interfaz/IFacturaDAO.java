package interfaz;

import java.time.LocalDate;
import arboles.ArbolB;
import arboles.ArbolBinarioBusqueda;
import modelo.Factura;
import modelo.Medidor;
import modelo.Usuario;

public interface IFacturaDAO {
    public boolean guardar(Factura factura) throws Exception;
    public boolean existeFactura(int idMedidor, LocalDate inicio, LocalDate fin) throws Exception;
    public ArbolBinarioBusqueda obtnerFacturasCliente(Usuario usuario) throws Exception;
    public ArbolB obtenerFacturasMedidor(Medidor medidor) throws Exception;
}