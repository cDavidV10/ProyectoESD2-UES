package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import modelo.Cliente;
import interfaz.IClienteDAO;
import java.sql.Statement;

public class ClienteDAO implements IClienteDAO {
    private static final String SELECT_CLIENTE = "select * from cliente";
    private static final String INSERT = "INSERT INTO cliente (dui, nombre, apellido, fecha_nacimiento, correo, telefono) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_REGISTRO = "DELETE FROM cliente WHERE dui = ?";

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL aBinarioAVL = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();
        conexion.setAutoCommit(false);
        PreparedStatement ps = conexion.prepareStatement(SELECT_CLIENTE);
        ResultSet rs = ps.executeQuery();

        try {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id_cliente"));
                cliente.setDui(rs.getString("dui"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setFechaNacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setTelefono(rs.getString("telefono"));

                aBinarioAVL.insertar(cliente);
            }

            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            conexion.rollback();
        }

        return aBinarioAVL;
    }

    @Override
    public void insertar(Cliente cliente) throws Exception {
        Connection conn = Conexion.getConexion();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getDui());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setDate(4, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            ps.setString(5, cliente.getCorreo());
            ps.setString(6, cliente.getTelefono());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {//Recuperando el ID generado
                if (rs.next()) {
                    cliente.setId(rs.getInt(1)); // asigna el ID al objeto
                }
            }

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
    }

    @Override
    public void eliminar(String dui) throws Exception {
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(DELETE_REGISTRO);
        ps.setString(1, dui);
        ps.executeUpdate(); // Para ejecutar importante
        conn.close();
    }

    // Compruebando si dui ya existe un docente con ese DUI
    public boolean existeDui(String dui) throws Exception {
        Connection conn = Conexion.getConexion();
        String sql = "SELECT COUNT(*) FROM Cliente WHERE dui = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, dui);
        ResultSet rs = ps.executeQuery();
        rs.next();
        boolean existe = rs.getInt(1) > 0;
        conn.close();
        return existe;
    }

}
