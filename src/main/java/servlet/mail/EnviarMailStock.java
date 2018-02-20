package servlet.mail;

import DataBase.DBStock;
import Utilities.Mail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class EnviarMailStock extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();
        DBStock dbStock = new DBStock();
        int cantCARSA = dbStock.getCantidadRegistrosCARSA();
        int cantEMSA = dbStock.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarRegistrosStockSinProcesarCorrectamente(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarRegistrosStockSinProcesarCorrectamente(Empresa.EMSA);
        else
            mail.enviarRegistrosStockSinProcesarCorrectamente(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Stock.html");
        rq.forward(request, response);
    }
}