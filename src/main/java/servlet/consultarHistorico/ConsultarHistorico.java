package servlet.consultarHistorico;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class ConsultarHistorico extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigo = request.getParameter("codigo");
        String feed = request.getParameter("feed");

        if(feed.equals("Productos"))
        {
            RequestDispatcher rq = request.getRequestDispatcher("ProductosHistoricos.html?codigo="+ codigo);
            rq.forward(request, response);
        }

        else if(feed.equals("Precios"))
        {
//            HistoricoPrecios historicoPrecios = new HistoricoPrecios();
//            List<Price> precios = historicoPrecios.getPrice(codigo);
//
//            request.setAttribute("listaPrecios", precios);

//            RequestDispatcher rq = request.getRequestDispatcher("WEB-INF/jsp/lista_precios.jsp");
            RequestDispatcher rq = request.getRequestDispatcher("PreciosHistoricos.html?codigo="+ codigo);
            rq.forward(request, response);
        }

        else if(feed.equals("Stock"))
        {
            RequestDispatcher rq = request.getRequestDispatcher("StockHistorico.html?codigo="+ codigo);
            rq.forward(request, response);
        }

        else if(feed.equals("Media"))
        {
            RequestDispatcher rq = request.getRequestDispatcher("MediaHistorico.html?codigo="+ codigo);
            rq.forward(request, response);
        }
    }
}
