package dao;

import conexion.Conexion;
import interfaz.IFacturaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Factura;

public class FacturaDAO implements IFacturaDAO {

    private static final String INSERT_FACTURA = """
                                INSERT INTO factura(fecha_limite, mora, monto_consumo, monto_servicio, monto_neto, monto_total, id_lectura, id_empleado)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                """;
                                
    private static final String INSERT_PAGO = """
                                INSERT INTO pago (id_factura)
                                VALUES (?)
                                """;
    
    private static final String ULTIMO_ID = "SELECT MAX(id_factura) AS id FROM factura;"; // Toma el id de la ultima factura
    

    @Override
    public boolean guardar(Factura factura) throws Exception {
        Connection conn = Conexion.getConexion();
        
        boolean guardado = false;

        try {
            conn.setAutoCommit(false); // Evita que se manden los querys a la bd sin haberse asegurado q el segundo querys funcioone realmente

            PreparedStatement ps = conn.prepareStatement(INSERT_FACTURA);

            ps.setDate(1, Date.valueOf(factura.getFechaLimite()));
            ps.setBigDecimal(2, factura.getMora());
            ps.setBigDecimal(3, factura.getMontoConsumo());
            ps.setBigDecimal(4, factura.getMontoServicio());
            ps.setBigDecimal(5, factura.getMontoNeto());
            ps.setBigDecimal(6, factura.getMontoTotal());
            ps.setInt(7, factura.getLectura().getId());
            ps.setInt(8, factura.getEmpleado().getId());

            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                PreparedStatement psId = conn.prepareStatement(ULTIMO_ID);
                ResultSet rs = psId.executeQuery();
                
                int idGenerado = 0;
                if (rs.next()) {
                    idGenerado = rs.getInt("id");
                }

                PreparedStatement psPago = conn.prepareStatement(INSERT_PAGO);
                psPago.setInt(1, idGenerado);
                psPago.executeUpdate();
                
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