package CSV;

import DataBase.DBPrice;
import DataBase.DBProduct;
import DataBase.DBStock;
import Feed.Price;
import Feed.Product;
import Feed.Stock;
import Utilities.Utilities;
import com.csvreader.CsvWriter;

import javax.rmi.CORBA.Util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class Writer
{
    private CsvWriter writer;
    private File file;

    public File getCsvProduct()
    {
        DBProduct dbProduct = new DBProduct();
        List<Product> productList = dbProduct.getProductList();
//        if(productList.size() == 0)
//            throw new RuntimeException("No hay productos para exportar");

        String archiveName = Utilities.nombreArchivoProcesadoProducto();
        try
        {
            file = new File(archiveName);
//            FileWriter fr = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fr);
//            bw.write("");
//            bw.close();

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

            for(Product p : productList)
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
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }


        return file;
    }

    public File getCsvStock()
    {
        DBStock dbStock = new DBStock();
        List<Stock> stockList = dbStock.getStockList();
//        if(stockList.size() == 0)
//            throw new RuntimeException("No hay stock para exportar");

        String archiveName = Utilities.nombreArchivoProcesadoStock();

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

            for(Stock stock : stockList)
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

    public File getCsvPriceList()
    {
        DBPrice dbPrice = new DBPrice();
        List<Price> priceList = dbPrice.getPriceList();
//        if(priceList.size() == 0)
//            throw new RuntimeException("No hay stock para exportar");

        String archiveName = Utilities.nombreArchivoProcesadoPrecio();

        try
        {
            file = new File(archiveName);

            FileWriter fileWriter = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fileWriter);
//            bw.write("");
//            bw.close();

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

            for(Price price : priceList)
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

    public File getCsvPriceListNotProcessedOk()
    {
        DBPrice dbPrice = new DBPrice();
        List<Price> priceList = dbPrice.filterByNotProcessedOk();
        if(priceList.size() == 0)
            throw new RuntimeException("No hay stock para exportar");

//        le agrego al nombre del archivo "(procesado)"
        String archiveName = Utilities.nombreArchivoNoProcesadoCorrectamentePrecio();

        try
        {
            file = new File(archiveName);

            FileWriter fileWriter = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fileWriter);
//            bw.write("");
//            bw.close();

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

            for(Price price : priceList)
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

    public File getCsvProductListNotProcessedOk()
    {
        DBProduct dbProduct = new DBProduct();
        List<Product> productList = dbProduct.filterByNotProcessedOk();
//        if(productList.size() == 0)
//            throw new RuntimeException("No hay productos para exportar");

        String archiveName = Utilities.nombreArchivoNoProcesadoCorrectamenteProducto();
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

            for(Product p : productList)
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
            fileWriter.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }

        return file;
    }

    public File getCsvStockListNotProcessedOk()
    {
        DBStock dbStock = new DBStock();
        List<Stock> stockList = dbStock.filterByNotProcessedOk();
//        if(stockList.size() == 0)
//            throw new RuntimeException("No hay stock para exportar");
//
////        le agrego al nombre del archivo "(procesado)"
        String archiveName = Utilities.nombreArchivoNoProcesadoCorrectamenteStock();

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

            for(Stock stock : stockList)
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
