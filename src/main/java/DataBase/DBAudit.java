package DataBase;

import Feed.AuditItem;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBAudit
{
    private final String EDIT = "UPDATE AUDIT SET importOrigin = ? WHERE productCode = ? ";
    private final String CREATE_TABLE_AUDIT = "CREATE TABLE AUDIT (auditLevel VARCHAR(50), auditType VARCHAR(50), auditDate VARCHAR(50), errorCode VARCHAR(50), description VARCHAR(500), empresa VARCHAR(50), productCode VARCHAR(50), importOrigin VARCHAR(100), feedType VARCHAR(50))";
    private final String DELETE_TABLE_AUDIT = "DROP TABLE AUDIT";
    private final String INSERT_AUDIT_ITEM = "INSERT INTO AUDIT (auditLevel, auditType, auditDate, errorCode, description, empresa, productCode, importOrigin, feedType) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String GET_AUDIT_ITEM = "SELECT * FROM AUDIT WHERE productCode = ? AND importOrigin = ?";
    private final String AUDIT_LIST = "SELECT * FROM AUDIT";
    private final String PRODUCT_LIST = "SELECT * FROM AUDIT WHERE feedType = 'PRODUCT'";
    private final String PRICE_LIST = "SELECT * FROM AUDIT WHERE feedType = 'PRICE'";
    private final String STOCK_LIST = "SELECT * FROM AUDIT WHERE feedType = 'STOCK'";
    private final String ADD_INDEX = "ALTER TABLE AUDIT ADD INDEX indiceAudit (productCode, importOrigin)";

    public void createTable()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_AUDIT);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {

            System.out.println("La tabla AUDIT ya existe");
        }

        DBConectionManager.closeConnection(c);

        Utilities.createIndex(ADD_INDEX);
    }

    public void deleteTable()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_AUDIT);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        try {
            DBConectionManager.closeConnection(c);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createAuditItem(AuditItem item) {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(INSERT_AUDIT_ITEM);
            ps.setString(1, item.getAuditLevel());
            ps.setString(2, item.getAuditType());
            ps.setString(3, item.getAuditDate());
            ps.setString(4, item.getErrorCode());
            ps.setString(5, item.getDescription());
            ps.setString(6, item.getEmpresa());
            ps.setString(7, item.getProductCode());
            ps.setString(8, item.getImportOrigin());
            ps.setString(9, item.getFeedType());
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

    public List<AuditItem> getAuditItem(String productCode, String importOrigin) {

        List<AuditItem> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_AUDIT_ITEM);
            ps.setString(1, productCode);
            ps.setString(2, importOrigin);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(productCode);
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                list.add(item);
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

    public List<AuditItem> getAuditItemList()
    {

        ArrayList<AuditItem> auditItemList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(AUDIT_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                auditItemList.add(item);
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

        return auditItemList;
    }

    public List<AuditItem> getProductList()
    {
        ArrayList<AuditItem> auditItemList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(PRODUCT_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                auditItemList.add(item);
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

        return auditItemList;
    }

    public List<AuditItem> getPriceList()
    {
        ArrayList<AuditItem> auditItemList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(PRICE_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                auditItemList.add(item);
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

        return auditItemList;
    }

    public List<AuditItem> getStockList()
    {
        ArrayList<AuditItem> auditItemList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(STOCK_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                auditItemList.add(item);
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

        return auditItemList;
    }

    private List<AuditItem> filterBy(String query)
    {
        List<AuditItem> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                AuditItem item = new AuditItem();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                list.add(item);
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


    public void edit(AuditItem item)
    {
        Connection c = DBConectionManager.openConnection();
        try
        {
            PreparedStatement ps = c.prepareStatement(EDIT);

            ps.setString(1, item.getImportOrigin());
            ps.setString(2, item.getProductCode());

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
}
