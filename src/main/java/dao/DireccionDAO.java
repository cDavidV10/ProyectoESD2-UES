package dao;

import conexion.Conexion;
import interfaz.IDireccionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Direccion;

public class DireccionDAO implements IDireccionDAO {

    private static final String INSERT = "INSERT INTO direccion(zona, num_casa, id_distrito) VALUES (?, ?, ?);";

    @Override
    public void insertar(Direccion direccion) throws Exception {
        Connection conn = Conexion.getConexion();
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, direccion.getZona());
            ps.setString(2, direccion.getNumeroCasa());
            ps.setInt(3, direccion.getDistrito().getId());

            ps.executeUpdate();
            
            rs = ps.getGeneratedKeys();
            if (rs.next()){
                int idGenerado = rs.getInt(1);
                direccion.setId(idGenerado);
            }
            conn.commit();
            
            

        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
    }
}