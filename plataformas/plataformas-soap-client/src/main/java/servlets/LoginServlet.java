package servlets;

import java.io.IOException;

import ar.edu.ubp.soap.ws.AssociationRequestBean;
import ar.edu.ubp.soap.ws.LoginRequestBean;
import ar.edu.ubp.soap.ws.PlataformasWS;
import ar.edu.ubp.soap.ws.PlataformasWSService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PlataformasWSService service = new PlataformasWSService();
			PlataformasWS client = service.getPlataformasWSPort();
            
            LoginRequestBean loginRequest = new LoginRequestBean();
            loginRequest.setEmail(request.getParameter("email"));
            loginRequest.setPassword(request.getParameter("password"));

            String uuid = request.getParameter("uuid");
            System.out.println(loginRequest.toString() + " " + uuid);

            AssociationRequestBean result = client.completeLoginAssociationRequest(loginRequest, uuid);

            System.out.println(result.toString());

            request.setAttribute("id", result.getUserId());

            this.gotoPage("/index.jsp", request, response);
        } catch (WebServiceException ex) {
            response.setStatus(400);
            request.setAttribute("error", ex.getMessage());
            this.gotoPage("/error.jsp", request, response);
        }

        
    }

    private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(address);
		                  dispatcher.forward(request, response);
	}
}