package servlets;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import beans.AssociationRequestBean;
import beans.CompleteLoginAssociationBean;
import beans.ErrorBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CompleteLoginAssociationBean loginRequest = CompleteLoginAssociationBean.builder()
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .uuid(request.getParameter("uuid"))
                .build();

        StringEntity stringEntity = new StringEntity(new Gson().toJson(loginRequest));

        URI uri = URI.create("http://localhost:9120/rest/plataforma/associations/login");

        HttpPost req = new HttpPost();
        req.setURI(uri);
        req.setHeader("Accept", "application/json");
        req.setHeader("Content-type", "application/json");
        req.setEntity(stringEntity);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse resp = client.execute(req);

        HttpEntity responseEntity = resp.getEntity();
        StatusLine responseStatus = resp.getStatusLine();

        String restResp = EntityUtils.toString(responseEntity);
        Gson gson = new Gson();

        if (responseStatus.getStatusCode() != HttpStatus.SC_OK) {
            ErrorBean error = gson.fromJson(restResp, new TypeToken<ErrorBean>() {
            }.getType());

            response.setStatus(400);
            request.setAttribute("error", error.getMessage());
            this.gotoPage("/error.jsp", request, response);
        } else {
            AssociationRequestBean associationRequest = gson.fromJson(restResp, AssociationRequestBean.class);
            int userId = associationRequest.getUserId();

            request.setAttribute("id", userId);

            this.gotoExternalPage(associationRequest.getRedirectUrl(), response);
        }

    }

    private void gotoPage(String address, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    private void gotoExternalPage(String address, HttpServletResponse response)
            throws IOException {
        response.sendRedirect(address);
    }
}