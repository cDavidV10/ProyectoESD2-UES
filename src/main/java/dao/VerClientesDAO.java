package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IVerClientesDAO;
import modelo.Cliente;

public class VerClientesDAO implements IVerClientesDAO {
    private static final String SELECT_CLIENTE = "select * from cliente";

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL aBinarioAVL = new ArbolBinarioAVL();
        Connection conexion = new Conexion().getConexion();
        PreparedStatement ps = conexion.prepareStatement(SELECT_CLIENTE);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Cliente
        }

        return aBinarioAVL;
    }
}
