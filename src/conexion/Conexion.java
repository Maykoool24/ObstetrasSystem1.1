package conexion;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class Conexion {

    public static Connection conectar() {
        String conexionUrl = "jdbc:sqlserver://localhost:1433;"
                + "database=obstetriciaDB;"
                + "user=maykol24;"
                + "password=maykol2003;"
                + "loginTimeout=30;"
                + "TrustServerCertificate=True;";

        try {
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}