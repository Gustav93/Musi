package servlet.ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBase.DBAudit;
import DataBase.DBPrice;
import DataBase.DBProduct;
import DataBase.Filtro;
import Feed.Price;
import com.fasterxml.jackson.databind.ObjectMapper;

import db.*;

/**
 * Servlet implementation class UsersListJson
 */
@WebServlet("/users_list_json")
public class UsersListJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersListJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
//		Usuario u = new Usuario(123, "asd", FechaUtil.crearFecha(11, 11, 1111));
		UsuarioServicio db = new UsuarioServicio();
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		DBAudit dbAudit = new DBAudit();
		DBPrice dbPrice = new DBPrice();
		DBProduct dbProduct = new DBProduct();

//		List<AuditItem> auditItemList = dbAudit.getListaAuditoria();
		List<Price> priceList = dbPrice.filtrarPor(Filtro.SIN_FILTRAR);
//		List<Product> productList = dbProduct.getProductList();

//		List<Usuario> users = db.listarUsuarios();

		
//		mapper.setDateFormat(df);

//		String jsonString = mapper.writeValueAsString(users);

//		String jsonString = mapper.writeValueAsString(auditItemList);
		String jsonString = mapper.writeValueAsString(priceList);
//		String jsonString = mapper.writeValueAsString(productList);

		response.setContentType("application/json");
		// Get the printwriter object from response to write the required json object to the output stream
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object
		out.print(jsonString);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
