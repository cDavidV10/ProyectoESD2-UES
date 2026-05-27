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
import interfaz.IUsuarioDAO;
import modelo.Usuario;

/**
 *
 * @author cdavi
 */
public class UsuarioDAO implements IUsuarioDAO {
    private Usuario usuario = new Usuario();
    private static final String SELECT = "select * from usuario where username = ?";

    @Override
    public String buscar(String username, String password) throws Exception {
        Connection conexion = Conexion.getConexion();

        boolean existe = false;

        PreparedStatement ps = conexion.prepareStatement(SELECT);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuario.setId(rs.getInt(1));
            usuario.setUsername(rs.getString(2));
            usuario.setPassword(rs.getString(3));
            usuario.setTipo(rs.getString(4));

            existe = true;
        }

        if (existe) {
            if (usuario.getUsername().equalsIgnoreCase(username)) {
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(),
                        usuario.getPassword());

                if (result.verified) {

                    return usuario.getTipo();
                }
            }
        }

        conexion.close();
        return "No";
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
