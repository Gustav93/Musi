package Utilities;

import DataBase.DBConectionManager;
import DataBase.Feed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Utilities
{
    private static String[] ARCHIVE_NAME_VALID = {"classification", "producto", "precio", "merchandise", "media", "stock"};
    private static final String[] FEED_TYPE ={"CLASSIFICATION", "PRODUCT", "PRICE", "MERCHANDISE", "MEDIA", "STOCK"};

    public static String setImportOrigin(String path)
    {
        String archiveName;
        String feedType = getFeedType(path);
        int index = path.length()-4;
        int finalIndex = 0;
        char beginningLetter = 'a';

//        if(feedType.equals("PRODUCT"))
//            archiveNameLength = path.length() - 23;
//
//        else if(feedType.equals("MEDIA"))
//        {
//            archiveNameLength =  path.length() - 20;
//        }
//
//        else if(feedType.equals("MERCHANDISE"))
//            archiveNameLength =  path.length() - 26;
//
//        else if(feedType.equals("PRICE"))
//            archiveNameLength =  path.length() - 21;
//
//        while (index > archiveNameLength)
//            index--;

        if(feedType.equals("PRODUCT") || feedType.equals("PRICE"))
            beginningLetter = 'p';

        else if(feedType.equals("MEDIA") || feedType.equals("MERCHANDISE"))
            beginningLetter = 'm';

        else if(feedType.equals("STOCK"))
            beginningLetter = 's';

        else if(path.contains("_aud"))
        {
            beginningLetter = 's';

//            int i = 0;
//
//            while(path.charAt(i) != '_')
//                i++;
//
//            archiveName = path.substring(0,i) + ".csv";
//
//            return archiveName;
        }

        while(index>0)
        {
            if(path.charAt(index) == 'd')
                finalIndex = index - 3;

            if(path.charAt(index) == beginningLetter)
                break;

            index--;
        }

        if(path.contains("_aud"))
        {
            archiveName = path.substring(index, finalIndex) + ".csv";
        }

        else
            archiveName = path.substring(index);

        return archiveName.toLowerCase();
    }

    public static String getFeedType(String path)
    {
        String res ="";

        for (int i = 0; i < ARCHIVE_NAME_VALID.length ; i++)
        {
            if(path.contains(ARCHIVE_NAME_VALID[i]))
            {
                return FEED_TYPE[i];
            }
        }

        return res;
    }

    public static boolean checkFeedType(String feedType)
    {
        boolean res = false;
        for (int i = 0; i < FEED_TYPE.length; i++)
        {
            if (FEED_TYPE[i].equals(feedType))
                res=true;
        }
        return res;
    }

    public static void createIndex(String statement)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(statement);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            e.getStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }

    public static boolean isProductFeed(String path)
    {
        return getFeedType(path).equals("PRODUCT") && !path.contains("_aud");
    }

    public static boolean isPriceFeed(String path)
    {
        return getFeedType(path).equals("PRICE") && !path.contains("_aud");
    }

    public static boolean isAudit(String path)
    {
        return path.contains("Auditoria") || path.contains("_aud");
    }

    public static boolean isMerchandiseFeed(String path)
    {
        return getFeedType(path).equals("MERCHANDISE") && !path.contains("_aud");
    }

    public static boolean isMediaFeed(String path)
    {
        return getFeedType(path).equals("MEDIA") && !path.contains("_aud");
    }

    public static boolean isClassification(String path)
    {
        return getFeedType(path).equals("CLASSIFICATION") && !path.contains("_aud");
    }

    public static boolean isStockFeed(String path)
    {
        return getFeedType(path).equals("STOCK") && !path.contains("_aud");
    }

    public static boolean isError(String errorCode)
    {
        return errorCode.contains("E");
    }

    public static String nombreArchivoProcesadoStock()
    {
        Calendario calendario = new Calendario();

        return "stock-procesado-" + calendario.getFechaYHora() + ".csv";

    }

    public static String nombreArchivoNoProcesadoCorrectamenteStock()
    {
        Calendario calendario = new Calendario();

        return "stock-no-procesado-correctamente-" + calendario.getFechaYHora() + ".csv";

    }

    public static String nombreArchivoProcesadoPrecio()
    {
        Calendario calendario = new Calendario();

        return "precio-procesado-" + calendario.getFechaYHora() + ".csv";

    }

    public static String nombreArchivoNoProcesadoCorrectamentePrecio()
    {
        Calendario calendario = new Calendario();

        return "precio-no-procesado-correctamente-" + calendario.getFechaYHora() + ".csv";
    }

    public static String nombreArchivoNoProcesadoCorrectamenteMedia()
    {
        Calendario calendario = new Calendario();

        return "media-no-procesado-correctamente-" + calendario.getFechaYHora() + ".csv";
    }

    public static String nombreArchivoProcesadoMedia()
    {
        Calendario calendario = new Calendario();

        return "media-procesado-" + calendario.getFechaYHora() + ".csv";
    }

    public static String nombreArchivoProcesadoProducto()
    {
        Calendario calendario = new Calendario();

        return "producto-procesado-" + calendario.getFechaYHora() + ".csv";

    }

    public static String nombreArchivoNoProcesadoCorrectamenteProducto()
    {
        Calendario calendario = new Calendario();

        return "producto-no-procesado-correctamente-" + calendario.getFechaYHora() + ".csv";

    }

    public static void crearIndice(Feed feed)
    {
        String query = null;
        Connection c = DBConectionManager.openConnection();

        if(feed.equals(Feed.PRECIO))
            query = "alter table price add index indicePrice (productCode, importOrigin)";

        else if(feed.equals(Feed.PRODUCTO))
            query = "alter table product add index indiceProduct (code, importOrigin)";

        else if(feed.equals(Feed.STOCK))
            query = "alter table stock add index indiceStock (productCode, importOrigin)";

        else if(feed.equals(Feed.MEDIA))
            query = "alter table media add index indiceMedia (productCode, importOrigin)";

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

    public static List<String> getImportOriginList(Feed feed)
    {
        List<String> importOriginList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(feed.equals(Feed.PRECIO))
            query = "select importOrigin from price group by importOrigin";

        else if (feed.equals(Feed.PRODUCTO))
            query = "select importOrigin from product group by importOrigin";

        else if (feed.equals(Feed.STOCK))
            query = "select importOrigin from stock group by importOrigin";

        else if (feed.equals(Feed.MEDIA))
            query = "select importOrigin from media group by importOrigin";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                importOriginList.add(res.getString(1));
            }
        }

        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }

        return importOriginList;
    }

    public static void importar(Feed feed)
    {

        //language=SQL
        String query = "insert into historico_media select * from media";
        Connection c = DBConectionManager.openConnection();

        if(feed.equals(Feed.PRECIO))
            query = "insert into historico_precio select * from price";

        else if(feed.equals(Feed.PRODUCTO))
            query = "insert into historico_producto select * from product";

        else if(feed.equals(Feed.STOCK))
            query = "insert into historico_stock select * from stock";

        else if(feed.equals(Feed.MEDIA))
            query = "insert into historico_media select * from media";
        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }
}
