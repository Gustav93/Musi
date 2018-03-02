package servlet;

import DataBase.DBAudit;
import auditoria.RegistroAuditoria;
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
public class MostrarAuditoria extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    }

    //devuelvo un json con los registros de la auditoria
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBAudit dbAudit = new DBAudit();
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        List<RegistroAuditoria> itemList = dbAudit.getListaAuditoria(); //me traigo la lista de registros de la auditoria
        String jsonString = mapper.writeValueAsString(itemList); //lo convierto en un json

        response.setContentType("application/json"); //indico que voy a devolver un json

        out.print(jsonString); //escribo el json en el response
        out.flush();
    }
}