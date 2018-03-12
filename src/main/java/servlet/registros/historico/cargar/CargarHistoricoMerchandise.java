package servlet.registros.historico.cargar;

import db.historico.HistoricoMerchandise;
import db.temporal.DBMerchandise;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoMerchandise extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBMerchandise dbMerchandise = new DBMerchandise();
        HistoricoMerchandise historicoMerchandise= new HistoricoMerchandise();

        historicoMerchandise.crearTabla();
        historicoMerchandise.importarMerchandise();

        dbMerchandise.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }
}
