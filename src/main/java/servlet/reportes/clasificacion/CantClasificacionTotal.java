package servlet.reportes.clasificacion;

import db.historico.HistoricoClasificacion;
import db.temporal.DBClasificacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantClasificacionTotal extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantTotal;

        if(codigoProducto.equals("false"))
        {
            DBClasificacion dbmedia = new DBClasificacion();
            cantTotal = String.valueOf(dbmedia.getCantidadTotalRegistros());
        }

        else
        {
            HistoricoClasificacion historicoClasificacion = new HistoricoClasificacion();
            cantTotal = String.valueOf(historicoClasificacion.getCantidadRegistros(codigoProducto));
        }

        response.getWriter().append(cantTotal);
    }
}
