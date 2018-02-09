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
public class EnviarMailMedia extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();

        mail.enviarRegistrosMediaSinProcesarCorrectamente("cbaez@musi.com.ar");

        RequestDispatcher rq = request.getRequestDispatcher("Media.html");
        rq.forward(request, response);
    }
}
