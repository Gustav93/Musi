package DataBase.Historico;

import DataBase.DBConectionManager;
import Feed.Price;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPrecios
{
    private final String CREATE_TABLE_PRICE = "CREATE TABLE HISTORICO_PRECIOS (productCode VARCHAR(50), onlinePrice VARCHAR(50), currency VARCHAR(50), storePrice VARCHAR(50), hasPriority VARCHAR(50), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200))";
    private final String DELETE_TABLE_PRICE = " DROP TABLE HISTORICO_PRECIOS";
    private final String INSERT_PRICE = "INSERT INTO HISTORICO_PRECIOS (productCode, onlinePrice, currency, storePrice, hasPriority, importOrigin, processed, errorDescription) VALUES (?,?,?,?,?,?,?,?)";
    private final String GET_PRICE = "SELECT * FROM HISTORICO_PRECIOS WHERE productCode = ?";
    private final String PRICE_LIST = "SELECT * FROM HISTORICO_PRRECIOS";
    private final String EDIT = "UPDATE HISTORICO_PRECIOS SET processed = ?, errorDescription = ? WHERE productCode = ?";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM HISTORICO_PRECIOS WHERE processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM HISTORICO_PRECIOS WHERE processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM HISTORICO_PRECIOS WHERE processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE HISTORICO_PRECIOS ADD INDEX indiceHistoricoPrecios (productCode)";
    private final String IMPORTAR_PRECIOS = "INSERT INTO HISTORICO_PRECIOS SELECT * FROM PRICE";


    public void createTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_PRICE);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            System.out.println("La tabla HISTORICO_PRECIOS ya existe");
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

    public List<Price> getPrice(String productCode) {

        List<Price> priceList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try {

            PreparedStatement ps = c.prepareStatement(GET_PRICE);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Price price = new Price();
                price.setProductCode(productCode);
                price.setOnlinePrice(res.getString(2));
                price.setCurrency(res.getString(3));
                price.setStorePrice(res.getString(4));
                price.setHasPriority(res.getString(5));
                price.setImportOrigin(res.getString(6));
                price.setProcessed(res.getString(7));
                price.setErrorDescription(res.getString(8));
                priceList.add(price);
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
        return priceList;
    }

    public List<Price> getPriceList()
    {
        return filterBy(PRICE_LIST);
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

    public void importarPrecios()
    {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(IMPORTAR_PRECIOS);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }
}
