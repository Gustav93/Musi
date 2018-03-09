package servlet.registros.historico.cargar;

import db.temporal.DBPrice;
import db.historico.HistoricoPrecios;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoPrecios extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBPrice dbPrice = new DBPrice();
        HistoricoPrecios historicoPrecios= new HistoricoPrecios();

        historicoPrecios.crearTabla();
        historicoPrecios.importarPrecios();

        dbPrice.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }
}