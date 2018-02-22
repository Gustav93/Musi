package DataBase;

import Feed.*;
import Utilities.Utilities;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    public void verificarProductos()
//    {
//        List<Product> productList = db_product.filtrarPor(Filtro.SIN_FILTRAR);
//
//        for (Product p : productList)
//        {
//            List<AuditItem> auditItemList = db_audit.getRegistro(p.getCode(), p.getImportOrigin());
//
//            for (AuditItem auditItem : auditItemList)
//            {
//                if (p.getCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin()))
//                {
//                    p.setEmpresa(auditItem.getEmpresa());
//                    if(Utilities.isError(auditItem.getErrorCode()))
//                    {
//                        p.setProcessed("Procesado con error");
//                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
//                        db_product.editar(p);
//                    }
//
//                    else
//                    {
//                        p.setProcessed("Procesado");
//                        db_product.editar(p);
//                    }
//                }
//            }
//        }
//    }

    public void verificarProductos()
    {
        List<Product> productList = db_product.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> auditItemList;

        for (Product p : productList)
        {
            StringBuilder errorDescription = new StringBuilder();
            auditItemList = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.I);

            for (AuditItem auditItem : auditItemList)
            {
                p.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            auditItemList = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : auditItemList)
            {
                p.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            auditItemList = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : auditItemList)
            {
                p.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            p.setErrorDescription(errorDescription.toString());
            p.setProcessed(setProcessed(p.getErrorDescription()));

            db_product.editar(p);
        }
    }

    public void verificarPrecios()
    {
//        List<Price> priceList = db_price.filtrarPor(Filtro.SIN_FILTRAR);
//
//        for (Price p : priceList)
//        {
//            List<AuditItem> listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(),p.getImportOrigin());
//
//            for (AuditItem auditItem : listaRegistrosAuditoria)
//            {
//                p.setEmpresa(auditItem.getEmpresa());
//                if (p.getProductCode().equals(auditItem.getProductCode()) && p.getImportOrigin().equals(auditItem.getImportOrigin())) {
//                    if(Utilities.isError(auditItem.getErrorCode()))
//                    {
//                        p.setProcessed("Procesado con error");
//                        p.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
//                        db_price.editar(p);
//                    }
//
//                    else
//                    {
//                        p.setProcessed("Procesado");
//                        db_price.editar(p);
//                    }
//                }
//            }
//        }

        List<AuditItem> listaRegistrosAuditoria;
        List<Price> priceList = db_price.filtrarPor(Filtro.SIN_FILTRAR);

        for (Price p : priceList)
        {
            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            p.setErrorDescription(errorDescription.toString());
            p.setProcessed(setProcessed(p.getErrorDescription()));

            db_price.editar(p);
        }
    }

    public void verificarStock()
    {
//        List<Stock> stockList = db_stock.filtrarPor(Filtro.SIN_FILTRAR);
//
//        for (Stock stock : stockList)
//        {
//            List<AuditItem> auditItemList = db_audit.getRegistro(stock.getProductCode(),stock.getImportOrigin());
//
//            for (AuditItem auditItem : auditItemList)
//            {
//                stock.setEmpresa(auditItem.getEmpresa());
//                if (stock.getProductCode().equals(auditItem.getProductCode()) && stock.getImportOrigin().equals(auditItem.getImportOrigin()))
//                {
//                    if(Utilities.isError(auditItem.getErrorCode()))
//                    {
//                        stock.setProcessed("Procesado con error");
//                        stock.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
//                        db_stock.editar(stock);
//                    }
//
//                    else
//                    {
//                        stock.setProcessed("Procesado");
//                        db_stock.editar(stock);
//                    }
//                }
//            }
//        }

        List<Stock> stockList = db_stock.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Stock stock : stockList)
        {
            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                stock.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                stock.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            stock.setErrorDescription(errorDescription.toString());
            stock.setProcessed(setProcessed(stock.getErrorDescription()));

            db_stock.editar(stock);
        }
    }

    public void verificarMedia()
    {
//        List<Media> mediaList = db_media.filtrarPor(Filtro.SIN_FILTRAR);
//
//        for (Media media : mediaList)
//        {
//            List<AuditItem> auditItemList = db_audit.getRegistro(media.getProductCode(),media.getImportOrigin());
//
//            for (AuditItem auditItem : auditItemList)
//            {
//                media.setEmpresa(auditItem.getEmpresa());
//                if (media.getProductCode().equals(auditItem.getProductCode()) && media.getImportOrigin().equals(auditItem.getImportOrigin()))
//                {
//                    if(Utilities.isError(auditItem.getErrorCode()))
//                    {
//                        media.setProcessed("Procesado con error");
//                        media.setErrorDescription(auditItem.getErrorCode() + ": "+ auditItem.getDescription());
//                        db_media.editar(media);
//                    }
//
//                    else
//                    {
//                        media.setProcessed("Procesado");
//                        db_media.editar(media);
//                    }
//                }
//            }
//        }

        List<Media> mediaList = db_media.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Media media : mediaList)
        {
            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                media.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                media.setEmpresa(auditItem.getEmpresa());
                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            media.setErrorDescription(errorDescription.toString());
            media.setProcessed(setProcessed(media.getErrorDescription()));

            db_media.editar(media);
        }
    }



    private String setProcessed(String errorDescription)
    {
        String processed = "";
        Pattern patternProcesadoOk = Pattern.compile("I\\d{2}");
        Pattern patternProcesadoError = Pattern.compile("E\\d{2}");
        Pattern patternProcesadoWarning = Pattern.compile("W\\d{2}");
        Matcher matcherProcesadoOk = patternProcesadoOk.matcher(errorDescription);
        Matcher matcherProcesadoError = patternProcesadoError.matcher(errorDescription);
        Matcher matcherProcesadoWarning = patternProcesadoWarning.matcher(errorDescription);

        Boolean procesadoOk = matcherProcesadoOk.find();
        Boolean procesadoWarning = matcherProcesadoWarning.find();
        Boolean procesadoError = matcherProcesadoError.find();


        if(procesadoOk && !procesadoError && !procesadoWarning)
            processed = "Procesado";

        else if((procesadoOk && procesadoWarning) || procesadoError)
            processed = "Procesado con error";

        return processed;
    }
}