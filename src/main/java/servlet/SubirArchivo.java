package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.*;

@WebServlet(name = "/subir_archivo")
@MultipartConfig
public class SubirArchivo extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {


        Part file = request.getPart("archivo");

        InputStream is = file.getInputStream();

        File archivo = new File("texto.txt");

        FileOutputStream fos = new FileOutputStream(archivo);
        int dato = is.read();

        System.out.println(dato);

        while(dato != -1)
        {
            fos.write(dato);
            dato = is.read();
        }

        fos.close();
        is.close();

        FileReader fr= new FileReader("texto.txt");
        BufferedReader br = new BufferedReader(fr);
        PrintWriter out = response.getWriter();
        String linea = br.readLine();
        while(linea != null)
        {
            out.println(br.readLine());
            linea = br.readLine();
        }
        fr.close();
        br.close();

//        PrintWriter out = response.getWriter();
        out.println("asdas");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
