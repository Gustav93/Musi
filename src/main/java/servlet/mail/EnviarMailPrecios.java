package servlet.mail;

import Utilities.Mail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class EnviarMailPrecios extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Mail mail = new Mail();

        mail.sendPriceFeed("gustavsanchez@yahoo.com.ar");

        RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
        rq.forward(request, response);

    }
}
