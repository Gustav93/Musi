package servlet.registros.historico.consultar;

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
        //recibo el codigo de producto y el feed en donde quiero consultar
        String codigo = request.getParameter("codigo");
        String feed = request.getParameter("feed");

        //redirecciono a la pag del feed que se quiere consultar. Ademas se pasa el codigo de producto en la url para
        //luego poder hacer la consulta y mostrarla.
        if(feed.equals("Productos"))
        {
            RequestDispatcher rq = request.getRequestDispatcher("ProductosHistoricos.html?codigo="+ codigo);
            rq.forward(request, response);
        }

        else if(feed.equals("Precios"))
        {
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
