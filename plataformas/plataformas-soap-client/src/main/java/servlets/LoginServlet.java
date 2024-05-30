package servlets;

import java.io.IOException;

import ar.edu.ubp.soap.ws.AssociationRequestBean;
import ar.edu.ubp.soap.ws.LoginRequestBean;
import ar.edu.ubp.soap.ws.PlataformasWS;
import ar.edu.ubp.soap.ws.PlataformasWSService;
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

            AssociationRequestBean result = client.completeLoginAssociationRequest(loginRequest, uuid);

            this.gotoExternalPage(result.getRedirectUrl(), response);
        } catch (WebServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    /*
     * 
     * private void gotoPage(String address, HttpServletRequest request,
     * HttpServletResponse response)
     * throws ServletException, IOException {
     * RequestDispatcher dispatcher =
     * this.getServletContext().getRequestDispatcher(address);
     * dispatcher.forward(request, response);
     * }
     */

    private void gotoExternalPage(String address, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(address);
    }
}