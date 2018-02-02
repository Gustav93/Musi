package servlet.consultarHistorico;

import DataBase.Historico.HistoricoPrecios;
import DataBase.Historico.HistoricoProductos;
import DataBase.Historico.HistoricoStock;
import Feed.Price;
import Feed.Product;
import Feed.Stock;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet
public class ConsultarHistorico extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigo = request.getParameter("codigo");
        String feed = request.getParameter("feed");

        if(feed.equals("Productos"))
        {
//            HistoricoProductos historicoProductos = new HistoricoProductos();
//            List<Product> productos = historicoProductos.getProducts(codigo);
//
//            request.setAttribute("listaProductos", productos);

//            RequestDispatcher rq = request.getRequestDispatcher("WEB-INF/jsp/lista_productos.jsp");
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
//            HistoricoStock historicoStock = new HistoricoStock();
//            List<Stock> listaStock = historicoStock.getStock(codigo);
//
//            request.setAttribute("listaStock", listaStock);

//            RequestDispatcher rq = request.getRequestDispatcher("WEB-INF/jsp/lista_stock.jsp");
            RequestDispatcher rq = request.getRequestDispatcher("StockHistorico.html?codigo="+ codigo);

            rq.forward(request, response);
        }
    }
}
