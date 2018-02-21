package servlet.ReportProduct;

import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CantProductosNoProcesados extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String codigoProducto = request.getParameter("codigo");
        String noProcesados;

        if(codigoProducto.equals("false"))
        {
            DBProduct dbProduct = new DBProduct();
            noProcesados = String.valueOf(dbProduct.getCantidadRegistrosNoProcesados());
        }

        else
        {
            HistoricoProductos historicoProductos = new HistoricoProductos();
            noProcesados = String.valueOf(historicoProductos.getCantidadRegistrosNoProcesados(codigoProducto));
        }

        response.getWriter().append(noProcesados);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }
}