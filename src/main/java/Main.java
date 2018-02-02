import DataBase.Historico.HistoricoProductos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

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


//        DBProduct dbProduct = new DBProduct();
//
//        Report r = dbProduct.getReport();
//
//        System.out.println(r);

//        DBArchivos dbArchivos = new DBArchivos();
//
//        dbArchivos.crearTabla();
//        File f = new File("ah.txt");
//        dbArchivos.setArchivo(f);

//        HistoricoProductos productos = new HistoricoProductos();
//
////        productos.createTable();
//        productos.importarProductos();

//        String s = "producto-1801270001 (procesado).csv";
//
//        String s1 = s.replaceAll(" \\(procesado\\)","");
//        System.out.println(s1);

//        File archivo = new File("C:\\Users\\gsanchez\\Google Drive\\prueba.txt");
//
//        BufferedWriter bw;
//        if(archivo.exists()) {
//            bw = new BufferedWriter(new FileWriter(archivo));
//            bw.write("El fichero de texto ya estaba creado.");
//        } else {
//            bw = new BufferedWriter(new FileWriter(archivo));
//            bw.write("Acabo de crear el fichero de texto.");
//        }
//        bw.close();

        Calendar calendar = new GregorianCalendar();

        System.out.println(calendar.get(calendar.YEAR));
        System.out.println((calendar.get(calendar.MONTH)+1));
        System.out.println(calendar.get(calendar.DAY_OF_MONTH));

    }
}
