package servlet;

import DataBase.DBArchivos;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet
@MultipartConfig
public class SubirArchivo extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

//        Cuando recibo la lista de archivos que enivia el usuario, aparece un elemento mas, para sacarlo convierto la
//        colecion en un arraylist y asi lo elimino.
//        Los nombres de los archivos los recibo en un solo string. Como estos se separan por comas, utilizo el metodo split
//        para armarme un arreglo que contenga los nombres de los archivos.

        DBArchivos dbArchivos = new DBArchivos();
        dbArchivos.crearTabla();



        Collection<Part> collectionFiles = request.getParts();
        ArrayList<Part> files = new ArrayList<>();
        files.addAll(collectionFiles);
        files.remove(files.size()-1);
        String[] fileNames = request.getParameter("nombreArchivo").split(",");
        int i = 0;

        for(Part file : files)
        {
            if(dbArchivos.existeArchivo(fileNames[i]))
                continue;

            InputStream is = file.getInputStream();
            String nombreArchivo = fileNames[i];

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



            dbArchivos.setArchivo(archivo);
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
            i++;


//            if(Utilities.isProductFeed(nombreArchivo))
//            {
//                RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
//                rq.forward(request, response);
//            }
//
//            else if(Utilities.isAudit(nombreArchivo))
//            {
//                RequestDispatcher rq = request.getRequestDispatcher("Auditoria.html");
//                rq.forward(request, response);
//            }
//
//            else if(Utilities.isPriceFeed(nombreArchivo))
//            {
//                RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
//                rq.forward(request, response);
//            }
//
//            else if(Utilities.isStockFeed(nombreArchivo))
//            {
//                RequestDispatcher rq = request.getRequestDispatcher("Stock.html");
//                rq.forward(request, response);
//            }
        }

        RequestDispatcher rq = request.getRequestDispatcher("Main.html");
                rq.forward(request, response);




//        Part file = request.getPart("archivo");
//
//        InputStream is = file.getInputStream();
//        String nombreArchivo = request.getParameter("nombreArchivo");
//
//
//
//        File archivo = new File(nombreArchivo);
//
//        FileOutputStream fos = new FileOutputStream(archivo);
//        int dato = is.read();
//
//        while(dato != -1)
//        {
//            fos.write(dato);
//            dato = is.read();
//        }
//
//        FileLoader fl = new FileLoader();
//        fl.loadFile(nombreArchivo);
//
//
//        fos.close();
//        is.close();
//
//
//
////        FileReader fr= new FileReader(nombreArchivo);
////        BufferedReader br = new BufferedReader(fr);
////        PrintWriter out = response.getWriter();
////        String linea = br.readLine();
////        while(linea != null)
////        {
////            out.println(linea);
////            linea = br.readLine();
////        }
////
////        out.println(nombreArchivo);
////        fr.close();
////        br.close();
//
//        file.delete();
//
//
//        if(Utilities.isProductFeed(nombreArchivo))
//        {
//            RequestDispatcher rq = request.getRequestDispatcher("Productos.html");
//            rq.forward(request, response);
//        }
//
//        else if(Utilities.isAudit(nombreArchivo))
//        {
//            RequestDispatcher rq = request.getRequestDispatcher("Auditoria.html");
//            rq.forward(request, response);
//        }
//
//        else if(Utilities.isPriceFeed(nombreArchivo))
//        {
//            RequestDispatcher rq = request.getRequestDispatcher("Precios.html");
//            rq.forward(request, response);
//        }
//
//        else if(Utilities.isStockFeed(nombreArchivo))
//        {
//            RequestDispatcher rq = request.getRequestDispatcher("Stock.html");
//            rq.forward(request, response);
//        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
