package dao;

import conexion.Conexion;
import interfaz.IFacturaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioBusqueda;
import modelo.Factura;
import modelo.Usuario;

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

    @Override
    public ArbolBinarioBusqueda obtnerFacturasCliente(Usuario usuario) throws Exception {

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

        Connection conn = Conexion.getConexion();
        ArbolBinarioBusqueda aBusqueda = new ArbolBinarioBusqueda();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, usuario.getCliente().getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Factura factura = new Factura();
                factura.setId(rs.getInt("id_factura"));
                factura.setFechaLimite(rs.getObject("fecha_limite", LocalDate.class));
                factura.setMontoConsumo(rs.getBigDecimal("monto_consumo"));
                factura.setMontoServicio(rs.getBigDecimal("monto_servicio"));
                factura.setMontoTotal(rs.getBigDecimal("monto_total"));

                aBusqueda.insertar(factura);
            }

        } catch (Exception ex) {
            System.out.println("Error general: " + ex.getMessage());
        } finally {
            conn.close();
        }

        return aBusqueda;
    }
}
