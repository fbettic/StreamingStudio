package servlets;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.AssociationRequestBean;
import beans.CompleteSignupAssociationBean;
import beans.ErrorBean;
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
    private static final URI SIGNUP_URI = URI.create("http://localhost:9130/rest/plataforma/associations/signup");
    private static final Gson gson = new Gson();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CompleteSignupAssociationBean signupRequest = new CompleteSignupAssociationBean(request.getParameterMap());
        StringEntity stringEntity = new StringEntity(gson.toJson(signupRequest));

        HttpResponse resp = handleRequest(stringEntity);
        handleResponse(resp, request, response);
    }

    private HttpResponse handleRequest(StringEntity entity) throws ClientProtocolException, IOException {
        HttpPost req = new HttpPost(SIGNUP_URI);
        req.setHeader("Accept", "application/json");
        req.setHeader("Content-type", "application/json");
        req.setEntity(entity);

        HttpClient client = HttpClientBuilder.create().build();
        return client.execute(req);
    }

    private void handleResponse(HttpResponse resp, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpEntity responseEntity = resp.getEntity();
        StatusLine responseStatus = resp.getStatusLine();

        String restResp = EntityUtils.toString(responseEntity);

        try {
            if (responseStatus.getStatusCode() != HttpStatus.SC_OK) {
                ErrorBean error = gson.fromJson(restResp, new TypeToken<ErrorBean>() {
                }.getType());
                response.setStatus(400);
                request.setAttribute("error", error.getMessage());
                request.getRequestDispatcher("/signup.jsp").forward(request, response);
            } else {

                AssociationRequestBean associationRequest = gson.fromJson(restResp, AssociationRequestBean.class);
                gotoExternalPage(associationRequest.getRedirectUrl(), response);

            }
        } catch (Exception e) {
            request.setAttribute("error", "Internal server error. Please try again later.");
            request.getRequestDispatcher("/signup.jsp").forward(request, response);
        }
    }

    private void gotoExternalPage(String address, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(address);
    }

}
