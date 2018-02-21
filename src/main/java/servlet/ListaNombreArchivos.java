package servlet;

import DataBase.DBArchivos;
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
public class ListaNombreArchivos extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        DBArchivos dbArchivos = new DBArchivos();
//        ObjectMapper mapper = new ObjectMapper();
//        List<String> nombreArchivos = dbArchivos.getNombreArchivos();
//
//        String jsonString = mapper.writeValueAsString(nombreArchivos);
//
//        response.setContentType("application/json");
//
//        PrintWriter out = response.getWriter();
//        out.print(jsonString);
//        out.flush();
    }
}
