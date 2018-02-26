package servlet;

import DataBase.DBArchivos;
import DataBase.DBNombreArchivosProcesados;
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
    //Recibe los archivos que el usuario intenta subir y los carga en la db temporal correspondiente
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        Cuando recibo la lista de archivos que enivia el usuario, aparece un elemento mas, para sacarlo convierto la
//        coleccion en un arraylist y asi lo elimino.
//        Los nombres de los archivos los recibo en un solo string. Como estos se separan por comas, utilizo el metodo split
//        para armarme un arreglo que contenga los nombres de los archivos.

        DBNombreArchivosProcesados dbNombreArchivosProcesados = new DBNombreArchivosProcesados();
        dbNombreArchivosProcesados.crearTabla();

        int i = 0;
        String[] fileNames = request.getParameter("nombreArchivo").split(",");
        Collection<Part> collectionFiles = request.getParts(); //obtengo los archivos de la request
        ArrayList<Part> files = new ArrayList<>();


        files.addAll(collectionFiles);
        files.remove(files.size()-1);

        for(Part file : files)
        {
            //si el nombre del archivo ya existe en la tabla que almacena los nombres de los archivos procesados,
            // saltea el archivo
            if(dbNombreArchivosProcesados.existeArchivo(fileNames[i]))
                continue;

            else
                dbNombreArchivosProcesados.setNombreArchivo(fileNames[i]);

            //me creo el archivo csv (temporal) que voy a cargar en la db temporal con el nombre correspondiente
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
            fl.loadFile(nombreArchivo); //cargo el archivo

            fos.close();
            is.close();
            archivo.delete(); //elimino el archivo temporal para no tener problemas luego
            i++;
        }

        RequestDispatcher rq = request.getRequestDispatcher("Procesar.html");
        rq.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}