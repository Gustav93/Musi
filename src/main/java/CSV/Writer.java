package CSV;

import DataBase.*;
import Feed.Price;
import Feed.Product;
import Feed.Stock;
import Feed.Media;
import Utilities.Utilities;
import com.csvreader.CsvWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Writer
{
    private CsvWriter writer;
    private File file;

    public File getCsvProductList()
    {
        return getCsvProducto(Filtro.SIN_FILTRAR);
    }

    public File getCsvStockList()
    {
        return getCsvStock(Filtro.SIN_FILTRAR);
    }

    public File getCsvPriceList()
    {
        return getCsvPrecio(Filtro.SIN_FILTRAR);
    }

    public  File getCsvMediaList()
    {
        return getCsvMedia(Filtro.SIN_FILTRAR);
    }

    public File getCsvPriceListNotProcessedOk()
    {
        return getCsvPrecio(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvProductListNotProcessedOk()
    {
        return getCsvProducto(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvStockListNotProcessedOk()
    {
        return getCsvStock(Filtro.NO_PROCESADOS_CORRECTAMENTE);
    }

    public File getCsvMediaListNotProcessedOk()
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
                writer.write(media.getProductCode());
                writer.write(media.getCodeMedia());
                writer.write(media.getIsDefault());
                writer.write(media.getImportOrigin());
                writer.write(media.getProcessed());
                writer.write(media.getErrorDescription());
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
                writer.write(price.getProductCode());
                writer.write(price.getOnlinePrice());
                writer.write(price.getCurrency());
                writer.write(price.getStorePrice());
                writer.write(price.getHasPriority());
                writer.write(price.getImportOrigin());
                writer.write(price.getProcessed());
                writer.write(price.getErrorDescription());
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
                writer.write(p.getCode());
                writer.write(p.getEan());
                writer.write(p.getBrand());
                writer.write(p.getName());
                writer.write(p.getCategory());
                writer.write(p.getWeight());
                writer.write(p.getOnlineDateTime());
                writer.write(p.getOfflineDateTime());
                writer.write(p.getApprovalStatus());
                writer.write(p.getDescription());
                writer.write(p.getImportOrigin());
                writer.write(p.getProcessed());
                writer.write(p.getErrorDescription());
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
                writer.write(stock.getProductCode());
                writer.write(stock.getStock());
                writer.write(stock.getWarehouse());
                writer.write(stock.getStatus());
                writer.write(stock.getImportOrigin());
                writer.write(stock.getProcessed());
                writer.write(stock.getErrorDescription());
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
}