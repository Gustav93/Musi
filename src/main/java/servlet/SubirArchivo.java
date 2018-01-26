package servlet;

import FileLoader.FileLoader;
import Utilities.Utilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.*;

@WebServlet
@MultipartConfig
public class SubirArchivo extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Part file = request.getPart("archivo");

        InputStream is = file.getInputStream();
        String nombreArchivo = request.getParameter("nombreArchivo");



        File archivo = new File(nombreArchivo);

        FileOutputStream fos = new FileOutputStream(archivo);
        int dato = is.read();

        while(dato != -1)
        {
            fos.write(dato);
            dato = is.read();
        }

        FileLoader fl = new FileLoader();
        fl.loadFile(nombreArchivo);


        fos.close();
        is.close();



//        FileReader fr= new FileReader(nombreArchivo);
//        BufferedReader br = new BufferedReader(fr);
//        PrintWriter out = response.getWriter();
//        String linea = br.readLine();
//        while(linea != null)
//        {
//            out.println(linea);
//            linea = br.readLine();
//        }
//
//        out.println(nombreArchivo);
//        fr.close();
//        br.close();

        file.delete();


        if(Utilities.isProductFeed(nombreArchivo))
        {
            RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
            rq.forward(request, response);
        }

        else if(Utilities.isAudit(nombreArchivo))
        {
            RequestDispatcher rq = request.getRequestDispatcher("Auditoria.html");
            rq.forward(request, response);
        }

        else if(Utilities.isPriceFeed(nombreArchivo))
        {
            RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
            rq.forward(request, response);
        }

        else if(Utilities.isStockFeed(nombreArchivo))
        {
            RequestDispatcher rq = request.getRequestDispatcher("Stock.html");
            rq.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
