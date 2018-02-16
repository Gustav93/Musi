package DataBase;

import Feed.*;
import Utilities.Utilities;

import java.util.List;

public class DBManager
{
    private DBAudit db_audit;
    private DBMedia db_media;
    private DBMerchandise db_merchandise;
    private DBPrice db_price;
    private DBProduct db_product;
    private DBStock db_stock;

    public DBManager()
    {
        db_product = new DBProduct();
        db_audit = new DBAudit();
        db_media = new DBMedia();
        db_merchandise = new DBMerchandise();
        db_price = new DBPrice();
        db_stock = new DBStock();
    }

    public void agregarListaProductos(List<Product> productList)
    {
        db_product.crearTabla();

        for (Product p : productList)
            db_product.crearRegistro(p);

    }

    public void agregarRegistrosAuditoria(List<AuditItem> items)
    {
        db_audit.crearTabla();

        for (AuditItem item : items)
            db_audit.crearRegistro(item);
    }

    public void agregarListaPrecios(List<Price> priceList)
    {
        db_price.crearTabla();

        for (Price price : priceList)
            db_price.crearRegistro(price);
    }

    public void agregarListaMerchandise(List<Merchandise> merchandiseList)
    {
        db_merchandise.createTable();

        for (Merchandise merch : merchandiseList)
            db_merchandise.createMerchandise(merch);
    }

    public void agregarListaMedia(List<Media> mediaList)
    {
        db_media.crearTabla();

        for (Media media : mediaList)
            db_media.crearRegistro(media);
    }

    public void agregarListaStock(List<Stock> stockList)
    {
        db_stock.crearTabla();

        for (Stock stock : stockList)
            db_stock.crearRegistro(stock);
    }

    public void verificarProductos()
    {
        List<Product> productList = db_product.filtrarPor(Filtro.SIN_FILTRAR);

        for (Product p : productList)
        {
            List<AuditItem> auditItemList = db_audit.getRegistro(p.getCode(), p.getImportOrigin());

            for (AuditItem auditItem : auditItemList)
            {
                if (p.getCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin()))
                {
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
    }

    public void verificarPrecios()
    {
        List<Price> priceList = db_price.filtrarPor(Filtro.SIN_FILTRAR);

        for (Price p : priceList)
        {
            List<AuditItem> auditItemList = db_audit.getRegistro(p.getProductCode(),p.getImportOrigin());

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

    public void verificarStock()
    {
        List<Stock> stockList = db_stock.filtrarPor(Filtro.SIN_FILTRAR);

        for (Stock stock : stockList)
        {
            List<AuditItem> auditItemList = db_audit.getRegistro(stock.getProductCode(),stock.getImportOrigin());

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

    public void verificarMedia()
    {
        List<Media> mediaList = db_media.filtrarPor(Filtro.SIN_FILTRAR);

        for (Media media : mediaList)
        {
            List<AuditItem> auditItemList = db_audit.getRegistro(media.getProductCode(),media.getImportOrigin());

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
