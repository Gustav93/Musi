package servlet.registros.descargar;

import csv.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet
public class DescargarMerchandise extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        Writer writer = new Writer();
        File archivo = writer.getCsvMerchandiseProcesadoCorrectamente();
        String nombreArchivo = archivo.getName();

        response.setContentType("fileType");
        response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);

        InputStream in = new FileInputStream(archivo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null)
            out.println(line);

        reader.close();
        archivo.delete();
    }
}
