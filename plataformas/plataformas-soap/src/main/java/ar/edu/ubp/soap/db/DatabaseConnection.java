package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static String databaseName;

    // Constructor privado para evitar la instanciación externa
    private DatabaseConnection() {
        
    }

    // Método estático para obtener la única instancia de la clase
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void setDatabaseName(String databaseName) {
        DatabaseConnection.databaseName = databaseName;
    }

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() throws Exception {
        if (databaseName == null){
            throw new Exception("The database name is not set");
        }

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;databaseName=" + databaseName + ";encrypt=false;trustServerCertificate=true",
                "sa", "Chino@1234");
    }
}
