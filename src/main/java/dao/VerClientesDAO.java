package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IVerClientesDAO;
import modelo.Cliente;

public class VerClientesDAO implements IVerClientesDAO {
    private static final String SELECT_CLIENTE = "select * from cliente";

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL aBinarioAVL = new ArbolBinarioAVL();
        Connection conexion = Conexion.getConexion();
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
}
