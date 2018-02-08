package DataBase;

import Feed.Stock;
import Reporte.Reporte;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBStock
{
    private final String CREATE_TABLE_STOCK = "CREATE TABLE STOCK (productCode VARCHAR(50), stock VARCHAR(100), warehouse VARCHAR(100), status VARCHAR(100), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
    private final String DELETE_TABLE_STOCK = "DROP TABLE STOCK";
    private final String INSERT_STOCK = "INSERT INTO STOCK (productCode, stock, warehouse, status, importOrigin, processed, errorDescription, empresa) VALUES (?,?,?,?,?,?,?,?)";
    private final String GET_STOCK = "SELECT * FROM STOCK WHERE code = ?";
    private final String STOCK_LIST = "SELECT * FROM STOCK";
    private final String EDIT = "UPDATE STOCK SET processed = ?, errorDescription = ?, empresa = ? WHERE productCode = ? ";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM STOCK WHERE processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM STOCK WHERE processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM STOCK WHERE processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE STOCK ADD INDEX indiceStock (productCode, importOrigin)";
    private final String FILTER_BY_NOT_PROCESSED_OK = "SELECT * FROM STOCK WHERE processed = 'Procesado con error' OR processed = 'Sin Procesar'";
    private final String GET_IMPORT_ORIGIN = "SELECT importOrigin FROM STOCK GROUP BY importOrigin";

    public void createTable()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_STOCK);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            System.out.println("La tabla STOCK ya existe");
//            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
        Utilities.createIndex(ADD_INDEX);
    }

    public void deleteTable()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_STOCK);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla");
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }

    public void createStock(Stock stock)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(INSERT_STOCK);
            ps.setString(1, stock.getProductCode());
            ps.setString(2, stock.getStock());
            ps.setString(3, stock.getWarehouse());
            ps.setString(4, stock.getStatus());
            ps.setString(5, stock.getImportOrigin());
            ps.setString(6, stock.getProcessed());
            ps.setString(7,stock.getErrorDescription());
            ps.setString(8, stock.getEmpresa());

            ps.executeUpdate();

            DBConectionManager.commit(c);
        }
        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }

    }

    public Stock getStock(String productCode) {

        Stock stock = new Stock();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(GET_STOCK);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                stock.setProductCode(productCode);
                stock.setStock(res.getString(2));
                stock.setWarehouse(res.getString(3));
                stock.setStatus(res.getString(4));
                stock.setImportOrigin(res.getString(5));
                stock.setProcessed(res.getString(6));
                stock.setErrorDescription(res.getString(7));
                stock.setEmpresa(res.getString(8));
            }
        }
        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }
        return stock;
    }

    public List<Stock> getStockList()
    {
        return filterBy(STOCK_LIST);
    }

    public void edit(Stock stock)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(EDIT);

            ps.setString(1, stock.getProcessed());
            ps.setString(2, stock.getErrorDescription());
            ps.setString(3,stock.getEmpresa());
            ps.setString(4, stock.getProductCode());

            ps.executeUpdate();
            DBConectionManager.commit(c);
        }
        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }

    }

    public List<Stock> filterByNotProcessed()
    {
        return filterBy(FILTER_BY_NOT_PROCESSED);
    }

    public List<Stock> filterByProcessed()
    {
        return filterBy(FILTER_BY_PROCESSED);
    }

    public List<Stock> filterByError()
    {
        return filterBy(FILTER_BY_ERROR);
    }

    public List<Stock> filterByNotProcessedOk(){ return  filterBy(FILTER_BY_NOT_PROCESSED_OK);}



    private List<Stock> filterBy(String query)
    {
        List<Stock> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Stock stock = new Stock();

                stock.setProductCode(res.getString(1));
                stock.setStock(res.getString(2));
                stock.setWarehouse(res.getString(3));
                stock.setStatus(res.getString(4));
                stock.setImportOrigin(res.getString(5));
                stock.setProcessed(res.getString(6));
                stock.setErrorDescription(res.getString(7));
                stock.setEmpresa(res.getString(8));

                list.add(stock);
            }
        }

        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }

        return list;
    }

    public int getNumberStockTotal()
    {
        return getStockList().size();
    }

    public int getNumberProcessed()
    {
        return filterByProcessed().size();
    }

    public int getNumberNotProcessed()
    {
        return filterByNotProcessed().size();
    }

    public int getNumberProcessedError()
    {
        return filterByError().size();
    }

    private List<String> getImportOriginList()
    {
        List<String> importOriginList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(GET_IMPORT_ORIGIN);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                importOriginList.add(res.getString(1));
            }
        }

        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }

        return importOriginList;
    }

    private int cantProcesados(String nombreArchivo, String procesado)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;

        try
        {
            String query = "select count(*) from stock where importOrigin like ? and processed like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, nombreArchivo);
            statement.setString(2, procesado);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                procesados = res.getInt(1);
            }
        }

        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }
        return procesados;
    }

    private int cantTotal(String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int totalProcesados = 0;

        try
        {
            String query = "select count(*) from stock where importOrigin like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, nombreArchivo);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                totalProcesados = res.getInt(1);
            }
        }

        catch (Exception e)
        {
            DBConectionManager.rollback(c);
        }
        finally
        {
            DBConectionManager.closeConnection(c);
        }
        return totalProcesados;
    }

    public List<Reporte> getReportes()
    {
        List<Reporte> reportes = new ArrayList<>();
        List<String> nombreArchivos = getImportOriginList();
        for(String nombreArchivo : nombreArchivos)
        {
            Reporte reporte = new Reporte();

            reporte.setNombreArchivo(nombreArchivo);
            reporte.setTotalRegistros(cantTotal(nombreArchivo));
            reporte.setNoProcesados(cantProcesados(nombreArchivo,"Sin Procesar"));
            reporte.setProcesadosConError(cantProcesados(nombreArchivo, "Procesado con error"));
            reporte.setProcesadosCorrectamente(cantProcesados(nombreArchivo, "Procesado"));

            reportes.add(reporte);
        }

        return reportes;
    }


}
