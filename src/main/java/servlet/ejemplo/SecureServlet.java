package servlet.ejemplo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class SecureServlet extends HttpServlet{

    protected boolean validateSession(HttpServletRequest request) {
        String token = (String) request.getSession().getAttribute("token");
        System.out.println(token);
        if(token != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(validateSession(request)) {
            processGet(request, response);
        } else {
            response.sendRedirect("/unathorized.html");
        }
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(validateSession(request)) {
            processPost(request, response);
        } else {
            response.sendRedirect("/unathorized.html");
        }
    }


    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
