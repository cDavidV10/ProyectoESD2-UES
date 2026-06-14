package interfaz;

import java.time.LocalDate;
import arboles.ArbolBinarioAVL;
import modelo.Factura;
import modelo.Medidor;
import modelo.Usuario;

public interface IFacturaDAO {

    public boolean guardar(Factura factura) throws Exception;

    public boolean existeFactura(int idMedidor, LocalDate inicio, LocalDate fin) throws Exception;

    public ArbolBinarioAVL obtnerFacturasCliente(Usuario usuario) throws Exception;

    public ArbolBinarioAVL obtenerFacturasMedidor(Medidor medidor) throws Exception;

    public void realizarPago(Factura factura) throws Exception;

    public boolean isFacturasVencidas(int idMedidor) throws Exception;
}
