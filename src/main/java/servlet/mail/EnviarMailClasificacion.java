package servlet.mail;

import db.temporal.DBClasificacion;
import utilidades.Mail;
import utilidades.enums.Empresa;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class EnviarMailClasificacion extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Mail mail = new Mail();
        DBClasificacion dbClasificacion = new DBClasificacion();
        int cantCARSA = dbClasificacion.getCantidadRegistrosCARSA();
        int cantEMSA = dbClasificacion.getCantidadRegistrosEMSA();

        if(cantCARSA > 0 && cantEMSA <= 0)
            mail.enviarInformeClasificacion(Empresa.CARSA);

        else if(cantCARSA <= 0 && cantEMSA > 0)
            mail.enviarInformeClasificacion(Empresa.EMSA);

        else
            mail.enviarInformeClasificacion(Empresa.NINGUNA);

        RequestDispatcher rq = request.getRequestDispatcher("Clasificacion.html");
        rq.forward(request, response);
    }
}