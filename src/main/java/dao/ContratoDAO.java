/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IContratoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Direccion;
import modelo.Medidor;
import modelo.Usuario;

/**
 *
 * @author Yonathan
 */
public class ContratoDAO implements IContratoDAO {

    private String INSERT = """
        INSERT INTO contrato (tipo, tarifa, fecha_inicio, fecha_fin, id_cliente, id_medidor)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    private String SELECT_TODO = """
        SELECT cn.id_contrato, cn.tipo, cn.tarifa, cn.fecha_inicio, cn.fecha_fin, cn.estado, 
               cl.nombre, cl.id_cliente, m.codigo, m.id_medidor 
        FROM contrato cn
        INNER JOIN cliente cl ON cn.id_cliente = cl.id_cliente
        INNER JOIN medidor m ON m.id_medidor = cn.id_medidor 
    """;
    
    private String BUSCAR_POR_DUI = """
        SELECT cn.id_contrato, cn.tipo, cn.tarifa, cn.fecha_inicio, cn.fecha_fin, cn.estado, 
               cl.dui, cl.nombre, cl.id_cliente, m.codigo, m.id_medidor 
        FROM contrato cn
        INNER JOIN cliente cl ON cn.id_cliente = cl.id_cliente
        INNER JOIN medidor m ON m.id_medidor = cn.id_medidor 
        WHERE dui = ?   
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

    //OCUPARE OTRO ARBOL CUANDO LO SUBAN
    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();

        conexion.setAutoCommit(false);
        PreparedStatement ps = conexion.prepareStatement(SELECT_TODO);
        ResultSet rs = ps.executeQuery();

        try {
            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
                contrato.setTipo(rs.getString("tipo"));
                contrato.setTarifa(rs.getBigDecimal("tarifa"));
                contrato.setFechaInicio(rs.getObject("fecha_inicio", LocalDate.class));
                contrato.setFechaFin(rs.getObject("fecha_fin", LocalDate.class));
                contrato.setEstado(rs.getString("estado"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setNombre(rs.getString("nombre"));

                Medidor medidor = new Medidor();
                medidor.setId(rs.getInt("id_medidor"));
                medidor.setCodigo(rs.getString("codigo"));

                //Asignando los objetos al contrato
                contrato.setCliente(cliente);
                contrato.setMedidor(medidor);

                abinario.insertar(contrato);
            }
            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            conexion.rollback();
        }

        return abinario;
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

    @Override
    public ArbolBinarioAVL buscarPorDui(String dui) throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();

        conexion.setAutoCommit(false);
        PreparedStatement ps = conexion.prepareStatement(BUSCAR_POR_DUI);
        
        ps.setString(1, dui);//parametro de busqueda
        ResultSet rs = ps.executeQuery();
        try {
            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
                contrato.setTipo(rs.getString("tipo"));
                contrato.setTarifa(rs.getBigDecimal("tarifa"));
                contrato.setFechaInicio(rs.getObject("fecha_inicio", LocalDate.class));
                contrato.setFechaFin(rs.getObject("fecha_fin", LocalDate.class));
                contrato.setEstado(rs.getString("estado"));
                
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setDui(rs.getString("dui"));
                cliente.setNombre(rs.getString("nombre"));

                Medidor medidor = new Medidor();
                medidor.setId(rs.getInt("id_medidor"));
                medidor.setCodigo(rs.getString("codigo"));

                //Asignando los objetos al contrato
                contrato.setCliente(cliente);
                contrato.setMedidor(medidor);

                abinario.insertar(contrato);
            }
            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            conexion.rollback();
        }

        return abinario;
    }

    @Override
    public Contrato buscarContratoCliente(Usuario usuario) throws Exception {
       String sql = """
                     Select * from contrato c
                     join medidor m on c.id_medidor = m.id_medidor
                     join cliente cl on c.id_cliente = cl.id_cliente
                     join usuario u on cl.id_cliente = u.id_cliente
                     where u.username = ?
                     """;
        
        Contrato contrato = new Contrato();
        Connection conn = Conexion.getConexion();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, usuario.getUsername().toUpperCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
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
                
                medidor.setCodigo(rs.getString("codigo"));
                medidor.setDiametroNomila(rs.getString("diametro_nominal"));
                
                medidor.setUnidadMedida(rs.getString("unidad_medida"));
                medidor.setContrato(contrato);
                medidor.setId(rs.getInt("id_medidor"));
                
                contrato.setMedidor(medidor);

            }
            conn.commit();
            conn.close();
        } catch (Exception e) {
            conn.rollback();
        }

        return contrato;
    }
}