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
public class CantClasificacionNoProcesado extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantNoProcesados;

        if(codigoProducto.equals("false"))
        {
            DBClasificacion dbmedia = new DBClasificacion();
            cantNoProcesados = String.valueOf(dbmedia.getCantidadRegistrosNoProcesados());
        }

        else
        {
            HistoricoClasificacion historicoClasificacion = new HistoricoClasificacion();
            cantNoProcesados = String.valueOf(historicoClasificacion.getCantidadRegistrosNoProcesados(codigoProducto));
        }

        response.getWriter().append(cantNoProcesados);
    }
}
