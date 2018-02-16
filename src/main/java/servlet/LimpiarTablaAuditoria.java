package servlet;

import DataBase.DBAudit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class LimpiarTablaAuditoria extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.getWriter().append("asdas");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        DBAudit dbAudit = new DBAudit();

        dbAudit.eliminarTabla();

        RequestDispatcher rq = request.getRequestDispatcher("Main.html");
        rq.forward(request,response);
    }
}
