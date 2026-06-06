/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import interfaz.IMedidorDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelo.Medidor;

/**
 *
 * @author danie
 */
public class MedidorDAO implements IMedidorDAO{
    
    private String INSERT = "";
    

    @Override
    public void crearRegistro(Medidor m) throws Exception {
        INSERT = "INSERT INTO medidor(codigo, diametro_nominal, unidad_medida, id_direccion) VALUES (?,?,?,?)";
        
        Connection conexion = Conexion.getConexion();
        try {
            conexion.setAutoCommit(false);
            PreparedStatement ps = conexion.prepareStatement(INSERT);
            ps.setString(1, m.getCodigo());
            ps.setString(2, m.getDiametroNomila());
            ps.setString(3, m.getUnidadMedida());
            ps.setInt(4, m.getDireccion().getId());
            
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
    
}
