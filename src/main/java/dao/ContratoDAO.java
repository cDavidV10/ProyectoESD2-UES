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
import modelo.Medidor;
import modelo.Usuario;

public class ContratoDAO implements IContratoDAO {

    private String INSERT = "INSERT INTO contrato (tipo, tarifa, fecha_inicio, fecha_fin, id_cliente, id_medidor) VALUES (?, ?, ?, ?, ?, ?)";
    private String SELECT_TODO = "SELECT cn.id_contrato, cn.tipo, cn.tarifa, cn.fecha_inicio, cn.fecha_fin, cn.estado, cl.nombre, cl.id_cliente, m.codigo, m.id_medidor FROM contrato cn INNER JOIN cliente cl ON cn.id_cliente = cl.id_cliente INNER JOIN medidor m ON m.id_medidor = cn.id_medidor";
    private String BUSCAR_POR_DUI = "SELECT cn.id_contrato, cn.tipo, cn.tarifa, cn.fecha_inicio, cn.fecha_fin, cn.estado, cl.dui, cl.nombre, cl.id_cliente, m.codigo, m.id_medidor FROM contrato cn INNER JOIN cliente cl ON cn.id_cliente = cl.id_cliente INNER JOIN medidor m ON m.id_medidor = cn.id_medidor WHERE cl.dui = ?";

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
        } finally {
            if (conexion != null) conexion.close();
        }
    }

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();
        try {
            PreparedStatement ps = conexion.prepareStatement(SELECT_TODO);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
                abinario.insertar(contrato);
            }
        } finally {
            conexion.close();
        }
        return abinario;
    }

    @Override
    public Contrato buscarContratoMedidor(String cod_medidor) throws Exception {
        String sql = "SELECT * FROM contrato c JOIN medidor m ON c.id_medidor = m.id_medidor JOIN cliente cl ON c.id_cliente = cl.id_cliente WHERE m.codigo = ?";
        Contrato contrato = null;
        Connection conexion = new Conexion().getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cod_medidor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
            }
        } finally {
            conexion.close();
        }
        return contrato;
    }

    @Override
    public ArbolBinarioAVL buscarPorDui(String dui) throws Exception {
        ArbolBinarioAVL abinario = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(BUSCAR_POR_DUI)) {
            ps.setString(1, dui);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contrato contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
                abinario.insertar(contrato);
            }
        } finally {
            conexion.close();
        }
        return abinario;
    }

    @Override
    public Contrato buscarContratoCliente(Usuario usuario) throws Exception {
        String sql = "SELECT * FROM contrato c JOIN medidor m ON c.id_medidor = m.id_medidor JOIN cliente cl ON c.id_cliente = cl.id_cliente JOIN usuario u ON cl.id_cliente = u.id_cliente WHERE u.username = ?";
        Contrato contrato = null;
        Connection conexion = new Conexion().getConexion();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getUsername());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                contrato = new Contrato();
                contrato.setId(rs.getInt("id_contrato"));
            }
        } finally {
            conexion.close();
        }
        return contrato;
    }
}