package servlet.ReportProduct;

import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CantProductosError extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        DBProduct dbProduct = new DBProduct();
//        response.getWriter().append(String.valueOf(dbProduct.getCantidadRegistrosProcesadosConError()));

        String codigoProducto = request.getParameter("codigo");
        String cantProductosError;

        if(codigoProducto.equals("false"))
        {
            DBProduct dbProduct = new DBProduct();
            cantProductosError = String.valueOf(dbProduct.getCantidadRegistrosProcesadosConError());
        }

        else
        {
            HistoricoProductos historicoProductos = new HistoricoProductos();

            cantProductosError = String.valueOf(historicoProductos.getCantidadRegistrosProcesadosConError(codigoProducto));
        }

        response.getWriter().append(cantProductosError);

    }
}
