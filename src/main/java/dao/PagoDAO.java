/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import interfaz.IPagoDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import modelo.Pago;

/**
 *
 * @author danie
 */
public class PagoDAO implements IPagoDAO {

    @Override
    public boolean modificarPago(Pago pago) throws Exception {
        String sql = "UPDATE pago SET fecha_pago = ?, estado = ?, id_factura = ? WHERE id_pago = ?";
        boolean actualizado = false;

        try (Connection conn = Conexion.getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(pago.getFechaPago()));
            ps.setString(2, pago.getEstado());
            ps.setInt(3, pago.getFactura().getId());
            ps.setInt(4, pago.getId());

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                actualizado = true;
            }

        } catch (Exception ex) {
            throw new Exception("Error al actualizar pago: " + ex.getMessage());
        }

        return actualizado;
    }

}
