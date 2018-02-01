package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("fileType");
//
//        response.setHeader("Content-disposition","attachment; filename=prueba.txt");
//        response.getWriter().append("asdasd");

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/lista_archivos.jsp");
        rd.forward(request, response);
    }
}
