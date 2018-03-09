package servlet.mail;

import db.temporal.DBPrice;
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
public class EnviarMailPrecios extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();
        DBPrice dbPrice = new DBPrice();
        int cantCARSA = dbPrice.getCantidadRegistrosCARSA();
        int cantEMSA = dbPrice.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarInformePrecios(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarInformePrecios(Empresa.EMSA);

        else
            mail.enviarInformePrecios(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
        rq.forward(request, response);
    }
}
