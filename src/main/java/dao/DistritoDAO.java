package dao;

import conexion.Conexion;
import interfaz.IDistritoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Distrito;

public class DistritoDAO implements IDistritoDAO {

    private static final String SELECT_BY_MUNICIPIO = "SELECT id_distrito, nombre FROM public.distrito WHERE id_municipio = ?;";

    @Override
    public List<Distrito> listarPorMunicipio(int idMunicipio) throws Exception {
        List<Distrito> lista = new ArrayList<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_BY_MUNICIPIO);

        ps.setInt(1, idMunicipio);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Distrito d = new Distrito();
            d.setId(rs.getInt("id_distrito"));
            d.setNombre(rs.getString("nombre"));

            lista.add(d);
        }

        conn.close();
        return lista;
    }
}