package servlet.registros.mostrar;

import Feeds.Classification;
import com.fasterxml.jackson.databind.ObjectMapper;
import db.historico.HistoricoClasificacion;
import db.temporal.DBClasificacion;
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
public class MostrarClasificacion extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBClasificacion dbClasificacion = new DBClasificacion();
        HistoricoClasificacion historicoClasificacion = new HistoricoClasificacion();

        String codigo = request.getParameter("codigo");
        List<Classification> listaMerchandise;

        if(codigo.equals("false"))
            listaMerchandise = dbClasificacion.filtrarPor(Filtro.SIN_FILTRAR);

        else
            listaMerchandise = historicoClasificacion.getRegistros(codigo);

        ObjectMapper mapper = new ObjectMapper();

        String jsonString = mapper.writeValueAsString(listaMerchandise);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);

        out.flush();
    }
}
