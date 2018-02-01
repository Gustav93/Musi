package DataBase;

import Utilities.Utilities;

import java.sql.*;


public class DBNombreArchivosProcesados
{

    private final String CREATE_TABLE_ARCHIVOS = "CREATE TABLE NOMBRE_ARCHIVOS (nombre VARCHAR(100), PRIMARY KEY (nombre))";
    private final String DELETE_TABLE_ARCHIVOS = "DROP TABLE ARCHIVOS";
    private final String INSERT_ARCHIVO = "INSERT INTO NOMBRE_ARCHIVOS (nombre) VALUES (?)";
    private final String GET_NOMBRE = "SELECT * FROM NOMBRE_ARCHIVOS WHERE nombre = ?";
    private final String LISTA_ARCHIVOS = "SELECT * FROM ARCHIVOS";
    private final String ADD_INDEX = "ALTER TABLE ARCHIVOS ADD INDEX indiceArchivos (nombre)";

    public void crearTabla()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_ARCHIVOS);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {

            System.out.println("La tabla ARCHIVOS ya existe");
        }

        DBConectionManager.closeConnection(c);

        Utilities.createIndex(ADD_INDEX);
    }

    public void elimiarTabla()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_ARCHIVOS);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }


        DBConectionManager.closeConnection(c);


    }

    public void setNombreArchivo(String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(INSERT_ARCHIVO);

            ps.setString(1, nombreArchivo);
            ps.executeUpdate();

            DBConectionManager.commit(c);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            DBConectionManager.rollback(c);
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }
    }

    public boolean existeArchivo(String nombreArchivo)
    {
        boolean res = false;
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(GET_NOMBRE);
            ps.setString(1, nombreArchivo);
            ResultSet rs = ps.executeQuery();

            res = rs.next();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }


        DBConectionManager.closeConnection(c);

        return res;
    }
}
