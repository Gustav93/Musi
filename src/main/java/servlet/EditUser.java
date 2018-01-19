package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UsuarioServicio;
import entidad_usuario.Usuario;
import utilidades.FechaUtil;

/**
 * Servlet implementation class EditUser
 */
@WebServlet("/edit_user")
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		int dni = Integer.parseInt(request.getParameter("dni"));
		
		UsuarioServicio db = new UsuarioServicio();
		
		Usuario user = db.consultarUsuario(dni);
		request.setAttribute("user", user);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/edit_user.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		UsuarioServicio db = new UsuarioServicio();
		int dni = Integer.parseInt(request.getParameter("dni_usuario"));
		String nombre = request.getParameter("nombre_usuario");
		String stringFecha = request.getParameter("fecha_nacimiento");
		Date fecha = null;

		try 
		{
			fecha = FechaUtil.crearFecha(stringFecha);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
	
		Usuario user = new Usuario(dni, nombre, fecha);
		
		try 
		{
			db.modificarUsuario(user);
		}
		
		catch (Exception e) 
		{
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/edit_user.jsp");
			rd.forward(request, response);
		}
		finally 
		{
			RequestDispatcher rd = request.getRequestDispatcher("users_list");
			rd.forward(request, response);
		}
	}

}
