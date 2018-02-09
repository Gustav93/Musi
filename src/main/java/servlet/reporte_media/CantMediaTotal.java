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
public class CantMediaTotal extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    //Devuelve la cantidad total de registros. Si se pasa como parametro el codigo de procudto devuelve
    //la cantidad total de registros (que tienen el codigo de producto pasado como parametro) que estan
    //en el registro historico. Si no se pasa el codigo de producto devuelve la cantidad total de registros de la
    //db temporal.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String cantTotal;

        if(codigoProducto.equals("false"))
        {
            DBMedia dbmedia = new DBMedia();
            cantTotal = String.valueOf(dbmedia.getCantidadTotalRegistros());
        }

        else
        {
            HistoricoMedia historicoMedia = new HistoricoMedia();
            cantTotal = String.valueOf(historicoMedia.getCantidadRegistros(codigoProducto));
        }

        response.getWriter().append(cantTotal);
    }
}
