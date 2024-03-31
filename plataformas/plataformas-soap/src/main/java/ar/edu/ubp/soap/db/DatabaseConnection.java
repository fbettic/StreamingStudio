package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private String databaseName;

    public DatabaseConnection(String databaseName) {
        this.databaseName = databaseName;
    }
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;databaseName=" + databaseName + ";encrypt=false;trustServerCertificate=true",
                "sa", "Chino@1234");
    }
}
