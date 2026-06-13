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

    @Override
    public List<Medidor> listarMedidores() throws Exception {
        List<Medidor> lista = new ArrayList<>();
        LIST = """
                 SELECT m.id_medidor, m.codigo, d.zona, d.num_casa, c.nombre, c.apellido 
                 FROM medidor m
                 INNER JOIN direccion d ON m.id_direccion = d.id_direccion
                 INNER JOIN contrato con ON con.id_medidor = m.id_medidor
                 INNER JOIN cliente c ON con.id_cliente = c.id_cliente
                 """;

        try {
            Connection con = Conexion.getConexion(); 
            PreparedStatement ps = con.prepareStatement(LIST); 
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));

                Direccion dir = new Direccion();
                dir.setZona(rs.getString("zona"));
                dir.setNumeroCasa(rs.getString("num_casa"));

                Contrato contrato = new Contrato();
                contrato.setCliente(cliente);

                Medidor medidor = new Medidor();
                medidor.setId(rs.getInt("id_medidor"));
                medidor.setCodigo(rs.getString("codigo"));
                medidor.setDireccion(dir);
                medidor.setContrato(contrato);

                lista.add(medidor);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return lista;
    }

}
