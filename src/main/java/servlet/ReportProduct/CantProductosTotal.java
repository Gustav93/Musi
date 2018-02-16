package servlet.ReportProduct;

import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class CantProductosTotal extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String totalProductos;

        if(codigoProducto.equals("false"))
        {
            DBProduct dbProduct = new DBProduct();
            totalProductos = String.valueOf(dbProduct.getCantidadTotalRegistros());
        }

        else
        {
            HistoricoProductos historicoProductos = new HistoricoProductos();

            totalProductos = String.valueOf(historicoProductos.getCantidadRegistros(codigoProducto));
        }

        response.getWriter().append(totalProductos);
    }
}
