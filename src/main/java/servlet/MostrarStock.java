package servlet;

import DataBase.DBStock;
import DataBase.Filtro;
import DataBase.Historico.HistoricoStock;
import Feed.Stock;
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
public class MostrarStock extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBStock dbStock = new DBStock();
        HistoricoStock historicoStock = new HistoricoStock();

        String codigo = request.getParameter("codigo");
        List<Stock> stockList;

        if(codigo.equals("false"))
            stockList = dbStock.filtrarPor(Filtro.SIN_FILTRAR);

        else
            stockList = historicoStock.getRegistros(codigo);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(stockList);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);
        out.flush();
    }
}
