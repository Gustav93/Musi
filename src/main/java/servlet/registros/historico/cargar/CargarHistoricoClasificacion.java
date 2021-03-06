package servlet.registros.historico.cargar;

import db.historico.HistoricoClasificacion;
import db.temporal.DBClasificacion;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CargarHistoricoClasificacion extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBClasificacion dbClasificacion = new DBClasificacion();
        HistoricoClasificacion historicoClasificacion = new HistoricoClasificacion();

        historicoClasificacion.crearTabla();
        historicoClasificacion.importarClasificacion();

        dbClasificacion.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }
}
