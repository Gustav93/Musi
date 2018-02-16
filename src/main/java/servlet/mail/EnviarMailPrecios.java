package servlet.mail;

import DataBase.DBPrice;
import Utilities.Mail;

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
//        Mail mail = new Mail();
//
//        mail.enviarRegistrosPreciosSinProcesarCorrectamente("cbaez@musi.com.ar");
//
//        RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
//        rq.forward(request, response);

        Mail mail = new Mail();
        DBPrice dbPrice = new DBPrice();
        int cantCARSA = dbPrice.getCantidadRegistrosCARSA();
        int cantEMSA = dbPrice.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
//            mail.enviarRegistrosPreciosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosPreciosSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");

        else if(cantCARSA <= 0 && cantEMSA > 0)
//            mail.enviarRegistrosPreciosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosPreciosSinProcesarCorrectamente("vizaral2@gmail.com");
        else
//            mail.enviarRegistrosPreciosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosPreciosSinProcesarCorrectamente("gsanchez@musi.com.ar");

        RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
        rq.forward(request, response);
    }
}
