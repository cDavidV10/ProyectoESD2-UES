/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import interfaz.IContratoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import modelo.Contrato;

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

}
