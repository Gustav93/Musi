package servlet;

import DataBase.DBStock;
import DataBase.Historico.HistoricoStock;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoStock extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBStock dbStock = new DBStock();
        HistoricoStock historicoStock = new HistoricoStock();

        historicoStock.createTable();
        historicoStock.importarStock();
        dbStock.deleteTable();

        RequestDispatcher rq = request.getRequestDispatcher("Main.html");
        rq.forward(request,response);
    }
}
