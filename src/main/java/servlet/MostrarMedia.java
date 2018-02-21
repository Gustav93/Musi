package servlet;

import DataBase.DBMedia;
import DataBase.Filtro;
import DataBase.Historico.HistoricoMedia;
import Feed.Media;
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
public class MostrarMedia extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //obtengo los registros de media o de la db historica dependiendo de que si se envia el codigo de producto.
        //si se envia el codigo, significa que va a enviar una lista de registros de la base de datos historica, de lo
        //contrario, se va a enviar la lista de registros de la db temporal. La lista se envia via JSON.
        DBMedia dbMedia = new DBMedia();
        HistoricoMedia historicoMedia = new HistoricoMedia();

        String codigo = request.getParameter("codigo");
        List<Media> mediaList;

        if(codigo.equals("false"))
            mediaList = dbMedia.filtrarPor(Filtro.SIN_FILTRAR);

        else
            mediaList = historicoMedia.getRegistros(codigo);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(mediaList);

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.print(jsonString);

        out.flush();
    }
}