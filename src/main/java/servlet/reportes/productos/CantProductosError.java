package servlet.reportes.productos;

import db.temporal.DBProduct;
import db.historico.HistoricoProductos;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CantProductosError extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
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
            cantProductosError = String.valueOf(historicoProductos.getCantidadRegistrosNoProcesadosCorrectamente(codigoProducto));
        }

        response.getWriter().append(cantProductosError);
    }
}