package servlet.reporteStock;

import DataBase.DBStock;
import DataBase.Historico.HistoricoStock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantStockTotal extends HttpServlet
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
            DBStock dbStock = new DBStock();
            cantProcesados = String.valueOf(dbStock.getCantidadTotalRegistros());
        }

        else
        {
            HistoricoStock historicoStock = new HistoricoStock();
            cantProcesados = String.valueOf(historicoStock.getCantidadRegistros(codigoProducto));
        }

        response.getWriter().append(cantProcesados);
    }
}