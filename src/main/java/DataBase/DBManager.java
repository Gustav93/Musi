package DataBase;

import Feed.*;
import Utilities.Utilities;

import java.util.List;

public class DBManager {
    private DBAudit db_audit;
    private DBMedia db_media;
    private DBMerchandise db_merchandise;
    private DBPrice db_price;
    private DBProduct db_product;
    private DBStock db_stock;
//    private DBNombreArchivosProcesados dbNombreArchivosProcesados;
//    private HistoricoProductos db_historico_productos;

    public DBManager() {
        db_product = new DBProduct();
        db_audit = new DBAudit();
        db_media = new DBMedia();
        db_merchandise = new DBMerchandise();
        db_price = new DBPrice();
        db_stock = new DBStock();
//        db_archivos = new DBArchivos();
//        dbNombreArchivosProcesados = new DBNombreArchivosProcesados();
//        db_historico_productos = new HistoricoProductos();
    }

    public void addProductList(List<Product> productList) {
        db_product.crearTabla();

//        String nombreArchivo = productList.get(0).getImportOrigin();

//        if(dbNombreArchivosProcesados.existeArchivo(nombreArchivo))

        for (Product p : productList)
        {
            db_product.crearRegistro(p);
        }
    }

    public void addAuditList(List<AuditItem> items) {
        db_audit.createTable();

        for (AuditItem item : items)
            db_audit.createAuditItem(item);
    }

    public void addPriceList(List<Price> priceList) {
        db_price.crearTabla();

        for (Price price : priceList) {

            db_price.crearRegistro(price);
        }
    }

    public void addMerchandiseList(List<Merchandise> merchandiseList) {
        db_merchandise.createTable();

        for (Merchandise merch : merchandiseList)
            db_merchandise.createMerchandise(merch);
    }

    public void addMediaList(List<Media> mediaList) {
        db_media.crearTabla();

        for (Media media : mediaList)
            db_media.crearRegistro(media);
    }

    public void addStockList(List<Stock> stockList) {
        db_stock.crearTabla();

        for (Stock stock : stockList)
            db_stock.crearRegistro(stock);
    }

    public void verfyProducts()
    {
        List<AuditItem> auditItemList = db_audit.getProductList();
        List<Product> productList = db_product.filtrarPor(Filtro.SIN_FILTRAR);

        for (Product p : productList) {
            for (AuditItem auditItem : auditItemList) {
                if (p.getCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin())) {
                    p.setEmpresa(auditItem.getEmpresa());
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        p.setProcessed("Procesado con error");
                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_product.editar(p);
                    }

                    else
                    {
                        p.setProcessed("Procesado");
                        db_product.editar(p);
                    }
                }
            }
        }

//        DBArchivos dbArchivos = new DBArchivos();
//        dbArchivos.crearTabla();
//        CSV.Writer writer = new CSV.Writer();
//        File archivo = writer.getCsvProductList();
//        dbArchivos.setArchivo(archivo);
////        archivo.delete();
//
////        CSV.Writer writer = new CSV.Writer();
////        File archivo = writer.getCsvProductList();
////
////        db_archivos.setArchivo(archivo);
//        db_historico_productos.importarProductos();
//        db_product.eliminarTable();
    }

    public void verfyPrice()
    {

        List<AuditItem> auditItemList = db_audit.getPriceList();
        List<Price> priceList = db_price.filtrarPor(Filtro.SIN_FILTRAR);

        for (Price p : priceList) {
            for (AuditItem auditItem : auditItemList)
            {
                p.setEmpresa(auditItem.getEmpresa());
                if (p.getProductCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin())) {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        p.setProcessed("Procesado con error");
                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_price.editar(p);
                    }

                    else
                    {
                        p.setProcessed("Procesado");
                        db_price.editar(p);
                    }
                }
            }
        }
    }

    public void verfyStock()
    {
//        List<AuditItem> auditItemList = db_audit.getStockList();
        List<Stock> stockList = db_stock.filtrarPor(Filtro.SIN_FILTRAR);

        for (Stock stock : stockList)
        {
            List<AuditItem> auditItemList = db_audit.getAuditItem(stock.getProductCode(),stock.getImportOrigin());

            for (AuditItem auditItem : auditItemList)
            {
                stock.setEmpresa(auditItem.getEmpresa());
                if (stock.getProductCode().equals(auditItem.getProductCode()) && stock.getImportOrigin().equals(auditItem.getImportOrigin()))
                {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        stock.setProcessed("Procesado con error");
                        stock.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_stock.editar(stock);
                    }

                    else
                    {
                        stock.setProcessed("Procesado");
                        db_stock.editar(stock);
                    }
                }
            }
        }
    }

    public void verfyMedia()
    {
//        List<AuditItem> auditItemList = db_audit.getStockList();
//        List<Stock> stockList = db_stock.getStockList();
        List<Media> mediaList = db_media.filtrarPor(Filtro.SIN_FILTRAR);

        for (Media media : mediaList)
        {
            List<AuditItem> auditItemList = db_audit.getAuditItem(media.getProductCode(),media.getImportOrigin());

            for (AuditItem auditItem : auditItemList)
            {
                media.setEmpresa(auditItem.getEmpresa());
                if (media.getProductCode().equals(auditItem.getProductCode()) && media.getImportOrigin().equals(auditItem.getImportOrigin()))
                {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        media.setProcessed("Procesado con error");
                        media.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_media.editar(media);
                    }

                    else
                    {
                        media.setProcessed("Procesado");
                        db_media.editar(media);
                    }
                }
            }
        }
    }

}
