package servlet.registros.mostrar;


import utilidades.enums.Filtro;
import db.historico.HistoricoPrecios;
import db.temporal.DBPrice;
import Feeds.Price;
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
public class MostrarPrecios extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBPrice dbPrice = new DBPrice();
        HistoricoPrecios historicoPrecios = new HistoricoPrecios();

        String codigo = request.getParameter("codigo");
        List<Price> priceList;

        if(codigo.equals("false"))
            priceList = dbPrice.filtrarPor(Filtro.SIN_FILTRAR);

        else
            priceList = historicoPrecios.getRegistros(codigo);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(priceList);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);

        out.flush();
    }
}