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
//        si el codigo es distinto de false es porque voy a mostrar la cantidad de productos con el mismo codigo que no
//        fueron procesados.

        String codigoProducto = request.getParameter("codigo");
        String noProcesados;

        if(codigoProducto.equals("false"))
        {
            DBProduct dbProduct = new DBProduct();
            noProcesados = String.valueOf(dbProduct.getCantidadRegistrosNoProcesados());
            System.out.println(dbProduct.getCantidadRegistrosNoProcesados());
        }

        else
        {
            HistoricoProductos historicoProductos = new HistoricoProductos();

            noProcesados = String.valueOf(historicoProductos.getNumberNotProcessed(codigoProducto));
        }

        response.getWriter().append(noProcesados);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
