package Utilities;

import DataBase.DBConectionManager;
import DataBase.Feed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utilities
{
    private static String[] ARCHIVE_NAME_VALID = {"classification", "producto", "precio", "merchandise", "media", "stock"};
    private static final String[] FEED_TYPE ={"CLASSIFICATION", "PRODUCT", "PRICE", "MERCHANDISE", "MEDIA", "STOCK"};

    //extrae el nombre del archivo csv de la direccion absoluta.
    public static String setImportOrigin(String path)
    {
        String archiveName;
        String feedType = getFeedType(path);

        //empiezo a iterar antes del substring .csv para no tener problemas con stock, ya que utilizo la 's'
        //(en este caso) para cortar la iteracion
        int index = path.length()-4;

        int finalIndex = 0;
        char beginningLetter = 'a';

        if(feedType.equals("PRODUCT") || feedType.equals("PRICE"))
            beginningLetter = 'p';

        else if(feedType.equals("MEDIA") || feedType.equals("MERCHANDISE"))
            beginningLetter = 'm';

        else if(feedType.equals("STOCK") || path.contains("_aud"))
            beginningLetter = 's';

//        else if(path.contains("_aud"))
//            beginningLetter = 's';

        while(index>0)
        {
            //Ejemplo del nombre del archivo de la auditoria de stock: "stock-1801170001_aud.csv".
            //este es el caso de la auditoria de stock que tiene el prefijo _aud, entonces si encuentro una "d", saco
            //"_aud.csv" de la parte que se va a iterar para tener el nombre del archivo correctamente (moviendo el
            // puntero finalIndex.
            if(path.charAt(index) == 'd')
                finalIndex = index - 3;

            if(path.charAt(index) == beginningLetter)
                break;

            index--;
        }

        //ahora le tengo que agregar el ".csv" que le saque arriba
        if(path.contains("_aud"))
        {
            archiveName = path.substring(index, finalIndex) + ".csv";
        }

        else
            archiveName = path.substring(index);

        return archiveName.toLowerCase();
    }

    //recibe como parametro el path del archivo y devuelve un string con el tipo de feed que es dicho archivo
    private static String getFeedType(String path)
    {
        String res ="";

        for (int i = 0; i < ARCHIVE_NAME_VALID.length ; i++)
        {
            if(path.contains(ARCHIVE_NAME_VALID[i]))
            {
                res = FEED_TYPE[i];
                break;
            }
        }

        if(res.equals(""))
            throw new IllegalArgumentException("El archivo no es valido");

        return res;
    }

    //chequea que el string pasado como parametro sea un feed valido ya que hay registros en la auditria que no tienen
    //nada en el campo donde deberia tener el tipo de feed
    public static boolean checkFeedType(String feedType)
    {
        boolean res = false;
        for (String feed : FEED_TYPE)
        {
            if (feed.equals(feedType))
                res = true;
        }
        return res;
    }

    //verifica si el archivo (recibe como parametro el path de dicho archivo) es un feed de producto
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

//    public static boolean isClassification(String path)
//    {
//        return getFeedType(path).equals("CLASSIFICATION") && !path.contains("_aud");
//    }

    public static boolean isStockFeed(String path)
    {
        return getFeedType(path).equals("STOCK") && !path.contains("_aud");
    }

    public static boolean isError(String errorCode)
    {
        return errorCode.contains("E");
    }

    //devuelve el nombre del archivo procesado de stock para luego utilizarlo en los archivos que se generan despues de
    //que los registros son procesados
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

    //crea un indice en la db temporal del feed pasado como parametro
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

        else if(feed.equals(Feed.AUDITORIA))
            query = "alter table audit add index indiceAudit (productCode, importOrigin)";

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

    //crea un indice en la db historica del feed pasado como parametro
    public static void crearIndiceHistorico(Feed feed)
    {
        String query = null;
        Connection c = DBConectionManager.openConnection();

        if(feed.equals(Feed.PRECIO))
            query = "alter table historico_precios add index indiceHistoricoPrecio (productCode)";

        else if(feed.equals(Feed.PRODUCTO))
            query = "alter table historico_productos add index indiceHistoricoProducto (code)";

        else if(feed.equals(Feed.STOCK))
            query = "alter table historico_stock add index indiceHistoricoStock (productCode)";

        else if(feed.equals(Feed.MEDIA))
            query = "alter table historico_media add index indiceHistoricoMedia (productCode)";

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

    //Devuelve una lista con los nombres de los archivos que hay en los registros de la db del feed solicitado
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
}
