package file_loader;

import csv.Reader;
import db.manager.DBManager;
import Feeds.builder.FeedBuilder;
import utilidades.Utilities;

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
            db.agregarRegistrosAuditoria(builder.crearListaRegistrosAuditoria(reader.procesar()));

        else if(Utilities.isPriceFeed(path))
            db.agregarListaPrecios(builder.crearListaPrecios(reader.procesar()));

        else if (Utilities.isProductFeed(path))
            db.agregarListaProductos(builder.crearListaProductos(reader.procesar()));

        else if(Utilities.isMerchandiseFeed(path))
            db.agregarListaMerchandise(builder.crearListaMerchandise(reader.procesar()));

        else if(Utilities.isMediaFeed(path))
            db.agregarListaMedia(builder.crearListaMedia(reader.procesar()));

        else if(Utilities.isStockFeed(path))
            db.agregarListaStock(builder.crearListaStock(reader.procesar()));

        System.out.println("ok");
    }
}