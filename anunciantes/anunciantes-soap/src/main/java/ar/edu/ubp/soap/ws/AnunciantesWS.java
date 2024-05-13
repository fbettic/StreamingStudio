package ar.edu.ubp.soap.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.Fault;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.soap.beans.AdvertisingBean;
import ar.edu.ubp.soap.beans.BannerBean;
import ar.edu.ubp.soap.beans.WeeklyReportBean;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService()
@XmlSeeAlso({})
public class AnunciantesWS {

    private String databaseName;

    public AnunciantesWS(String databaseName) {
        this.databaseName = databaseName;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost;databaseName=" + databaseName
                        + ";encrypt=false;trustServerCertificate=true",
                "sa", "Chino@1234");
    }

    private Integer getServiceId(String authToken) throws Fault {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL GetServiceConnectionByToken(?)}")) {
            stmt.setString(1, authToken);
            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("serviceId");
                } else {
                    throw new Fault(new Exception("Invalid token"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    @WebMethod()
    @WebResult(name = "banner")
    public BannerBean getBannerById(@WebParam(name = "bannerId") String bannerId,
            @WebParam(name = "authToken") String authToken) throws Fault {
        Integer serviceId = getServiceId(authToken);
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }

        try {
            Connection conn = getConnection();
            PreparedStatement stmt;
            ResultSet result;
            BannerBean banner = new BannerBean();

            conn.setAutoCommit(true);

            String query = "{CALL dbo.GetBannerById(?)}";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, bannerId);

            result = stmt.executeQuery();

            while (result.next()) {
                banner.setBannerId(result.getInt("bannerId"));
                banner.setText(result.getString("text"));
                banner.setImageUrl(result.getString("imageUrl"));
                banner.setRedirectUrl(result.getString("redirectUrl"));
            }

            stmt.close();
            conn.close();

            return banner;
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    @WebMethod()
    @WebResult(name = "advertising")
    public AdvertisingBean getAdvertisingById(@WebParam(name = "advertisingId") String advertisingId,
            @WebParam(name = "authToken") String authToken) throws Fault {
        Integer serviceId = getServiceId(authToken);
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }

        try {
            Connection conn = getConnection();
            PreparedStatement stmt;
            ResultSet result;
            AdvertisingBean advertising = new AdvertisingBean();

            conn.setAutoCommit(true);

            String query = "{CALL dbo.GetAdvertisingById(?)}";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, advertisingId);

            result = stmt.executeQuery();

            while (result.next()) {
                advertising.setAdvertisingId(result.getInt("advertisingId"));
                advertising.setBannerId(result.getInt("bannerId"));
                advertising.setText(result.getString("text"));
                advertising.setImageUrl(result.getString("imageUrl"));
                advertising.setRedirectUrl(result.getString("redirectUrl"));
                advertising.setServiceId(result.getInt("serviceId"));
                advertising.setPriorityTitle(result.getString("priorityTitle"));
                advertising.setFromDate(result.getDate("fromDate"));
                advertising.setToDate(result.getDate("toDate"));
            }

            stmt.close();
            conn.close();

            return advertising;
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    @WebMethod()
    @WebResult(name = "advertisings")
    public List<AdvertisingBean> getAllAdvertisings(@WebParam(name = "authToken") String authToken) throws Fault {
        final Integer serviceId = getServiceId(authToken);
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL dbo.GetAllAdvertisings(?)}")) {
            stmt.setInt(1, serviceId);
            try (ResultSet result = stmt.executeQuery()) {
                List<AdvertisingBean> advertisings = new ArrayList<>();
                while (result.next()) {
                    AdvertisingBean advertising = new AdvertisingBean();
                    advertising.setAdvertisingId(result.getInt("advertisingId"));
                    advertising.setBannerId(result.getInt("bannerId"));
                    advertising.setText(result.getString("text"));
                    advertising.setImageUrl(result.getString("imageUrl"));
                    advertising.setRedirectUrl(result.getString("redirectUrl"));
                    advertising.setServiceId(result.getInt("serviceId"));
                    advertising.setPriorityTitle(result.getString("priorityTitle"));
                    advertising.setFromDate(result.getDate("fromDate"));
                    advertising.setToDate(result.getDate("toDate"));
                    advertisings.add(advertising);
                }
                return advertisings;
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
    }

    public String createWeeklyReport(String reportJson) throws Fault {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL CreateWeeklyReportFromJson(?)}")) {

            stmt.setString(1, reportJson);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getString("result");
                } else {
                    throw new Fault(new Exception("Invalid user token"));
                }
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    @WebMethod()
    @WebResult(name = "result")
    public String receiveWeeklyReport(@WebParam(name = "report") WeeklyReportBean report) throws Exception {
        final Integer serviceId = getServiceId(report.getAuthToken());

        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            throw e;
        }

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement("{CALL CreateWeeklyReportFromJson(?)}")) {

            stmt.setString(1, jsonString);

            try (ResultSet result = stmt.executeQuery()) {
                if (result.next()) {
                    return result.getString("result");
                } else {
                    throw new Fault(new Exception("Invalid user token"));
                }
            }
        } catch (Exception e) {
            throw new Fault(e);
        }
    }

    @WebMethod()
    @WebResult(name = "pong")
    public String ping(@WebParam(name = "authToken") String authToken) throws Fault {
        Integer serviceId = getServiceId(authToken);
        if (serviceId == null || serviceId == 0) {
            throw new Fault(new Exception("Invalid token"));
        }
        return "pong";
    }
}
