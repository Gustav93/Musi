package servlet.reportes.media;

import db.temporal.DBMedia;
import db.historico.HistoricoMedia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantMediaError extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    //Devuelve la cantidad de registros procesados con error. Si se pasa como parametro el codigo de producto devuelve
    //la cantidad de registros procesados (que tienen el codigo de producto pasado como parametro) con error que estan
    //en el registro historico. Si no se pasa el codigo de producto devuelve la cantidad de registros procesados con
    //error de la db temporal.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantError;

        if(codigoProducto.equals("false"))
        {
            DBMedia dbmedia = new DBMedia();
            cantError = String.valueOf(dbmedia.getCantidadRegistrosProcesadosConError());
        }

        else
        {
            HistoricoMedia historicoMedia = new HistoricoMedia();
            cantError = String.valueOf(historicoMedia.getCantidadRegistrosProcesadosConError(codigoProducto));
        }

        response.getWriter().append(cantError);
    }
}
