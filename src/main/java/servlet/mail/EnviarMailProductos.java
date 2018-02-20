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

        //se va a eviar el mail a la respectiva empresa si hay por lo menos 1 registro que sea de EMSA o CARSA, de lo
        //contrario lo deberia enviar a otro mail (no a las empresas).
        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarRegistrosProductosSinProcesarCorrectamente(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarRegistrosProductosSinProcesarCorrectamente(Empresa.EMSA);

        else
            mail.enviarRegistrosProductosSinProcesarCorrectamente(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
        rq.forward(request, response);
    }
}
