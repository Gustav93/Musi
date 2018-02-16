package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet
public class DescargarProduct extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DBArchivos dbArchivos = new DBArchivos();
//        String nombreArchivo = request.getParameter("nombreArchivo");
//        File archivo = dbArchivos.getArchivo(nombreArchivo);
        PrintWriter out = response.getWriter();
        CSV.Writer writer = new CSV.Writer();
        File archivo = writer.getCsvProductList();
        String nombreArchivo = archivo.getName();


        response.setContentType("fileType");
        response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);

//        try {
//            fr = new FileReader(archivo);
//            br = new BufferedReader(fr);
//
//            while(br.readLine() != null)
//                out.println(br.readLine());
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        fr.close();
//        br.close();
        InputStream in = new FileInputStream(archivo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            out.println(line);
        }
        reader.close();
        archivo.delete();

//        response.getWriter().append("asd");
    }

}
