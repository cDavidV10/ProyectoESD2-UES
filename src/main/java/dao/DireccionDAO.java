package dao;

import conexion.Conexion;
import interfaz.IDireccionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Direccion;

public class DireccionDAO implements IDireccionDAO {

    private static final String INSERT = "INSERT INTO public.direccion(zona, num_casa, id_distrito) VALUES (?, ?, ?);";

    @Override
    public void insertar(Direccion direccion) throws Exception {
        Connection conn = Conexion.getConexion();

        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(INSERT);

            ps.setString(1, direccion.getZona());
            ps.setString(2, direccion.getNumeroCasa());
            ps.setInt(3, direccion.getDistrito().getId());

            ps.executeUpdate();
            conn.commit();

        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.close();
        }
    }
}