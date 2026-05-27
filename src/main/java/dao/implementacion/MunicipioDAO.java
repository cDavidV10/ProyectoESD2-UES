package dao.implementacion;

import conexion.Conexion;
import interfaz.IMunicipioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Municipio;

public class MunicipioDAO implements IMunicipioDAO {

    private static final String SELECT_ALL = "SELECT id_municipio, nombre FROM public.municipio;";

    @Override
    public List<Municipio> listar() throws Exception {
        List<Municipio> lista = new ArrayList<>();
        Connection conn = Conexion.getConexion();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            Municipio m = new Municipio();
            m.setIdMunicipio(rs.getInt("id_municipio"));
            m.setNombre(rs.getString("nombre"));
            
            lista.add(m);
        }
        
        conn.close();
        return lista;
    }
}