/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import at.favre.lib.crypto.bcrypt.BCrypt;
import conexion.Conexion;
import interfaz.IRegistroDAO;
import modelo.Usuario;

public class RegistroDAO implements IRegistroDAO {

    @Override
    public boolean validarClienteExistente(String correo, String medidor) throws Exception {

        String sql = "SELECT COUNT(*) FROM contrato c "
                + "INNER JOIN medidor m ON c.id_medidor = m.id_medidor "
                + "INNER JOIN cliente cl ON c.id_cliente = cl.id_cliente "
                + "WHERE cl.correo = ? AND m.codigo = ?";

        Connection conexion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean existe = false;

        try {
            conexion = Conexion.getConexion();
            ps = conexion.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, medidor);

            rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
        return existe;
    }

    @Override
    public boolean registrarNuevoUsuario(Usuario usr) throws Exception {

        String sqlBuscarCliente = "SELECT id_cliente FROM cliente WHERE correo = ?";

        String sqlInsertarUsuario = "INSERT INTO usuario (username, password, tipo, id_cliente) "
                + "VALUES (?, ?, ?::tipo_usuarios, ?)";

        Connection conexion = null;
        PreparedStatement psBuscar = null;
        PreparedStatement psInsertar = null;
        ResultSet rs = null;

        try {
            conexion = Conexion.getConexion();

            psBuscar = conexion.prepareStatement(sqlBuscarCliente);
            psBuscar.setString(1, usr.getUsername());
            rs = psBuscar.executeQuery();

            int idCliente = 0;
            if (rs.next()) {
                idCliente = rs.getInt("id_cliente");
            }

            psInsertar = conexion.prepareStatement(sqlInsertarUsuario);

            psInsertar.setString(1, usr.getUsername());

            String passwordEncriptada = BCrypt.withDefaults().hashToString(12, usr.getPassword().toCharArray());
            psInsertar.setString(2, passwordEncriptada);

            psInsertar.setString(3, "Cliente");

            if (idCliente > 0) {
                psInsertar.setInt(4, idCliente);
            } else {
                psInsertar.setNull(4, java.sql.Types.INTEGER);
            }

            int filasAfectadas = psInsertar.executeUpdate();
            return filasAfectadas > 0;

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (psBuscar != null) {
                psBuscar.close();
            }
            if (psInsertar != null) {
                psInsertar.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        }
    }
}
