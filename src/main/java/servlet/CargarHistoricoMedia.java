package servlet;

import DataBase.DBMedia;
import DataBase.Historico.HistoricoMedia;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoMedia extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    //se copia el contenido de la tabla temporal al registro historico. Luego se la elimina
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBMedia dbMedia = new DBMedia();
        HistoricoMedia historicoMedia = new HistoricoMedia();

        historicoMedia.crearTabla();
        historicoMedia.importarMedia();

        dbMedia.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }
}
