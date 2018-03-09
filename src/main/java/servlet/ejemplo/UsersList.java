package servlet.ejemplo;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejemplo.database_ejemplo.UsuarioServicio;
import ejemplo.entidad_usuario.Usuario;

/**
 * Servlet implementation class Add_user
 */
@WebServlet("/users_list")
public class UsersList extends SecureServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsersList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
//	{
//		if(request.getAttribute("userEmail") == null )
//		{
//			System.out.println("no log");
//		}
//		UsuarioServicio db = new UsuarioServicio();
//
//		List<Usuario> users = db.listarUsuarios();
//		request.setAttribute("users_list", users);
//
//		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/lista.jsp");
//		rd.forward(request, response);
//	}
//
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		doGet(request, response);
//	}

	@Override
	protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UsuarioServicio db = new UsuarioServicio();

		List<Usuario> users = db.listarUsuarios();
		request.setAttribute("users_list", users);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/lista.jsp");
		rd.forward(request, response);
	}

	@Override
	protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
