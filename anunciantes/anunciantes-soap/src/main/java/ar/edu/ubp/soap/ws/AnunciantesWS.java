package ar.edu.ubp.soap.ws;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.cxf.interceptor.Fault;

import ar.edu.ubp.soap.beans.BannerBean;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;

@WebService()
@XmlSeeAlso({})
public class AnunciantesWS {

    @WebMethod()
    @WebResult(name = "bannerId")
    public BannerBean getBanner(@WebParam(name = "bannerId") String bannerId) throws Fault {
        try{
            System.out.println("-----------------------> "+ bannerId +" <-----------------------");
            Connection conn;
			PreparedStatement stmt;
			ResultSet result;
			BannerBean banner = new BannerBean();
	
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=anunciante1_db;encrypt=false;trustServerCertificate=true", "sa", "Chino@1234");
            conn.setAutoCommit(true);

            String query = "SELECT * FROM Banner WHERE bannerId = ?";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, bannerId);

            result = stmt.executeQuery();

            while(result.next()) {
				banner.setBannerId(result.getInt("bannerId"));
				banner.setText(result.getString("text"));
				banner.setImageUrl(result.getString("imageUrl"));
                banner.setRedirectUrl(result.getString("redirectUrl"));
			}

            stmt.close();
			conn.close();

            return banner;
        }catch (ClassNotFoundException | SQLException e) {
            throw new Fault(e);
        }
        
    }

    @WebMethod()
    @WebResult(name = "test")
    public String getTest() {
        return "Hello World!! :D";
    }
}
