package dao;

import conexion.Conexion;
import interfaz.IDireccionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Direccion;
import modelo.Distrito;
import modelo.Municipio;

public class DireccionDAO implements IDireccionDAO {

    @Override
    public Direccion buscarDireccionId(int id_direccion) throws Exception {
        String sql = """
                     Select d.zona, d.num_casa, di.nombre, m.nombre
                     from medidor me
                     join direccion d on me.id_direccion = d.id_direccion
                     join distrito di on d.id_distrito = di.id_distrito
                     join municipio m on di.id_municipio = m.id_municipio
                     where d.id_direccion = ?
                     """;
        Direccion direccion = null;
        Connection conn = Conexion.getConexion();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id_direccion);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                direccion = new Direccion();
                Distrito distrito = new Distrito();
                distrito.setNombre("nombre");
                Municipio municipio = new Municipio();
                municipio.setNombre("nombre");
                distrito.setMunicipio(municipio);
                direccion.setZona(rs.getString("zona"));
                direccion.setNumeroCasa(rs.getString("num_casa"));
                direccion.setDistrito(distrito);
            }

        } catch (Exception ex) {
            System.out.println("Error general: " + ex.getMessage() + "Direccion");
        } finally {
            conn.close();
        }
        return direccion;
    }
}