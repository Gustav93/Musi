package servlet.registros.mostrar;

import Feeds.Merchandise;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.historico.HistoricoMerchandise;
import db.temporal.DBMerchandise;
import utilidades.enums.Filtro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet
public class MostrarMerchandise extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBMerchandise dbMerchandise = new DBMerchandise();
        HistoricoMerchandise historicoMerchandise = new HistoricoMerchandise();

        String codigo = request.getParameter("codigo");
        List<Merchandise> listaMerchandise;

        if(codigo.equals("false"))
            listaMerchandise = dbMerchandise.filtrarPor(Filtro.SIN_FILTRAR);

        else
            listaMerchandise = historicoMerchandise.getRegistros(codigo);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(listaMerchandise);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);

        out.flush();
    }
}
