package db.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConectionManager
{
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_USERNAME = "auditoriaadmin";
    private static final String DB_PASSWORD = "audiadmin2018";

    public static Connection openConnection()
    {
        try
        {
            Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Connection c = null;

        try
        {
//            c = DriverManager.getConnection("jdbc:mysql://localhost/Feed?useSSL=false", DB_USERNAME, DB_PASSWORD);
//            c = DriverManager.getConnection("jdbc:mysql://10.5.202.62/Feed?useSSL=false", DB_USERNAME, DB_PASSWORD);
//            c = DriverManager.getConnection("jdbc:mysql://10.5.202.62/feed_test?useSSL=false", DB_USERNAME, DB_PASSWORD);
            c = DriverManager.getConnection("jdbc:mysql://200.61.221.197/feed?useSSL=false", DB_USERNAME, DB_PASSWORD);

            c.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnection(Connection c)
    {
        try
        {
            c.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection c)
    {
        try
        {
            c.rollback();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void commit(Connection c)
    {
        try
        {
            c.commit();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}