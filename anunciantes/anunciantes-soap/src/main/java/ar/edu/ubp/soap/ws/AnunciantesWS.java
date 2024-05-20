package ar.edu.ubp.soap.ws;

import java.util.List;

import org.apache.cxf.interceptor.Fault;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.ubp.soap.beans.AdvertisingBean;
import ar.edu.ubp.soap.beans.BannerBean;
import ar.edu.ubp.soap.beans.BasicResponseBean;
import ar.edu.ubp.soap.beans.WeeklyReportBean;
import ar.edu.ubp.soap.db.AdvertisingManager;
import ar.edu.ubp.soap.db.AuthManager;
import ar.edu.ubp.soap.db.ReportManager;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService()
@XmlSeeAlso({})
public class AnunciantesWS {
    private AuthManager authManager;
    private ReportManager reportManager;
    private AdvertisingManager advertisingManager;

    public AnunciantesWS() {
        this.authManager = new AuthManager();
        this.reportManager = new ReportManager();
        this.advertisingManager = new AdvertisingManager();
    }

    @WebMethod()
    @WebResult(name = "banner")
    public BannerBean getBannerById(@WebParam(name = "bannerId") String bannerId,
            @WebParam(name = "authToken") String authToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return advertisingManager.getBannerById(bannerId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error retrieving banner by ID: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "advertising")
    public AdvertisingBean getAdvertisingById(@WebParam(name = "advertisingId") String advertisingId,
            @WebParam(name = "authToken") String authToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return advertisingManager.getAdvertisingById(advertisingId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error retrieving advertising by ID: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "advertisings")
    public List<AdvertisingBean> getAllAdvertisings(@WebParam(name = "authToken") String authToken) {
        try {
            final Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return advertisingManager.getAllAdvertisings(serviceId);
        } catch (Exception e) {
            throw new Fault(new Exception("Error retrieving all advertisings: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "report")
    public BasicResponseBean receiveWeeklyReport(@WebParam(name = "report") WeeklyReportBean report) {
        try {
            final Integer serviceId = authManager.validateServiceToken(report.getAuthToken());
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = objectMapper.writeValueAsString(report);
            } catch (JsonProcessingException e) {
                throw new Fault(new Exception("Error processing weekly report JSON: " + e.getMessage()));
            }
            return new BasicResponseBean(reportManager.createWeeklyReport(jsonString));
        } catch (Exception e) {
            throw new Fault(new Exception("Error receiving weekly report: " + e.getMessage()));
        }
    }

    @WebMethod()
    @WebResult(name = "pong")
    public BasicResponseBean ping(@WebParam(name = "authToken") String authToken) {
        try {
            Integer serviceId = authManager.validateServiceToken(authToken);
            if (serviceId == null || serviceId == 0) {
                throw new Fault(new Exception("Invalid token"));
            }
            return new BasicResponseBean("pong");
        } catch (Exception e) {
            throw new Fault(new Exception("Error pinging service: " + e.getMessage()));
        }
    }
}
