package dao;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IMunicipioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Municipio;

public class MunicipioDAO implements IMunicipioDAO {

    private static final String SELECT_ALL = "SELECT id_municipio, nombre FROM public.municipio;";

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        ArbolBinarioAVL arbol = new ArbolBinarioAVL();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Municipio m = new Municipio();
            m.setId(rs.getInt("id_municipio"));
            m.setNombre(rs.getString("nombre"));

            arbol.insertar(m);
        }

        conn.close();
        return arbol; 
    }
}