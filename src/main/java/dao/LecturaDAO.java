package dao;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.ILecturaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Cliente;
import modelo.Contrato;
import modelo.Direccion;
import modelo.Lectura;
import modelo.Medidor;

public class LecturaDAO implements ILecturaDAO {

    private static final String lectura = """
                                SELECT l.id_lectura, l.consumo, l.fecha_inicio, l.fecha_fin, 
                                    m.id_medidor, m.codigo, d.zona, d.num_casa, 
                                    c.nombre, c.apellido 
                                FROM lectura l
                                INNER JOIN medidor m ON l.id_medidor = m.id_medidor
                                INNER JOIN direccion d ON m.id_direccion = d.id_direccion
                                INNER JOIN contrato con ON con.id_medidor = m.id_medidor
                                INNER JOIN cliente c ON con.id_cliente = c.id_cliente
                                WHERE l.id_lectura NOT IN (SELECT id_lectura FROM factura);
                                """;

    @Override
    public ArbolBinarioAVL listarLecturasPendientes() throws Exception {
        ArbolBinarioAVL arbol = new ArbolBinarioAVL();

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(lectura); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));

                Direccion dir = new Direccion();
                dir.setZona(rs.getString("zona"));
                dir.setNumeroCasa(rs.getString("num_casa"));

                Medidor medidor = new Medidor();
                medidor.setId(rs.getInt("id_medidor"));
                medidor.setCodigo(rs.getString("codigo"));
                medidor.setDireccion(dir);

                Contrato contrato = new Contrato();
                contrato.setCliente(cliente);
                medidor.setContrato(contrato);

                Lectura lectura = new Lectura();
                lectura.setId(rs.getInt("id_lectura"));
                lectura.setConsumo((int) rs.getDouble("consumo"));
                lectura.setFechaInicial(rs.getDate("fecha_inicio").toLocalDate());
                lectura.setFechaFinal(rs.getDate("fecha_fin").toLocalDate());
                lectura.setMedidor(medidor);

                arbol.insertar(lectura);
            }
        }

        return arbol;
    }

    @Override
    public ArrayList<Lectura> buscarLecturaMedidor(int id_medidor) throws Exception {
        ArrayList<Lectura> datosLectura = new ArrayList<>();
        Lectura lectura = null;
        String sql = """
                     ELECT *
                     FROM lectura l
                     join medidor m on l.id_medidor = m.id_medidor
                     WHERE id_medidor = ?;
                     """;
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ps.setInt(1, id_medidor);
            while (rs.next()) {

                Medidor medid = new MedidorDAO().buscarPorId(rs.getInt("id_medidor"));

                lectura.setId(rs.getInt("id_lectura"));
                lectura.setConsumo((int) rs.getDouble("consumo"));
                lectura.setFechaInicial(rs.getDate("fecha_inicio").toLocalDate());
                lectura.setFechaFinal(rs.getDate("fecha_fin").toLocalDate());
                lectura.setMedidor(medid);
                
                datosLectura.add(lectura);
            }
        }

        return datosLectura;
    }
}
