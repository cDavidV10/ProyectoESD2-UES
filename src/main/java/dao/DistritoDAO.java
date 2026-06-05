package dao;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IDistritoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Distrito;

public class DistritoDAO implements IDistritoDAO {

    private static final String SELECT_BY_MUNICIPIO = "SELECT id_distrito, nombre FROM public.distrito WHERE id_municipio = ?;";

    @Override
    public ArbolBinarioAVL listarPorMunicipio(int idMunicipio) throws Exception {
        ArbolBinarioAVL arbol = new ArbolBinarioAVL();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_MUNICIPIO);

        ps.setInt(1, idMunicipio);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Distrito d = new Distrito();
            d.setId(rs.getInt("id_distrito"));
            d.setNombre(rs.getString("nombre"));

            arbol.insertar(d);
        }

        conn.close();
        return arbol; 
    }
}
