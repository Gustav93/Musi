package servlet.reportePrecios;

import DataBase.DBPrice;
import DataBase.Historico.HistoricoPrecios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantPreciosProcesados extends HttpServlet
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
            DBPrice dbPrice = new DBPrice();
            cantProcesados = String.valueOf(dbPrice.getCantidadRegistrosProcesados());
        }

        else
        {
            HistoricoPrecios historicoPrecios = new HistoricoPrecios();
            cantProcesados = String.valueOf(historicoPrecios.getCantidadRegistrosProcesados(codigoProducto));
        }

        response.getWriter().append(cantProcesados);
    }
}
