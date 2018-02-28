package DataBase;

import Feed.*;

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
        db_merchandise.crearTabla();

        for (Merchandise merch : merchandiseList)
            db_merchandise.crearRegistro(merch);
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
            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.I);

            //los breaks los hago porque si la lista tiene mas de 1 elemento, son todos iguales (de momento, puede
            //existir un caso en el que no pero todavia no pasom por eso la aclaracion)
            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem auditItem : listaRegistrosAuditoria)
            {
                p.setEmpresa(auditItem.getEmpresa());
                p.setErrorDescription(auditItem.getErrorCode() + ": " + auditItem.getDescription());
                p.setProcessed("Procesado con error");
                break;
            }

            db_product.editar(p);
        }
    }

    public void verificarPrecios()
    {
        List<AuditItem> listaRegistrosAuditoria;
        List<Price> listaPrecios = db_price.filtrarPor(Filtro.SIN_FILTRAR);

        for (Price p : listaPrecios)
        {
            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(p.getProductCode(), p.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria) {
                p.setEmpresa(registro.getEmpresa());
                p.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                p.setProcessed("Procesado con error");
                break;
            }

            db_price.editar(p);
        }
    }

    public void verificarStock()
    {
        List<Stock> stockList = db_stock.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Stock stock : stockList)
        {
            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(stock.getProductCode(), stock.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                stock.setEmpresa(registro.getEmpresa());
                stock.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                stock.setProcessed("Procesado con error");
                break;
            }

            db_stock.editar(stock);
        }
    }

    public void verificarMedia()
    {
        List<Media> mediaList = db_media.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Media media : mediaList)
        {
            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.I);


            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(media.getProductCode(), media.getImportOrigin(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                media.setEmpresa(registro.getEmpresa());
                media.setErrorDescription(registro.getErrorCode() + ": " + registro.getDescription());
                media.setProcessed("Procesado con error");
                break;
            }

            db_media.editar(media);
        }
    }

    public void verificarMerchandise()
    {
        List<Merchandise> listaMerchandise = db_merchandise.filtrarPor(Filtro.SIN_FILTRAR);
        List<AuditItem> listaRegistrosAuditoria;

        for (Merchandise m : listaMerchandise)
        {
            listaRegistrosAuditoria = db_audit.getRegistro(m.getSource(), m.getOrigenImportacion(), ErrorType.I);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                m.setEmpresa(registro.getEmpresa());
                m.setDescripcionError(registro.getErrorCode() + ": " + registro.getDescription());
                m.setEstadoProcesamiento("Procesado");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(m.getSource(), m.getOrigenImportacion(), ErrorType.W);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                m.setEmpresa(registro.getEmpresa());
                m.setDescripcionError(registro.getErrorCode() + ": " + registro.getDescription());
                m.setEstadoProcesamiento("Procesado con error");
                break;
            }

            listaRegistrosAuditoria = db_audit.getRegistro(m.getSource(), m.getOrigenImportacion(), ErrorType.E);

            for (AuditItem registro : listaRegistrosAuditoria)
            {
                m.setEmpresa(registro.getEmpresa());
                m.setDescripcionError(registro.getErrorCode() + ": " + registro.getDescription());
                m.setEstadoProcesamiento("Procesado con error");
                break;
            }

            db_merchandise.editar(m);
        }
    }
}