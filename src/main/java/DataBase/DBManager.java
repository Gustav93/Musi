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
    //Para verificar los registros primero traigo la lista de productos que van a ser procesados luego recorro dicha
    //lista y por cada elemento me traigo de la auditoria los registros que tienen el mismo codigo de produto, archivo de
    // importacion y ademas el tipo de error separados en 3 listas (I, W, E). De esta manera,si existe un producto que
    // tiene I30 y warning, solamente se almacenara la descripcion del warning
    public void verificarProductos()
    {
        List<Product> listaProductos = db_product.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Product p : listaProductos)
        {
//            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.I);

            //los breaks los hago porque si la lista tiene mas de 1 elemento, son todos iguales (de momento, puede
            //existir un caso en el que no pero todavia no pasom por eso la aclaracion)
            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado");
//                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado con error");
//                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado con error");
//                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

//            p.setErrorDescription(errorDescription.toString());
//            p.setProcessed(setProcessed(p.getErrorDescription()));

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
        List<Price> listaPrecios = db_price.filtrarPor(Filtro.SIN_FILTRAR);

        for (Price p : listaPrecios)
        {
//            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado");
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado con error");
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado con error");
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

//            p.setErrorDescription(errorDescription.toString());
//            p.setProcessed(setProcessed(p.getErrorDescription()));

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
//            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado");
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado con error");
//                errorDescription.append(auditItem.getErrorCode() + ": " + auditItem.getDescription() + " ");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado con error");
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                break;
            }

//            stock.setErrorDescription(errorDescription.toString());
//            stock.setProcessed(setProcessed(stock.getErrorDescription()));

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
//            StringBuilder errorDescription = new StringBuilder();
            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.I);


            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
//                errorDescription.append(registro.getErrorCode() + ": " + registro.getDescription() + " ");
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado con error");
                break;
            }

//            media.setErrorDescription(errorDescription.toString());
//            media.setProcessed(setProcessed(media.getErrorDescription()));

            db_media.editar(media);
        }
    }


    //busco en la descripcion del error el tipo de error que se genero para que en base a eso devolver si el registro
    //fue procesado correctamente o con error.
//    private String setProcessed(String errorDescription)
//    {
//        String processed = "Sin Procesar";
//        Pattern patternProcesadoOk = Pattern.compile("I\\d{2}"); //matchea por ejemplo con I30
//        Pattern patternProcesadoError = Pattern.compile("E\\d{2}"); //matchea por ejemplo con E37
//        Pattern patternProcesadoWarning = Pattern.compile("W\\d{2}"); // matchea por ejemplo con W22
//        Matcher matcherProcesadoOk = patternProcesadoOk.matcher(errorDescription);
//        Matcher matcherProcesadoError = patternProcesadoError.matcher(errorDescription);
//        Matcher matcherProcesadoWarning = patternProcesadoWarning.matcher(errorDescription);
//
//        Boolean procesadoOk = matcherProcesadoOk.find();
//        Boolean procesadoWarning = matcherProcesadoWarning.find();
//        Boolean procesadoError = matcherProcesadoError.find();
//
//        //si solo encontro en la descripcion del error un I** y no E** ni W** es porque el registro fue procesado
//        //correctamente.
//        if(procesadoOk && !procesadoError && !procesadoWarning)
//            processed = "Procesado";
//
//        //si en la descripcion del error hay un I** y W**, el registro se proceso pero con error. En cambio si se
//        //encuentra un E** el registro se intento procesar pero lanzo un error.
//        else if(procesadoWarning || procesadoError)
//            processed = "Procesado con error";
//
//        return processed;
//    }
}