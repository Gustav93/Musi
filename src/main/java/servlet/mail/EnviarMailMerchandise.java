package servlet.mail;

import db.temporal.DBMerchandise;
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
public class EnviarMailMerchandise extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();
        DBMerchandise dbMerchandise = new DBMerchandise();
        int cantCARSA = dbMerchandise.getCantidadRegistrosCARSA();
        int cantEMSA = dbMerchandise.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarInformeMerchandise(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarInformeMerchandise(Empresa.EMSA);

        else
            mail.enviarInformeMerchandise(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Merchandise.html");
        rq.forward(request, response);
    }
}
