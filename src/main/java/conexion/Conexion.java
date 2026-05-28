package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Conexion {
    public static Connection getConexion() {
        Dotenv dotenv = Dotenv.load();
        String dbHost = dotenv.get("DB_HOST");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        try {
            Connection conexion = DriverManager.getConnection(dbHost, dbUser, dbPassword);

            if (conexion != null) {
                return conexion;
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }

        return null;
    }
}
