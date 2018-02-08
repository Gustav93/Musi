package servlet;

import DataBase.DBProduct;
import DataBase.Historico.HistoricoProductos;
import Feed.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet
public class MostrarProductos extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBProduct dbProduct = new DBProduct();
        HistoricoProductos historicoProductos = new HistoricoProductos();

        String codigo = request.getParameter("codigo");
        List<Product> productList;

//        System.out.println(codigo);

        if(codigo.equals("false"))
            productList = dbProduct.getProductList();

        else
            productList = historicoProductos.getProducts(codigo);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(productList);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);

        out.flush();
    }
}
