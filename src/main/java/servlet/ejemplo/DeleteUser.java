package servlet.ejemplo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejemplo.database_ejemplo.UsuarioServicio;

/**
 * Servlet implementation class DeleteUser
 */
@WebServlet("/delete_user")
public class DeleteUser extends SecureServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		UsuarioServicio db = new UsuarioServicio();
		int dni = Integer.parseInt(request.getParameter("dni"));
		db.eliminarUsuario(dni);
		RequestDispatcher rd = request.getRequestDispatcher("users_list");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
