package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.cxf.interceptor.Fault;

public class ReportManager {
    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    public String createWeeklyReport(String reportJson) throws Fault {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL CreateWeeklyReportFromJson(?)}")) {

            stmt.setString(1, reportJson);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    System.out.println(result.toString());

                    System.out.println(result.getString("response"));
                    
                    return result.getString("response");
                } else {
                    throw new Fault(new Exception("Invalid user token"));
                }
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }
}
