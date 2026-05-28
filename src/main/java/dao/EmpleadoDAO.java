package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import arboles.ArbolBinarioAVL;
import conexion.Conexion;
import interfaz.IEmpleadoDAO;
import modelo.Cliente;
import modelo.Empleado;

public class EmpleadoDAO implements IEmpleadoDAO {

    @Override
    public void insertar(Empleado empleado) throws Exception {
        String insertar = """
                    insert into empleado(dui, nombre, apellido, fecha_nacimiento, fecha_contrato, sueldo, genero, correo, telefono)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        Connection conexion = Conexion.getConexion();
        PreparedStatement ps = conexion.prepareStatement(insertar);

        try {
            conexion.setAutoCommit(false);

            ps.setString(1, empleado.getDui());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setObject(4, empleado.getFechaNacimiento());
            ps.setObject(5, empleado.getFechaContrato());
            ps.setBigDecimal(6, empleado.getSueldo());
            ps.setString(7, empleado.getGenero());
            ps.setString(8, empleado.getCorreo());
            ps.setString(9, empleado.getTelefono());

            ps.executeUpdate();

            conexion.commit();
        } catch (Exception e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.close();
        }
    }

    @Override
    public boolean buscar(String dui) throws Exception {
        String buscar = "select count(*) from empleado emp where emp.dui = ?";
        Connection conexion = Conexion.getConexion();

        PreparedStatement ps = conexion.prepareStatement(buscar);
        ps.setString(1, dui);

        ResultSet rs = ps.executeQuery();

        rs.next();
        conexion.close();
        return rs.getInt(1) > 0;

    }

    @Override
    public String buscarUsuarioEmpleado(String nombre, String apellido) throws Exception {
        String resultado = "No hay resultado";
        String iniciales = "" + nombre.charAt(0) + apellido.charAt(0);
        String buscarUser = "SELECT username FROM usuario WHERE username Like ? ORDER BY username DESC LIMIT 1";

        Connection conexion = Conexion.getConexion();
        PreparedStatement ps = conexion.prepareStatement(buscarUser);
        ps.setString(1, "E" + iniciales + '%');

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            resultado = rs.getString("username");
        }
        rs.close();
        ps.close();
        conexion.close();
        return resultado;
    }

    @Override
    public void insertarUsuario(String user, String password, String dui) throws Exception {
        String insert = """
                    insert into usuario(username, password, tipo, id_cliente, id_empleado, id_admind)
                    SELECT ?, ?, 'Empleado', null, emp.id_empleado, null
                    from empleado emp where emp.dui = ?
                """;

        Connection conexion = Conexion.getConexion();
        PreparedStatement ps = conexion.prepareStatement(insert);

        try {
            conexion.setAutoCommit(false);
            ps.setString(1, user);
            ps.setString(2, password);
            ps.setString(3, dui);

            ps.executeUpdate();
            conexion.commit();
        } catch (Exception e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.close();
        }
    }

    @Override
    public ArbolBinarioAVL listar() throws Exception {
        String selectEmpleado = "select * from empleado";
        ArbolBinarioAVL aBinarioAVL = new ArbolBinarioAVL();
        Connection conexion = Conexion.getConexion();
        conexion.setAutoCommit(false);
        PreparedStatement ps = conexion.prepareStatement(selectEmpleado);
        ResultSet rs = ps.executeQuery();

        try {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("id_empleado"));
                empleado.setDui(rs.getString("dui"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setFechaNacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
                empleado.setFechaContrato(rs.getObject("fecha_contrato", LocalDate.class));
                empleado.setSueldo(rs.getBigDecimal("sueldo"));
                empleado.setGenero(rs.getString("genero"));
                empleado.setCorreo(rs.getString("correo"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setEstado(rs.getString("estado"));

                aBinarioAVL.insertar(empleado);
            }

            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            conexion.rollback();
        }

        return aBinarioAVL;
    }

}
