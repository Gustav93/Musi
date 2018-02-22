package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConectionManager
{
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

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
            c = DriverManager.getConnection("jdbc:mysql://localhost/Feed?useSSL=false", DB_USERNAME, DB_PASSWORD);
//            c = DriverManager.getConnection("jdbc:mysql://localhost/feed_test?useSSL=false", DB_USERNAME, DB_PASSWORD);
//            c = DriverManager.getConnection("jdbc:mysql://mysq14505-musiwebapp.jelastic.saveincloud.net/feed", "user", "sWGgjGnHwbI1mORB");
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