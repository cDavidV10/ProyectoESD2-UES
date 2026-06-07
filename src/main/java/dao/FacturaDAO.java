package dao;

import conexion.Conexion;
import interfaz.IFacturaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import modelo.Factura;

public class FacturaDAO implements IFacturaDAO {

    private static final String INSERT = """
                                INSERT INTO public.factura(fecha_limite, mora, monto_consumo, monto_servicio, monto_neto, monto_total, id_lectura, id_empleado)
                                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                """;

    public DefaultTableModel obtenerFacturasCliente(int idCliente) {
        String[] columnas = {"N° Factura", "Fecha Límite", "Monto Consumo", "Monto Servicio", "Monto Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String sql = """
                     SELECT f.id_factura, f.fecha_limite, f.monto_consumo, f.monto_servicio, f.monto_total 
                     FROM factura f 
                     INNER JOIN lectura l ON f.id_lectura = l.id_lectura 
                     INNER JOIN medidor m ON l.id_medidor = m.id_medidor 
                     INNER JOIN contrato con ON con.id_medidor = m.id_medidor 
                     INNER JOIN cliente c ON con.id_cliente = c.id_cliente 
                     WHERE c.id_cliente = ? 
                     ORDER BY f.id_factura DESC;
                     """;

        try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);

            try (ResultSet rs = ps.executeQuery()) {
                Object[] fila = new Object[5];
                while (rs.next()) {
                    fila[0] = rs.getInt("id_factura");
                    fila[1] = rs.getDate("fecha_limite");
                    fila[2] = "$" + rs.getBigDecimal("monto_consumo");
                    fila[3] = "$" + rs.getBigDecimal("monto_servicio");
                    fila[4] = "$" + rs.getBigDecimal("monto_total");
                    modelo.addRow(fila);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener facturas: " + e.getMessage());
        } catch (Exception ex) {
            System.out.println("Error general: " + ex.getMessage());
        }

        return modelo;
    }

    @Override
    public boolean guardar(Factura factura) throws Exception {
        Connection conn = Conexion.getConexion();
        boolean guardado = false;

        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT);

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
