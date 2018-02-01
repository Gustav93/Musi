package DataBase;

import DataBase.Historico.HistoricoProductos;
import Feed.*;
import Utilities.Utilities;

import java.io.File;
import java.util.List;

public class DBManager {
    private DBAudit db_audit;
    private DBMedia db_media;
    private DBMerchandise db_merchandise;
    private DBPrice db_price;
    private DBProduct db_product;
    private DBStock db_stock;
    private DBNombreArchivosProcesados dbNombreArchivosProcesados;
    private HistoricoProductos db_historico_productos;

    static int i = 0;

    public DBManager() {
        db_product = new DBProduct();
        db_audit = new DBAudit();
        db_media = new DBMedia();
        db_merchandise = new DBMerchandise();
        db_price = new DBPrice();
        db_stock = new DBStock();
//        db_archivos = new DBArchivos();
        dbNombreArchivosProcesados = new DBNombreArchivosProcesados();
        db_historico_productos = new HistoricoProductos();
    }

    public void addProductList(List<Product> productList) {
        db_product.createTable();

//        String nombreArchivo = productList.get(0).getImportOrigin();

//        if(dbNombreArchivosProcesados.existeArchivo(nombreArchivo))

        for (Product p : productList)
        {
            db_product.createProduct(p);
        }

    }

    public void addAuditList(List<AuditItem> items) {
        db_audit.createTable();

        for (AuditItem item : items)
            db_audit.createAuditItem(item);
    }

    public void addPriceList(List<Price> priceList) {
        db_price.createTable();

        for (Price price : priceList) {

            db_price.createPrice(price);
        }
    }

    public void addMerchandiseList(List<Merchandise> merchandiseList) {
        db_merchandise.createTable();

        for (Merchandise merch : merchandiseList)
            db_merchandise.createMerchandise(merch);
    }

    public void addMediaList(List<Media> mediaList) {
        db_media.createTable();

        for (Media media : mediaList)
            db_media.createMedia(media);
    }

    public void addStockList(List<Stock> stockList) {
        db_stock.createTable();

        for (Stock stock : stockList)
            db_stock.createStock(stock);
    }

    public void verfyProducts()
    {
        List<AuditItem> auditItemList = db_audit.getProductList();
        List<Product> productList = db_product.getProductList();

        for (Product p : productList) {
            for (AuditItem auditItem : auditItemList) {
                if (p.getCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin())) {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        p.setProcessed("Procesado con error");
                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_product.edit(p);
                    }

                    else
                    {
                        p.setProcessed("Procesado");
                        db_product.edit(p);
                    }
                }
            }
        }

//        DBArchivos dbArchivos = new DBArchivos();
//        dbArchivos.crearTabla();
//        CSV.Writer writer = new CSV.Writer();
//        File archivo = writer.getCsvProduct();
//        dbArchivos.setArchivo(archivo);
////        archivo.delete();
//
////        CSV.Writer writer = new CSV.Writer();
////        File archivo = writer.getCsvProduct();
////
////        db_archivos.setArchivo(archivo);
//        db_historico_productos.importarProductos();
//        db_product.deleteTable();
    }

    public void verfyPrice()
    {

        List<AuditItem> auditItemList = db_audit.getPriceList();
        List<Price> priceList = db_price.getPriceList();

        for (Price p : priceList) {
            for (AuditItem auditItem : auditItemList)
            {
                if (p.getProductCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin())) {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        p.setProcessed("Procesado con error");
                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_price.edit(p);
                    }

                    else
                    {
                        p.setProcessed("Procesado");
                        db_price.edit(p);
                    }
                }
            }
        }
    }

    public void verfyStock()
    {
//        List<AuditItem> auditItemList = db_audit.getStockList();
        List<Stock> stockList = db_stock.getStockList();

        for (Stock stock : stockList)
        {
            i++;
            System.out.println(i);
            List<AuditItem> auditItemList = db_audit.getAuditItem(stock.getProductCode(),stock.getImportOrigin());

            for (AuditItem auditItem : auditItemList)
            {
                if (stock.getProductCode().equals(auditItem.getProductCode()) && stock.getImportOrigin().equals(auditItem.getImportOrigin()))
                {
                    if(Utilities.isError(auditItem.getErrorCode()))
                    {
                        stock.setProcessed("Procesado con error");
                        stock.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
                        db_stock.edit(stock);
                    }

                    else
                    {
                        stock.setProcessed("Procesado");
                        db_stock.edit(stock);
                    }
                }
            }
        }
    }

    public List<Price> filterPriceByError()
    {
        return db_price.filterByError();
    }

    public List<Price> filterPriceByNotProcessed()
    {
        return db_price.filterByNotProcessed();
    }

    public List<Price> filterPriceByProcessed()
    {
        return db_price.filterByProcessed();
    }

    public List<Product> filterProductByError()
    {
        return db_product.filterByError();
    }

    public List<Product> filterProductByNotProcessed()
    {
        return db_product.filterByNotProcessed();
    }

    public List<Product> filterProductByProcessed()
    {
        return db_product.filterByProcessed();
    }

    public List<Stock> filterStockByError()
    {
        return db_stock.filterByError();
    }

    public List<Stock> filterStockByNotProcessed()
    {
        return db_stock.filterByNotProcessed();
    }

    public List<Stock> filterStockByProcessed()
    {
        return db_stock.filterByProcessed();
    }
}
