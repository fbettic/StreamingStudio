package servlets;

import java.io.IOException;

import jakarta.xml.ws.WebServiceException;
import ar.edu.ubp.soap.ws.AssociationRequestBean;
import ar.edu.ubp.soap.ws.NewPlatformUserBean;
import ar.edu.ubp.soap.ws.PlataformasWS;
import ar.edu.ubp.soap.ws.PlataformasWSService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NuevaLocalidadServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PlataformasWSService service = new PlataformasWSService();
			PlataformasWS client = service.getPlataformasWSPort();
				
			NewPlatformUserBean newPlatformUser = new NewPlatformUserBean();
            newPlatformUser.setFirstname(request.getParameter("firstname"));
            newPlatformUser.setLastname(request.getParameter("lastname"));
            newPlatformUser.setEmail(request.getParameter("email"));
            newPlatformUser.setPassword(request.getParameter("password"));
            newPlatformUser.setPhone(request.getParameter("phone"));

            
			String uuid = request.getParameter("uuid");
			System.out.println(newPlatformUser.toString() + " " + uuid);

			AssociationRequestBean result = client.completeSignupAssociationRequest(newPlatformUser, uuid);

			System.out.println(result.toString());

			request.setAttribute("id", result.getUserId());
            this.gotoPage("/index.jsp", request, response);
		} 
		catch (WebServiceException ex) {
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
