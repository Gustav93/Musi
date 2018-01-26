package FileLoader;

import CSV.Reader;
import DataBase.DBConectionManager;
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

    public void loadFile(String path) throws IOException
    {
        reader = new Reader(path);

        if (Utilities.isAudit(path))
            db.addAuditList(builder.createAuditList(reader.process()));

        else if(Utilities.isPriceFeed(path))
            db.addPriceList(builder.createPriceList(reader.process()));

        if (Utilities.isProductFeed(path))
            db.addProductList(builder.createProductList(reader.process()));

        else if(Utilities.isMerchandiseFeed(path))
            db.addMerchandiseList(builder.createMerchandiseList(reader.process()));

        else if(Utilities.isMediaFeed(path))
            db.addMediaList(builder.createMediaList(reader.process()));

        else if(Utilities.isStockFeed(path))
            db.addStockList(builder.createStockList(reader.process()));

        System.out.println("ok");
    }
}
