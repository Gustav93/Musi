package servlet.mail;

import db.temporal.DBMedia;
import utilidades.Mail;
import utilidades.enums.Empresa;

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
        DBMedia dbMedia = new DBMedia();
        int cantCARSA = dbMedia.getCantidadRegistrosCARSA();
        int cantEMSA = dbMedia.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarInformeMedia(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarInformeMedia(Empresa.EMSA);

        else
            mail.enviarInformeMedia(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Media.html");
        rq.forward(request, response);
    }
}