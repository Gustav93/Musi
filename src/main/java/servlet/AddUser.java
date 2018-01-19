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
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/add_user")
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		UsuarioServicio servicio = new UsuarioServicio();
		
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
		
		servicio.crearUsuario(user);

		RequestDispatcher rq = request.getRequestDispatcher("Main.html");
		rq.forward(request, response);

	}

}
