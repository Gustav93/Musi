package servlet.reportes.merchandise;

import db.historico.HistoricoMerchandise;
import db.temporal.DBMerchandise;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantMerchandiseError extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String totalMerchandise;

        if(codigoProducto.equals("false"))
        {
            DBMerchandise dbMerchandise = new DBMerchandise();
            totalMerchandise = String.valueOf(dbMerchandise.getCantidadRegistrosProcesadosConError());
        }

        else
        {
            HistoricoMerchandise historicoMerchandise = new HistoricoMerchandise();
            totalMerchandise = String.valueOf(historicoMerchandise.getCantidadRegistrosNoProcesadosCorrectamente(codigoProducto));
        }

        response.getWriter().append(totalMerchandise);
    }
}
