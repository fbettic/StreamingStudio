package ar.edu.ubp.soap.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.soap.beans.AdvertisingBean;
import ar.edu.ubp.soap.beans.BannerBean;

public class AdvertisingManager {
    private Connection getConnection() throws Exception {
        return DatabaseConnection.getConnection();
    }

    public BannerBean getBannerById(String bannerId) throws Exception {
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

    public AdvertisingBean getAdvertisingById(String advertisingId) throws Exception {
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

    public List<AdvertisingBean> getAllAdvertisings(Integer serviceId) throws Exception {
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
}
