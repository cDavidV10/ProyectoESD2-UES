package dao;

import conexion.Conexion;
import interfaz.IFacturaDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
<<<<<<< HEAD
import java.sql.Statement;
import java.time.LocalDate;
=======
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.table.DefaultTableModel;

import arboles.ArbolBinarioBusqueda;
>>>>>>> develop
import modelo.Factura;
import modelo.Usuario;

public class FacturaDAO implements IFacturaDAO {

    private static final String INSERT_LECTURA = "INSERT INTO lectura (consumo, fecha_inicio, fecha_fin, id_medidor) VALUES (?, ?, ?, ?)";

    private static final String INSERT_FACTURA = """
                                                 INSERT INTO factura(fecha_limite, mora, monto_consumo, monto_servicio, monto_neto, monto_total, id_lectura, id_empleado) 
                                                 VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                                                 """;

    private static final String INSERT_PAGO = "INSERT INTO pago (id_factura) VALUES (?)";

    private static final String FIND_FACTURA = """
                                               SELECT COUNT(*) FROM factura f 
                                               JOIN lectura l ON f.id_lectura = l.id_lectura
                                               WHERE l.id_medidor = ?
                                               AND l.fecha_inicio <= ?
                                               AND l.fecha_fin >= ?
                                               """;

    @Override
    public boolean guardar(Factura factura) throws Exception {
        Connection conn = Conexion.getConexion();
        boolean guardado = false;

        try {
            conn.setAutoCommit(false); // Para que solo se guarden las inserciones cuando vuelva a ser true

            // Guardamos lectura
            PreparedStatement psL = conn.prepareStatement(INSERT_LECTURA, Statement.RETURN_GENERATED_KEYS); // RETURN_GENERATED_KEYS retiene el id generado por este insert
            psL.setInt(1, factura.getLectura().getConsumo());
            psL.setDate(2, Date.valueOf(factura.getLectura().getFechaInicial()));
            psL.setDate(3, Date.valueOf(factura.getLectura().getFechaFinal()));
            psL.setInt(4, factura.getLectura().getMedidor().getId());
            psL.executeUpdate();

            // Se obtiene el id de la lectura creada arriba
            ResultSet rsL = psL.getGeneratedKeys(); // se le dice a psL que recupere la llave generada anteriormente
            int idLectura = 0;
            if (rsL.next()) {
                idLectura = rsL.getInt(1);
            }

            PreparedStatement psF = conn.prepareStatement(INSERT_FACTURA, Statement.RETURN_GENERATED_KEYS);
            psF.setDate(1, Date.valueOf(factura.getFechaLimite()));
            psF.setBigDecimal(2, factura.getMora());
            psF.setBigDecimal(3, factura.getMontoConsumo());
            psF.setBigDecimal(4, factura.getMontoServicio());
            psF.setBigDecimal(5, factura.getMontoNeto());
            psF.setBigDecimal(6, factura.getMontoTotal());
            psF.setInt(7, idLectura);
            psF.setInt(8, factura.getEmpleado().getId());
            psF.executeUpdate();

            // Obtiene id por factura
            ResultSet rsF = psF.getGeneratedKeys();
            int idFactura = 0;
            if (rsF.next()) {
                idFactura = rsF.getInt(1);
            }

            // Guarda el pago
            PreparedStatement psP = conn.prepareStatement(INSERT_PAGO);
            psP.setInt(1, idFactura);
            psP.executeUpdate();

            conn.commit();
            guardado = true;
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
        return guardado;
    }

    @Override
    public boolean existeFactura(int idMedidor, LocalDate inicio, LocalDate fin) throws Exception {
        Connection conn = Conexion.getConexion();
        try {
            PreparedStatement ps = conn.prepareStatement(FIND_FACTURA);
            ps.setInt(1, idMedidor);
            ps.setDate(2, java.sql.Date.valueOf(fin)); // para comparar con el fin existente
            ps.setDate(3, java.sql.Date.valueOf(inicio)); // para comparar con el inicio existente

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar las fechas: " + e.getMessage());
        }
        return false;
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
