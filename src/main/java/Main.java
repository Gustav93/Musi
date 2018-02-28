import CSV.Writer;
import FileLoader.FileLoader;

import java.io.File;

public class Main
{
    public static void main(String[] args) throws Exception
    {
//        Reader reader = new Reader("D:\\Nueva Carpeta\\stock-1801140001.csv");
//        List<List<String>> list = reader.procesar();
//        System.out.println(list.get(1));
//
//        List<AuditItem> f = FeedBuilder.createAuditList(list);
//
//        System.out.println(f);
//
//        DBConecionManager dbm = new DBConecionManager();
//
//        dbm.agregarRegistrosAuditoria(f);

        FileLoader fileLoader = new FileLoader();

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
        fileLoader.loadFile("D:\\Feeds\\merchandise-1712070003.csv");


//        dbConecionManager.verificarProductos();
//        dbConecionManager.verificarPrecios();
//        dbConecionManager.verificarStock();

//        DBAudit dbAudit = new DBAudit();
//        System.out.println(dbAudit.getRegistro("171820", "stock-1801170001.csv").size());

//        Reader r = new Reader("D:\\Auditoria stock.csv");
//
//        FeedBuilder.buscarImportOrigin(r.procesar());


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
////        productos.crearTabla();
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

//        Calendar calendar = new GregorianCalendar();
//
//        System.out.println("año: " + calendar.get(calendar.YEAR));
//        System.out.println("mes: " + (calendar.get(calendar.MONTH)+1));
//        System.out.println("dia: " + calendar.get(calendar.DAY_OF_MONTH));
//        System.out.println("hora: " + calendar.get(calendar.HOUR_OF_DAY));
//        System.out.println("min: " + calendar.get(calendar.MINUTE));
//
//        String year = String.valueOf(calendar.get(calendar.YEAR));
//        String mes = String.valueOf(calendar.get(calendar.MONTH)+1);
//        String dia = String.valueOf((calendar.get(calendar.DAY_OF_MONTH)));
//        String hora = String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
//        String min = String.valueOf(calendar.get(calendar.MINUTE));
//
//        if (mes.length() == 1)
//            mes = 0 + mes;
//
//        if(dia.length() == 1)
//            dia = 0 + dia;
//
//        if(hora.length() == 1)
//            hora = 0+ hora;
//
//        if(min.length() == 1)
//            min = 0 + min;
//
//        System.out.println("año: " + year);
//        System.out.println("mes: " + mes);
//        System.out.println("dia: " + dia);
//        System.out.println("hora: " + hora);
//        System.out.println("min: " + min);
//
//        System.out.println("stock-procesado-" + year + mes + dia + hora + min + ".csv");
//        System.out.println(Utilities.nombreArchivoProcesadoStock());

//        DBPrice dbPrice = new DBPrice();
//
////        List<String> s = dbPrice.getImportOriginList();
//
////        System.out.println(s);
//
//        List<Reporte> reportes = dbPrice.getReportes();
//
//        System.out.println(reportes);

//        DBMedia dbMedia = new DBMedia();
//
//        dbMedia.crearTabla();
//        Feed.Media m = new Media();
//        m.setProductCode("asdasda");
//
//        dbMedia.crearRegistro(m);

//        FileLoader loader = new FileLoader();
////        loader.loadFile("D:\\Media EMSA - 16012018 al 31012018_aud.csv");
//        loader.loadFile("D:\\media-20180122025.csv");
////
//        DBManager dbManager = new DBManager();
////
//        dbManager.verificarMedia();

//        Writer writer = new Writer();
//
//        File f = writer.getCsvMediaNoProcesadoCorrectamente();

    }
}
