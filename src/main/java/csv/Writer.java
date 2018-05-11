package csv;

import Feeds.*;
import utilidades.enums.Filtro;
import db.temporal.*;
import utilidades.Utilities;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Writer
{
    private CsvWriter writer;
    private File file;

    public File getCsvPreciosProcesadosCorrectamente()
    {
        return getCsvPrecio(Filtro.SIN_FILTRAR);
    }

    public File getCsvProductosProcesadosCorrectamente()
    {
        return getCsvProducto(Filtro.SIN_FILTRAR);
    }

    public File getCsvStockProcesadoCorrectamente()
    {
        return getCsvStock(Filtro.SIN_FILTRAR);
    }

    public  File getCsvMediaProcesadoCorrectamente()
    {
        return getCsvMedia(Filtro.SIN_FILTRAR);
    }

    public File getCsvMerchandiseProcesadoCorrectamente(){return getCsvMerchandise(Filtro.SIN_FILTRAR);}

    public File getCsvClasificacionProcesadoCorrectamente()
    {
        return getCsvClasificacion(Filtro.SIN_FILTRAR);
    }

    public File getCsvPreciosNoProcesadosCorrectamente()
    {
        return getCsvPrecio(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }


    public File getCsvProductosNoProcesadosCorrectamente()
    {
        return getCsvProducto(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvStockNoProcesadoCorrectamente()
    {
        return getCsvStock(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvClasificacionNoProcesadoCorrectamente()
    {
        return getCsvClasificacion(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvMerchandiseNoProcesadoCorrectamente()
    {
        return getCsvMerchandise(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvMediaNoProcesadoCorrectamente()
    {
        return getCsvMedia(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    //Devuelve un archivo csv que contriene los registros obtenidos de la db temporal, estos registros pueden ser
    //filtrados por no procesados correctamente (con errores y sin procesar) y sin filtrar
    //(la lista de registros entera).
    private File getCsvMedia(Filtro filtro)
    {
        List<Media> mediaList;
        DBMedia dbMedia = new DBMedia();
        String archiveName;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            mediaList = dbMedia.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            archiveName = Utilities.nombreArchivoNoProcesadoCorrectamenteMedia();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            mediaList = dbMedia.filtrarPor(Filtro.SIN_FILTRAR);
            archiveName = Utilities.nombreArchivoProcesadoMedia();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(archiveName);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Product Code");
            writer.write("Code Media");
            writer.write("Is Default");
            writer.write("Import Origin");
            writer.write("Processed");
            writer.write("Error Descriprion");
            writer.write("Empresa");
            writer.endRecord();

            for (Media media : mediaList)
            {
                writer.write(media.getCodigoProducto());
                writer.write(media.getCodeMedia());
                writer.write(media.getIsDefault());
                writer.write(media.getOrigenImportacion());
                writer.write(media.getEstadoProcesamiento());
                writer.write(media.getDescripcionError());
                writer.write(media.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }

    private File getCsvPrecio(Filtro filtro)
    {
        List<Price> priceList;
        DBPrice dbPrice = new DBPrice();
        String archiveName;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            priceList = dbPrice.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            archiveName = Utilities.nombreArchivoNoProcesadoCorrectamentePrecio();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            priceList = dbPrice.filtrarPor(Filtro.SIN_FILTRAR);
            archiveName = Utilities.nombreArchivoProcesadoPrecio();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(archiveName);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Product Code");
            writer.write("Online Price");
            writer.write("Currency");
            writer.write("Store Price");
            writer.write("Has Priority");
            writer.write("Import Origin");
            writer.write("Processed");
            writer.write("Error Descriprion");
            writer.write("Empresa");
            writer.endRecord();

            for (Price price : priceList)
            {
                writer.write(price.getCodigoProducto());
                writer.write(price.getOnlinePrice());
                writer.write(price.getCurrency());
                writer.write(price.getStorePrice());
                writer.write(price.getHasPriority());
                writer.write(price.getOrigenImportacion());
                writer.write(price.getEstadoProcesamiento());
                writer.write(price.getDescripcionError());
                writer.write(price.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }

    private File getCsvProducto(Filtro filtro)
    {
        List<Product> productList;
        DBProduct dbProduct = new DBProduct();
        String archiveName;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            productList = dbProduct.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            archiveName = Utilities.nombreArchivoNoProcesadoCorrectamenteProducto();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            productList = dbProduct.filtrarPor(Filtro.SIN_FILTRAR);
            archiveName = Utilities.nombreArchivoProcesadoProducto();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(archiveName);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Code");
            writer.write("Ean");
            writer.write("Brand");
            writer.write("Name");
            writer.write("Category");
            writer.write("Weight");
            writer.write("Online Date Time");
            writer.write("Offline Date Time");
            writer.write("Approval Status");
            writer.write("Description");
            writer.write("Import Origin");
            writer.write("Processed");
            writer.write("Error Descriprion");
            writer.write("Empresa");
            writer.endRecord();

            for (Product p : productList)
            {
                writer.write(p.getCodigoProducto());
                writer.write(p.getEan());
                writer.write(p.getBrand());
                writer.write(p.getName());
                writer.write(p.getCategory());
                writer.write(p.getWeight());
                writer.write(p.getOnlineDateTime());
                writer.write(p.getOfflineDateTime());
                writer.write(p.getApprovalStatus());
                writer.write(p.getDescription());
                writer.write(p.getOrigenImportacion());
                writer.write(p.getEstadoProcesamiento());
                writer.write(p.getDescripcionError());
                writer.write(p.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }

    private File getCsvStock(Filtro filtro)
    {
        List<Stock> stockList;
        DBStock dbStock = new DBStock();
        String archiveName;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            stockList = dbStock.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            archiveName = Utilities.nombreArchivoNoProcesadoCorrectamenteStock();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            stockList = dbStock.filtrarPor(Filtro.SIN_FILTRAR);
            archiveName = Utilities.nombreArchivoProcesadoStock();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(archiveName);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Product Code");
            writer.write("Stock");
            writer.write("Warehouse");
            writer.write("Status");
            writer.write("Import Origin");
            writer.write("Processed");
            writer.write("Error Descriprion");
            writer.write("Empresa");
            writer.endRecord();

            for (Stock stock : stockList)
            {
                writer.write(stock.getCodigoProducto());
                writer.write(stock.getStock());
                writer.write(stock.getWarehouse());
                writer.write(stock.getStatus());
                writer.write(stock.getOrigenImportacion());
                writer.write(stock.getEstadoProcesamiento());
                writer.write(stock.getDescripcionError());
                writer.write(stock.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }

    private File getCsvMerchandise(Filtro filtro)
    {
        List<Merchandise> listaMerchandise;
        DBMerchandise dbMerchandise = new DBMerchandise();
        String nombreArchivo;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            listaMerchandise = dbMerchandise.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            nombreArchivo = Utilities.nombreArchivoNoProcesadoCorrectamenteMerchandise();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            listaMerchandise = dbMerchandise.filtrarPor(Filtro.SIN_FILTRAR);
            nombreArchivo = Utilities.nombreArchivoProcesadoMerchandise();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(nombreArchivo);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Source");
            writer.write("Ref Type");
            writer.write("Target");
            writer.write("Relacion");
            writer.write("Qualifier");
            writer.write("Preselected");
            writer.write("Origen Importacion");
            writer.write("Estado Procesamiento");
            writer.write("Descripcion del Error");
            writer.write("Empresa");
            writer.endRecord();

            for (Merchandise m : listaMerchandise)
            {
                writer.write(m.getCodigoProducto());
                writer.write(m.getRefType());
                writer.write(m.getTarget());
                writer.write(m.getRelacion());
                writer.write(m.getQualifier());
                writer.write(m.getPreselected());
                writer.write(m.getOrigenImportacion());
                writer.write(m.getEstadoProcesamiento());
                writer.write(m.getDescripcionError());
                writer.write(m.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }

    private File getCsvClasificacion(Filtro filtro)
    {
        List<Classification> listaClasificacion;
        DBClasificacion dbclasificacion = new DBClasificacion();
        String nombreArchivo;

        if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
        {
            listaClasificacion = dbclasificacion.filtrarPor(Filtro.NO_PROCESADOS_CORRECTAMENTE);
            nombreArchivo = Utilities.nombreArchivoNoProcesadoCorrectamenteClasificacion();
        }

        else if(filtro.equals(Filtro.SIN_FILTRAR))
        {
            listaClasificacion = dbclasificacion.filtrarPor(Filtro.SIN_FILTRAR);
            nombreArchivo = Utilities.nombreArchivoProcesadoClasificacion();
        }

        else
            throw new IllegalArgumentException("Filtro incorrecto");

        try
        {
            file = new File(nombreArchivo);
            FileWriter fileWriter = new FileWriter(file, true);
            writer = new CsvWriter(fileWriter, ',');

            writer.write("Codigo de Producto");
            writer.write("Codigo del Atributo");
            writer.write("Codigo de Categoria");
            writer.write("Valor del Atributo");
            writer.write("Origen Importacion");
            writer.write("Estado Procesamiento");
            writer.write("Descripcion del Error");
            writer.write("Empresa");
            writer.endRecord();

            for (Classification clasificacion : listaClasificacion)
            {
                writer.write(clasificacion.getCodigoProducto());
                writer.write(clasificacion.getCodigoAtributo());
                writer.write(clasificacion.getCodigoCategoria());
                writer.write(clasificacion.getValorAtributo());
                writer.write(clasificacion.getOrigenImportacion());
                writer.write(clasificacion.getEstadoProcesamiento());
                writer.write(clasificacion.getDescripcionError());
                writer.write(clasificacion.getEmpresa());
                writer.endRecord();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            writer.close();
        }

        return file;
    }
}