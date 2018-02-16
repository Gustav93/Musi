package FileLoader;

import CSV.Reader;
import DataBase.DBManager;
import Feed.FeedBuilder;
import Utilities.Utilities;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileLoader
{
    private Reader reader;
    private DBManager db;
    private FeedBuilder builder;

    public FileLoader() throws FileNotFoundException
    {
        db = new DBManager();
        builder = new FeedBuilder();
    }

    //recibe la ubicacion del archivo csv como parametro para luego cargar su contenido a la db correspondiente
    public void loadFile(String path) throws IOException
    {
        reader = new Reader(path);

        if (Utilities.isAudit(path))
            db.agregarRegistrosAuditoria(builder.createAuditList(reader.procesar()));

        else if(Utilities.isPriceFeed(path))
            db.agregarListaPrecios(builder.createPriceList(reader.procesar()));

        else if (Utilities.isProductFeed(path))
            db.agregarListaProductos(builder.createProductList(reader.procesar()));

        else if(Utilities.isMerchandiseFeed(path))
            db.agregarListaMerchandise(builder.createMerchandiseList(reader.procesar()));

        else if(Utilities.isMediaFeed(path))
            db.agregarListaMedia(builder.createMediaList(reader.procesar()));

        else if(Utilities.isStockFeed(path))
            db.agregarListaStock(builder.createStockList(reader.procesar()));

        System.out.println("ok");
    }
}