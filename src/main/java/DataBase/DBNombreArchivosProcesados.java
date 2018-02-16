package DataBase;

import java.sql.*;

public class DBNombreArchivosProcesados
{
    public void crearTabla()
    {
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "create table nombre_archivos (nombre varchar(100), primary key (nombre))";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {

            System.out.println("La tabla NOMBRE_ARCHIVOS ya existe");
        }

        DBConectionManager.closeConnection(c);

        crearIndice();
    }

//    public void elimiarTabla()
//    {
//        Connection c = DBConectionManager.openConnection();
//        //language=SQL
//        String query = "drop table nombre_archivos";
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.execute();
//            DBConectionManager.commit(c);
//        }
//
//        catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//
//        DBConectionManager.closeConnection(c);
//    }

    public void setNombreArchivo(String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "insert into nombre_archivos (nombre) values (?)";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

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
        //language=SQL
        String query = "select * from nombre_archivos where nombre = ?";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
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

    private void crearIndice()
    {
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query ="alter table nombre_archivos add index indiceArchivos (nombre)";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            e.getStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }
}