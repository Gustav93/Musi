package servlet;

import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoProductos extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBProduct dbProduct = new DBProduct();
        HistoricoProductos db_historico_productos = new HistoricoProductos();

        db_historico_productos.crearTabla();
        db_historico_productos.importarProductos();

        dbProduct.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }
}
