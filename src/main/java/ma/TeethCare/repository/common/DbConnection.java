package ma.TeethCare.repository.common; // Le package est 'common'

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/teethcare_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {

            throw new RuntimeException("Erreur de chargement du driver JDBC MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}