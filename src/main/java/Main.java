import DataBase.DBManager;
import DataBase.DBProduct;
import FileLoader.FileLoader;
import Report.Report;

public class Main
{
    public static void main(String[] args) throws Exception
    {
//        Reader reader = new Reader("D:\\Nueva Carpeta\\stock-1801140001.csv");
//        List<List<String>> list = reader.process();
//        System.out.println(list.get(1));
//
//        List<AuditItem> f = FeedBuilder.createAuditList(list);
//
//        System.out.println(f);
//
//        DBConecionManager dbm = new DBConecionManager();
//
//        dbm.addAuditList(f);

//        FileLoader fileLoader = new FileLoader();
//        fileLoader.loadFile("D:\\media-1712070001.csv");
//        fileLoader.loadFile("D:\\merchandise-1712070003.csv");
//        fileLoader.loadFile("D:\\precio-1712070003.csv");
//        fileLoader.loadFile("D:\\producto-1712140001.csv");

//        fileLoader.loadFile("D:\\Nueva Carpeta\\Auditoria de importacion - PRODUCTOS.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\Auditoria de importacion - PRECIOS.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\Auditoria de importacion - STOCK.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\Auditoria.csv");

//        fileLoader.loadFile("D:\\Auditoria Stock.csv");
//        DBManager dbConecionManager = new DBManager();
//        fileLoader.loadFile("D:\\producto-20180114026.csv");
//        fileLoader.loadFile("D:\\producto-1801150001.csv");
//
//        fileLoader.loadFile("D:\\Nueva Carpeta\\precio-1801120002.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\precio-20180114030.csv");
//
//        fileLoader.loadFile("D:\\Nueva Carpeta\\stock-1801140001.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\stock-20180114027.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\stock-1801170001_aud.csv");
//        fileLoader.loadFile("D:\\Nueva Carpeta\\stock-1801170001.csv");


//        dbConecionManager.verfyProducts();
//        dbConecionManager.verfyPrice();
//        dbConecionManager.verfyStock();

//        DBAudit dbAudit = new DBAudit();
//        System.out.println(dbAudit.getAuditItem("171820", "stock-1801170001.csv").size());

//        Reader r = new Reader("D:\\Auditoria stock.csv");
//
//        FeedBuilder.buscarImportOrigin(r.process());


        DBProduct dbProduct = new DBProduct();

        Report r = dbProduct.getReport();

        System.out.println(r);

    }
}
