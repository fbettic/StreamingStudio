package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.cxf.interceptor.Fault;

public class AuthManager {
    private DatabaseConnection databaseConnection;

    public AuthManager(DatabaseConnection databaseConnection){
        this.databaseConnection = databaseConnection;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        return databaseConnection.getConnection();
    }

    private Integer getServiceId(String authToken) throws Fault {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL GetServiceConnectionByToken(?)}")) {

            stmt.setString(1, authToken);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("serviceId");
                } else {
                    throw new Fault(new Exception("Invalid platform token"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    private Integer getUserId(String userToken) throws Fault {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL GetUserByToken(?)}")) {

            stmt.setString(1, userToken);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("userId");
                } else {
                    throw new Fault(new Exception("Invalid user token"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public Integer validateServiceToken(String authToken) throws Fault {
        return getServiceId(authToken);
    }

    public Integer validateUserToken(String userToken) throws Fault {
        return getUserId(userToken);
    }
}
