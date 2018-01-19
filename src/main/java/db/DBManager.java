package db;



import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

//    private static final String DB_DRIVER = "org.h2.Driver";
//    private static final String DB_BASE_URL = "jdbc:h2:tcp://localhost//{DIR}";
//    private static final String DB_NAME = "/mi_ejemplo_proyecto";
//    private static final String DB_USERNAME = "sa";
//    private static final String DB_PASSWORD = "";
//
//    //public static Connection openConnection() throws DriverException, DBOperationException {
//    public static Connection openConnection() {
//        try {
//            Class.forName(DB_DRIVER);
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            //tirar DriverException
//        }
//        Connection c = null;
//        try {
//            final String url = DB_BASE_URL.replace("{DIR}", obtenerUbicacionBase()) + DB_NAME;
////            System.out.println("CONECTANDOSE A:\r\n" + url);
//            c = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
//            c.setAutoCommit(false);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            //tirar DriverException
//        }
//        return c;
//    }
//
//    public static void closeConnection(Connection c)
//    {
//        try {
//            c.close();
//        } catch (SQLException e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//    public static void rollback(Connection c) {
//        try {
//            c.rollback();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void commit(Connection c) {
//        try {
//            c.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private static String obtenerUbicacionBase() {
//        File currDir = new File("h2/base_de_datos/");
//        return currDir.getAbsolutePath();
//    }

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_BASE_URL = "jdbc:h2:tcp://localhost//{DIR}";
    private static final String DB_NAME = "/feed";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    //public static Connection openConnection() throws DriverException, DBOperationException {
    public static Connection openConnection() {
        try {
            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            //tirar DriverException
        }
        Connection c = null;
        try {
//            final String url = DB_BASE_URL.replace("{DIR}", obtenerUbicacionBase()) + DB_NAME;
//            System.out.println("CONECTANDOSE A:\r\n" + url);
            c = DriverManager.getConnection("jdbc:mysql://localhost/feed?useSSL=false", DB_USERNAME, DB_PASSWORD);
            c.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            //tirar DriverException
        }
        return c;
    }

    public static void closeConnection(Connection c)
    {
        try {
            c.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void rollback(Connection c) {
        try {
            c.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection c) {
        try {
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static String obtenerUbicacionBase() {
        File currDir = new File("h2/base_de_datos/");
        return currDir.getAbsolutePath();
    }
}
