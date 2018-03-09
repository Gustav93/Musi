package servlet.reportes.precios;

import db.temporal.DBPrice;
import db.historico.HistoricoPrecios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantPreciosError extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String totalPrecios;

        if(codigoProducto.equals("false"))
        {
            DBPrice dbPrice = new DBPrice();
            totalPrecios = String.valueOf(dbPrice.getCantidadRegistrosProcesadosConError());
        }

        else
        {
            HistoricoPrecios historicoPrecios = new HistoricoPrecios();
            totalPrecios = String.valueOf(historicoPrecios.getCantidadRegistrosProcesadosConError(codigoProducto));
        }

        response.getWriter().append(totalPrecios);
    }
}
