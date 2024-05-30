package servlets;

import java.io.IOException;

import ar.edu.ubp.soap.ws.AssociationRequestBean;
import ar.edu.ubp.soap.ws.NewPlatformUserBean;
import ar.edu.ubp.soap.ws.PlataformasWS;
import ar.edu.ubp.soap.ws.PlataformasWSService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceException;

/**
 * Servlet implementation class NuevaLocalidadServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			AssociationRequestBean result = client.completeSignupAssociationRequest(newPlatformUser, uuid);

			request.setAttribute("id", result.getUserId());
			this.gotoExternalPage(result.getRedirectUrl(), response);
		} catch (WebServiceException ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
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
