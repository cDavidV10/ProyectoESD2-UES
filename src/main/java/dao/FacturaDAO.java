package dao;

import conexion.Conexion;
import interfaz.IFacturaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import modelo.Factura;

public class FacturaDAO implements IFacturaDAO {

    private static final String INSERT = """
                                INSERT INTO public.factura(fecha_limite, mora, monto_consumo, monto_servicio, monto_neto, monto_total, id_lectura, id_empleado)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                """;

    @Override
    public boolean guardar(Factura factura) throws Exception {
        Connection conn = Conexion.getConexion();
        boolean guardado = false;

        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT);

            ps.setDate(1, Date.valueOf(factura.getFechaLimite()));
            ps.setDouble(2, factura.getMora());
            ps.setDouble(3, factura.getMontoConsumo());
            ps.setDouble(4, factura.getMontoServicio());
            ps.setDouble(5, factura.getMontoNeto());
            ps.setDouble(6, factura.getMontoTotal());
            ps.setInt(7, factura.getLectura().getId());
            ps.setInt(8, factura.getEmpleado().getId());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                guardado = true;
            }

            conn.commit();

        } catch (Exception ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        return guardado;
    }
}
