package DataBase.Historico;

import DataBase.DBConectionManager;
import Feed.Stock;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoStock
{
    private final String CREATE_TABLE_STOCK = "CREATE TABLE HISTORICO_STOCK (productCode VARCHAR(50), stock VARCHAR(100), warehouse VARCHAR(100), status VARCHAR(100), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200))";
    private final String DELETE_TABLE_STOCK = "DROP TABLE HISTORICO_STOCK";
    private final String INSERT_STOCK = "INSERT INTO HISTORICO_STOCK (productCode, stock, warehouse, status, importOrigin, processed, errorDescription) VALUES (?,?,?,?,?,?,?)";
    private final String GET_STOCK = "SELECT * FROM HISTORICO_STOCK WHERE productCode = ?";
    private final String STOCK_LIST = "SELECT * FROM HISTORICO_STOCK";
    private final String EDIT = "UPDATE HISTORICO_STOCK SET processed = ?, errorDescription = ? WHERE productCode = ? ";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM HISTORICO_STOCK WHERE productCode = ? AND processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM HISTORICO_STOCK WHERE productCode = ? AND processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM HISTORICO_STOCK WHERE productCode = ? AND processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE HISTORICO_STOCK ADD INDEX indiceHistoricoStock (productCode)";
    private final String IMPORTAR_STOCK = "INSERT INTO HISTORICO_STOCK SELECT * FROM STOCK";

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
            System.out.println("La tabla HISTORICO_STOCK ya existe");
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

    public List<Stock> getStock(String productCode) {

        List<Stock> stockList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(GET_STOCK);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Stock stock = new Stock();
                stock.setProductCode(productCode);
                stock.setStock(res.getString(2));
                stock.setWarehouse(res.getString(3));
                stock.setStatus(res.getString(4));
                stock.setImportOrigin(res.getString(5));
                stock.setProcessed(res.getString(6));
                stock.setErrorDescription(res.getString(7));
                stockList.add(stock);
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
        return stockList;
    }

//    public List<Stock> getStockList(String codigoProducto)
//    {
//        return filterBy(STOCK_LIST, codigoProducto);
//    }


    public List<Stock> filterByNotProcessed(String codigoProducto)
    {
        return filterBy(FILTER_BY_NOT_PROCESSED, codigoProducto);
    }

    public List<Stock> filterByProcessed(String codigoProducto)
    {
        return filterBy(FILTER_BY_PROCESSED, codigoProducto);
    }

    public List<Stock> filterByError(String codigoProducto)
    {
        return filterBy(FILTER_BY_ERROR, codigoProducto);
    }



    private List<Stock> filterBy(String query, String codigoProducto)
    {
        List<Stock> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, codigoProducto);
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

    public int getNumberStock(String codigoProducto)
    {
        return getStock(codigoProducto).size();
    }

    public int getNumberProcessed(String codigoProducto)
    {
        return filterByProcessed(codigoProducto).size();
    }

    public int getNumberNotProcessed(String codigoProducto)
    {
        return filterByNotProcessed(codigoProducto).size();
    }

    public int getNumberProcessedError(String codigoProducto)
    {
        return filterByError(codigoProducto).size();
    }

    public void importarStock()
    {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(IMPORTAR_STOCK);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }
}
