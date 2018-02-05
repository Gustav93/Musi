package servlet.ejemplo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
@SuppressWarnings("serial")
public class LogoutServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    // you can also make an authenticated request to logout, but here we choose to
    // simply delete the session variables for simplicity
    HttpSession session =  req.getSession(false);
    String token = (String) req.getSession().getAttribute("token");
    resp.setContentType("application/x-www-form-urlencoded");
    resp.sendRedirect("https://accounts.google.com/o/oauth2/revoke?token=" + token);
    if (session != null) {
      session.invalidate();
    }
    // rebuild session
//    req.getSession();
  }
}