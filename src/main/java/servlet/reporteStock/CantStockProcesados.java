package servlet.reporteStock;

import DataBase.DBPrice;
import DataBase.DBStock;
import DataBase.Historico.HistoricoPrecios;
import DataBase.Historico.HistoricoStock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantStockProcesados extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        DBStock dbStock = new DBStock();
//        response.getWriter().append(String.valueOf(dbStock.getNumberProcessed()));

        String codigoProducto = request.getParameter("codigo");
        String cantProcesados;

        if(codigoProducto.equals("false"))
        {
            DBStock dbStock = new DBStock();
            cantProcesados = String.valueOf(dbStock.getNumberProcessed());
        }

        else
        {
            HistoricoStock historicoStock = new HistoricoStock();

            cantProcesados = String.valueOf(historicoStock.getNumberProcessed(codigoProducto));
        }

        response.getWriter().append(cantProcesados);
    }
}
