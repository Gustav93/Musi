package ejemplo.database_ejemplo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.conexiones.DBConectionManager;
import ejemplo.entidad_usuario.Usuario;
import utilidades.FechaUtil;

public class UsuarioServicio implements UsuarioDAO
{
	private final String CREAR_TABLA = "CREATE TABLE USUARIOS (dni INTEGER, nombre VARCHAR(50), fecha_nacimiento DATE)";
	private final String ELIMINAR_TABLA = "DROP TABLE USUARIOS";
    private final String INSERTAR = "INSERT INTO USUARIOS (dni, nombre, fecha_nacimiento) VALUES (?,?,?)";
    private final String EDITAR = "UPDATE USUARIOS SET nombre = ?, fecha_nacimiento = ? WHERE dni = ?";
    private final String BORRAR = "DELETE FROM USUARIOS WHERE dni = ?";
    private final String LISTAR = "SELECT * FROM USUARIOS";
    private final String CONSULTAR = "SELECT * FROM USUARIOS WHERE dni = ?";

//	public UsuarioServicio()
//	{
//
//	}

    public void crearTabla()
    {
    	Connection c = DBConectionManager.openConnection();

    	try
    	{
			PreparedStatement ps = c.prepareStatement(CREAR_TABLA);
			ps.execute();
			DBConectionManager.commit(c);
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	try
    	{
			DBConectionManager.closeConnection(c);
		}

    	catch (Exception e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void eliminarTabla()
    {
    	Connection c = DBConectionManager.openConnection();

    	try
    	{
			PreparedStatement ps = c.prepareStatement(ELIMINAR_TABLA);
			ps.execute();
			DBConectionManager.commit(c);
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	try
    	{
			DBConectionManager.closeConnection(c);
		}

    	catch (Exception e)
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Override
	public void crearUsuario(Usuario u)
	{
		Connection c = DBConectionManager.openConnection();

		try
		{
			PreparedStatement ps = c.prepareStatement(INSERTAR);
			java.sql.Date fecha = new java.sql.Date(u.getFechaDenacimiento().getTime());

			ps.setInt(1, u.getDni());
			ps.setString(2, u.getNombre());
			ps.setDate(3, fecha);
			ps.executeUpdate();

			DBConectionManager.commit(c);
		}

		catch (Exception e)
		{
			DBConectionManager.rollback(c);
		}
		finally {

			try {
				DBConectionManager.closeConnection(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void modificarUsuario(Usuario u)
	{
		Connection c = DBConectionManager.openConnection();

		try
		{
			PreparedStatement ps = c.prepareStatement(EDITAR);

			ps.setString(1, u.getNombre());
			ps.setDate(2, FechaUtil.getFecha(u.getFechaDenacimiento()));
			ps.setInt(3, u.getDni());
			ps.executeUpdate();

			DBConectionManager.commit(c);


		}
		catch (Exception e)
		{
			DBConectionManager.rollback(c);
		}

		finally
		{
			try {
				DBConectionManager.closeConnection(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

//	@Override
//	public void modificarUsuario(Usuario u)
//	{
//        Connection connection = DBConecionManager.openConnection();
//        try {
//            PreparedStatement ps = connection.prepareStatement(EDITAR);
//            ps.setString(1, u.getNombre());
//            java.sql.Date d = new java.sql.Date(u.getFechaDenacimiento().getTime());
//            ps.setDate(2, d);
//            ps.setInt(3, u.getDni());
//            ps.executeUpdate();
//
//            DBConecionManager.commit(connection);
//
//        } catch (SQLException e) {
//            DBConecionManager.rollback(connection);
//            e.printStackTrace();
//
//        } finally {
//            try {
//				DBConecionManager.closeConnection(connection);
//			} catch (DBOperationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//	}


	@Override
	public void eliminarUsuario(int dni)
	{
		Connection c = DBConectionManager.openConnection();

		try
		{
			PreparedStatement ps = c.prepareStatement(BORRAR);
			ps.setInt(1, dni);
			ps.executeUpdate();
			DBConectionManager.commit(c);
		}
		catch (Exception e)
		{
			DBConectionManager.rollback(c);
		}

		finally
		{
				try {
					DBConectionManager.closeConnection(c);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

//    @Override
//    public void eliminarUsuario(int id)
//    {
//        Connection connection = DBConecionManager.openConnection();
//        try {
//            PreparedStatement ps = connection.prepareStatement(BORRAR);
//            ps.setInt(1, id);
//            ps.executeUpdate();
//
//            DBConecionManager.commit(connection);
//
//        } catch (SQLException e) {
//            DBConecionManager.rollback(connection);
//            //e.printStackTrace();
//
//        } finally {
//            try {
//				DBConecionManager.closeConnection(connection);
//			} catch (DBOperationException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//    }

	@Override
	public Usuario consultarUsuario(int dni)
	{
		Usuario u = new Usuario();
		Connection c = DBConectionManager.openConnection();

		try
		{
			PreparedStatement ps = c.prepareStatement(CONSULTAR);
			ps.setInt(1, dni);
			ResultSet res = ps.executeQuery();

			while(res.next())
			{
				java.sql.Date fecha_nacimiento_sql = res.getDate("fecha_nacimiento");
				java.util.Date fechaNacimiento = new java.util.Date(fecha_nacimiento_sql.getTime());
				u.setFechaDenacimiento(fechaNacimiento);
				u.setDni(res.getInt("dni"));
				u.setNombre(res.getString("nombre"));

			}
		}
		catch (Exception e)
		{
			DBConectionManager.rollback(c);
		}

		finally
		{
			try {
				DBConectionManager.closeConnection(c);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return u;
	}

	@Override
	public List<Usuario> listarUsuarios()
	{
		ArrayList<Usuario> users = new ArrayList<>();
		Connection c = DBConectionManager.openConnection();

		try
		{
			PreparedStatement statement = c.prepareStatement(LISTAR);
			ResultSet res = statement.executeQuery();

			while(res.next())
			{
				java.sql.Date fecha_nacimiento_sql = res.getDate("fecha_nacimiento");
				java.util.Date fechaNacimiento = new java.util.Date(fecha_nacimiento_sql.getTime());

				Usuario u = new Usuario();
				u.setDni(res.getInt("dni"));
				u.setNombre(res.getString("nombre"));
				u.setFechaDenacimiento(fechaNacimiento);
				users.add(u);
			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}

		return users;
	}
}
