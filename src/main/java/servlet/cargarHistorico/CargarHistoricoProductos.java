package servlet.cargarHistorico;

import DataBase.DBArchivos;
import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet
public class CargarHistoricoProductos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HistoricoProductos db_historico_productos = new HistoricoProductos();
        DBProduct dbProduct = new DBProduct();

        db_historico_productos.createTable();
        db_historico_productos.importarProductos();


        dbProduct.deleteTable();

        RequestDispatcher rq = request.getRequestDispatcher("Main.html");
        rq.forward(request, response);
    }
}
