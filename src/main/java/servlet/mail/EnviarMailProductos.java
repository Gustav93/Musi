package servlet.mail;

import DataBase.DBProduct;
import Utilities.Mail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class EnviarMailProductos extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();
        DBProduct dbProduct = new DBProduct();
        int cantCARSA = dbProduct.getCantidadRegistrosCARSA();
        int cantEMSA = dbProduct.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
//            mail.enviarRegistrosProductosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosProductosSinProcesarCorrectamente("gustavsanchez@yahoo.com.ar");

        else if(cantCARSA <= 0 && cantEMSA > 0)
//            mail.enviarRegistrosProductosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosProductosSinProcesarCorrectamente("vizaral2@gmail.com");
        else
//            mail.enviarRegistrosProductosSinProcesarCorrectamente("cbaez@musi.com.ar");
            mail.enviarRegistrosProductosSinProcesarCorrectamente("gsanchez@musi.com.ar");

        RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
        rq.forward(request, response);
    }
}
