/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import arboles.ArbolB;
import conexion.Conexion;
import interfaz.IContratoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Direccion;
import modelo.Factura;
import modelo.Lectura;
import modelo.Medidor;

/**
 *
 * @author Yonathan
 */
public class ContratoDAO implements IContratoDAO {

    private String INSERT = """
        INSERT INTO contrato (tipo, tarifa, fecha_inicio, fecha_fin, id_cliente, id_medidor)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    @Override
    public void insertar(Contrato contrato) throws Exception {
        Connection conexion = new Conexion().getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(INSERT)) {
            ps.setString(1, contrato.getTipo());
            ps.setBigDecimal(2, contrato.getTarifa());
            ps.setDate(3, java.sql.Date.valueOf(contrato.getFechaInicio()));
            ps.setDate(4, java.sql.Date.valueOf(contrato.getFechaFin()));
            ps.setInt(5, contrato.getCliente().getId());
            ps.setInt(6, contrato.getMedidor().getId());

            ps.executeUpdate();
        }
    }

    @Override
    public Contrato buscarContratoMedidor(String cod_medidor) throws Exception {
        String sql = """
                     Select * from contrato c
                     join medidor m on c.id_medidor = m.id_medidor
                     join cliente cl on c.id_cliente = cl.id_cliente
                     where c.id_medidor = (select id_medidor from medidor where codigo = ?)
                     """;
        Contrato contrato = null;
        Connection conn = Conexion.getConexion();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, cod_medidor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
                contrato.setTarifa(rs.getBigDecimal("tarifa"));
                contrato.setTipo(rs.getString("tipo"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                System.out.println(cliente.toString());
                contrato.setCliente(cliente);
                
                Medidor medidor = new Medidor();
                Direccion direccion = new DireccionDAO().buscarDireccionId(rs.getInt("id_medidor"));
                medidor.setCodigo(rs.getString("codigo"));
                medidor.setDiametroNomila(rs.getString("diametro_nominal"));
                medidor.setDireccion(direccion);
                medidor.setUnidadMedida(rs.getString("unidad_medida"));
                medidor.setContrato(contrato);
                contrato.setMedidor(medidor);
            }

        } catch (Exception ex) {
            System.out.println("Error general: " + ex.getMessage());
        } finally {
            conn.close();
        }

        return contrato;
    }

}
