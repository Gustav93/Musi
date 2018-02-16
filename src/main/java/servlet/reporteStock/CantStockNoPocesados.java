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
public class CantStockNoPocesados extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        DBStock dbStock = new DBStock();
//        response.getWriter().append(String.valueOf(dbStock.getCantidadRegistrosNoProcesados()));

        String codigoProducto = request.getParameter("codigo");
        String cantNoProcesados;

        if(codigoProducto.equals("false"))
        {
            DBStock dbStock = new DBStock();
            cantNoProcesados = String.valueOf(dbStock.getCantidadRegistrosNoProcesados());
        }

        else
        {
            HistoricoStock historicoStock = new HistoricoStock();

            cantNoProcesados = String.valueOf(historicoStock.getNumberNotProcessed(codigoProducto));
        }

        response.getWriter().append(cantNoProcesados);
    }
}
