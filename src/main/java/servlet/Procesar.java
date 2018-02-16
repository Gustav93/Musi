package servlet;

import DataBase.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class Procesar extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBManager dbManager = new DBManager();
        String feed = request.getParameter("Feed");

        if(feed.equals("Productos"))
        {
            dbManager.verificarProductos();
            RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
            rq.forward(request, response);
        }


        else if(feed.equals("Precios"))
        {
            dbManager.verificarPrecios();
            RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
            rq.forward(request, response);
        }


        else if (feed.equals("Stock"))
        {
            dbManager.verificarStock();
            RequestDispatcher rq = request.getRequestDispatcher("Stock.html");
            rq.forward(request, response);
        }

        else if(feed.equals("Media"))
        {
            dbManager.verificarMedia();
            RequestDispatcher rq = request.getRequestDispatcher("Media.html");
            rq.forward(request, response);
        }

//


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
