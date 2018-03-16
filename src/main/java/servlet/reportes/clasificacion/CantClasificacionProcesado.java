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
public class CantClasificacionProcesado extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantProcesados;

        if(codigoProducto.equals("false"))
        {
            DBClasificacion dbmedia = new DBClasificacion();
            cantProcesados = String.valueOf(dbmedia.getCantidadRegistrosProcesados());
        }

        else
        {
            HistoricoClasificacion historicoClasificacion = new HistoricoClasificacion();
            cantProcesados = String.valueOf(historicoClasificacion.getCantidadRegistrosProcesados(codigoProducto));
        }

        response.getWriter().append(cantProcesados);
    }
}
