/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IMedidorDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Direccion;
import modelo.Medidor;

/**
 *
 * @author danie
 */
public class MedidorDAO implements IMedidorDAO {

    private String INSERT = "";
    private String LIST = "";
    private String BUSCAR_POR_CODIGO = "SELECT * FROM medidor WHERE codigo = ?";
    private String MEDIDORES_DISP = """
        SELECT m.id_medidor, m.codigo, m.diametro_nominal, m.unidad_medida
        FROM medidor m
        WHERE m.id_medidor NOT IN (
            SELECT c.id_medidor
            FROM contrato c
            WHERE c.estado = 'Activo'
        )
    """;

    @Override
    public void crearRegistro(Medidor m) throws Exception {
        INSERT = """
                
                WITH nueva_direccion AS (
                    INSERT INTO direccion (zona, num_casa, id_distrito)
                    VALUES (?, ? , ?)
                    RETURNING id_direccion
                )
                INSERT INTO medidor (codigo, diametro_nominal, unidad_medida, id_direccion)
                SELECT ?, ?, ?, id_direccion
                FROM nueva_direccion;

                """;
        Connection conexion = Conexion.getConexion();
        try {
            conexion.setAutoCommit(false);
            PreparedStatement ps = conexion.prepareStatement(INSERT);
            ps.setString(1, m.getDireccion().getZona());
            ps.setString(2, m.getDireccion().getNumeroCasa());
            ps.setInt(3, m.getDireccion().getDistrito().getId());
            ps.setString(4, m.getCodigo());
            ps.setString(5, m.getDiametroNomila());
            ps.setString(6, m.getUnidadMedida());

            ps.executeUpdate();
            conexion.commit();
            JOptionPane.showMessageDialog(null, "Registrado Correctamente");
        } catch (Exception ex) {
            conexion.rollback();
            throw ex;
        } finally {
            conexion.close();
        }

    }

    @Override
    public boolean modificarRegistro(Medidor m) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArbolBinarioAVL listarDisponibles() throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();

        Connection conexion = Conexion.getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(MEDIDORES_DISP); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medidor m = new Medidor();
                m.setId(rs.getInt("id_medidor"));
                m.setCodigo(rs.getString("codigo"));
                m.setDiametroNomila(rs.getString("diametro_nominal"));
                m.setUnidadMedida(rs.getString("unidad_medida"));
                abinario.insertar(m);
            }
        }
        return abinario;
    }

    @Override
    public ArbolBinarioAVL buscarPorCodigo(String codigo) throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();
        Connection conexion = Conexion.getConexion();
        
        try (PreparedStatement ps = conexion.prepareStatement(BUSCAR_POR_CODIGO)) {
        ps.setString(1, codigo);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Direccion direccion = new Direccion(rs.getInt("id_direccion"));

                Medidor medidor = new Medidor(
                    rs.getInt("id_medidor"),
                    rs.getString("codigo"),
                    rs.getString("diametro_nominal"),
                    rs.getString("unidad_medida"),
                    direccion
                );
                abinario.insertar(medidor); 
            }
        }
    }

    return abinario;
    }

    @Override
    public Medidor buscarPorId(int id_medidor) throws Exception {
        String sql = "Select * from contrato c join medidor m on c.id_medidor = m.id_medidor where c.id_medidor = ?";
        
        Connection conexion = Conexion.getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id_medidor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Direccion direccion = new DireccionDAO().buscarDireccionId(rs.getInt("id_medidor"));
                    
                    Medidor medidor = new Medidor();
                    Contrato contrato = new Contrato();
                    
                    medidor.setCodigo(rs.getString("codigo"));
                    medidor.setDiametroNomila(rs.getString("diametro_nominal"));
                    medidor.setDireccion(direccion);
                    medidor.setUnidadMedida(rs.getString("unidad_medida"));
                    medidor.setContrato(contrato);
                    
                    return medidor;
                }
            }
        }
        return null;
    }

    @Override
    public List<Medidor> listarMedidores() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
