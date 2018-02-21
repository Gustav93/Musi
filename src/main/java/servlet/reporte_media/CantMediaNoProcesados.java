package servlet.reporte_media;

import DataBase.DBMedia;
import DataBase.Historico.HistoricoMedia;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantMediaNoProcesados extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    //Devuelve la cantidad de registros no procesados. Si se pasa como parametro el codigo de producto devuelve
    //la cantidad de registros no procesados (que tienen el codigo de producto pasado como parametro) que estan
    //en el registro historico. Si no se pasa el codigo de producto devuelve la cantidad de registros no procesados
    //de la db temporal.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantNoProcesados;

        if(codigoProducto.equals("false"))
        {
            DBMedia dbmedia = new DBMedia();
            cantNoProcesados = String.valueOf(dbmedia.getCantidadRegistrosNoProcesados());
        }

        else
        {
            HistoricoMedia historicoMedia = new HistoricoMedia();
            cantNoProcesados = String.valueOf(historicoMedia.getCantidadRegistrosNoProcesados(codigoProducto));
        }

        response.getWriter().append(cantNoProcesados);
    }
}
