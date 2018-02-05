package DataBase;

import Feed.Price;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPrice
{
    private final String CREATE_TABLE_PRICE = "CREATE TABLE PRICE (productCode VARCHAR(50), onlinePrice VARCHAR(50), currency VARCHAR(50), storePrice VARCHAR(50), hasPriority VARCHAR(50), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
    private final String DELETE_TABLE_PRICE = " DROP TABLE PRICE";
    private final String INSERT_PRICE = "INSERT INTO PRICE (productCode, onlinePrice, currency, storePrice, hasPriority, importOrigin, processed, errorDescription, empresa) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String GET_PRICE = "SELECT * FROM PRICE WHERE productCode = ?";
    private final String PRICE_LIST = "SELECT * FROM PRICE";
    private final String EDIT = "UPDATE PRICE SET processed = ?, errorDescription = ?, empresa = ? WHERE productCode = ?";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM PRICE WHERE processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM PRICE WHERE processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM PRICE WHERE processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE PRICE ADD INDEX indicePrice (productCode, importOrigin)";


    public void createTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_PRICE);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            System.out.println("La tabla ya existe");
//            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);

        Utilities.createIndex(ADD_INDEX);

    }

    public void deleteTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_PRICE);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("La tabla no se puede borrar, no existe");
        }

        DBConectionManager.closeConnection(c);
    }

    public void createPrice(Price price) {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(INSERT_PRICE);
            ps.setString(1, price.getProductCode());
            ps.setString(2, price.getOnlinePrice());
            ps.setString(3, price.getCurrency());
            ps.setString(4, price.getStorePrice());
            ps.setString(5, price.getHasPriority());
            ps.setString(6, price.getImportOrigin());
            ps.setString(7, price.getProcessed());
            ps.setString(8, price.getErrorDescription());
            ps.setString(9, price.getEmpresa());

            ps.executeUpdate();

            DBConectionManager.commit(c);
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {

            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public Price getPrice(String productCode) {

        Price price = new Price();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_PRICE);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                price.setProductCode(productCode);
                price.setOnlinePrice(res.getString(2));
                price.setCurrency(res.getString(3));
                price.setStorePrice(res.getString(4));
                price.setHasPriority(res.getString(5));
                price.setImportOrigin(res.getString(6));
                price.setProcessed(res.getString(7));
                price.setErrorDescription(res.getString(8));
                price.setEmpresa(res.getString(9));
            }
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return price;
    }

    public List<Price> getPriceList()
    {
        return filterBy(PRICE_LIST);
    }

    public void edit(Price p)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(EDIT);

            ps.setString(1, p.getProcessed());
            ps.setString(2, p.getErrorDescription());
            ps.setString(3, p.getEmpresa());
            ps.setString(4, p.getProductCode());

            ps.executeUpdate();
            DBConectionManager.commit(c);

        }
        catch (Exception e)
        {
            DBConectionManager.rollback(c);
            e.printStackTrace();
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }
    }

    public List<Price> filterByNotProcessed()
    {
        return filterBy(FILTER_BY_NOT_PROCESSED);
    }

    public List<Price> filterByProcessed()
    {
        return filterBy(FILTER_BY_PROCESSED);
    }

    public List<Price> filterByError()
    {
        return filterBy(FILTER_BY_ERROR);
    }

    private List<Price> filterBy(String query)
    {
        List<Price> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Price price = new Price();

                price.setProductCode(res.getString(1));
                price.setOnlinePrice(res.getString(2));
                price.setCurrency(res.getString(3));
                price.setStorePrice(res.getString(4));
                price.setHasPriority(res.getString(5));
                price.setImportOrigin(res.getString(6));
                price.setProcessed(res.getString(7));
                price.setErrorDescription(res.getString(8));
                price.setEmpresa(res.getString(9));

                list.add(price);
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

    public int getNumberTotal()
    {
        return getPriceList().size();
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
}
