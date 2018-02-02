package DataBase.Historico;

import DataBase.DBConectionManager;
import Feed.Product;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoProductos
{
    private final String CREATE_TABLE_PRODUCT = "CREATE TABLE HISTORICO_PRODUCTOS (code VARCHAR(50), ean VARCHAR(50), brand VARCHAR(50), name VARCHAR(100), category VARCHAR(100), weight VARCHAR(50) , onlineDateTime VARCHAR(50), offlineDateTime VARCHAR(50), approvalStatus VARCHAR(50), description VARCHAR(500), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(500))";
    private final String DELETE_TABLE_PRODUCT = "DROP TABLE HISTORICO_PRODUCTOS";
    private final String INSERT_PRODUCT = "INSERT INTO HISTORICO_PRODUCTOS (code, " + "ean, brand, name, category, weight, onlineDateTime, offlineDateTime, approvalStatus, Description, importOrigin, processed, errorDescription) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String GET_PRODUCT = "SELECT * FROM HISTORICO_PRODUCTOS WHERE code = ?";
    private final String PRODUCT_LIST = "SELECT * FROM HISTORICO_PRODUCTOS";
    private final String EDIT = "UPDATE PRODUCT SET processed = ?, errorDescription = ? WHERE code = ? ";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM HISTORICO_PRODUCTOS WHERE code = ? AND processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM HISTORICO_PRODUCTOS WHERE code = ? AND processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM HISTORICO_PRODUCTOS WHERE code = ? AND processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE HISTORICO_PRODUCTOS ADD INDEX indiceProductosHistoricos (code)";
    private final String IMPORTAR_PRODUCTOS = "INSERT INTO HISTORICO_PRODUCTOS SELECT * FROM PRODUCT";

    public void createTable()
    {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_PRODUCT);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("La tabla HISTORICO_PRODUCTOS ya existe");
//            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);

        Utilities.createIndex(ADD_INDEX);
    }

    public void deleteTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_PRODUCT);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            DBConectionManager.closeConnection(c);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createProduct(Product p) {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(INSERT_PRODUCT);
            ps.setString(1, p.getCode());
            ps.setString(2, p.getEan());
            ps.setString(3, p.getBrand());
            ps.setString(4, p.getName());
            ps.setString(5, p.getCategory());
            ps.setString(6, p.getWeight());
            ps.setString(7, p.getOnlineDateTime());
            ps.setString(8, p.getOfflineDateTime());
            ps.setString(9, p.getApprovalStatus());
            ps.setString(10, p.getDescription());
            ps.setString(11, p.getImportOrigin());
            ps.setString(12, p.getProcessed());
            ps.setString(13, p.getErrorDescription());

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

    public List<Product> getProducts(String code) {

        List<Product> productList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_PRODUCT);
            ps.setString(1, code);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Product p = new Product();
                p.setCode(code);
                p.setEan(res.getString(2));
                p.setBrand(res.getString(3));
                p.setName(res.getString(4));
                p.setCategory(res.getString(5));
                p.setWeight(res.getString(6));
                p.setOnlineDateTime(res.getString(7));
                p.setOfflineDateTime(res.getString(8));
                p.setApprovalStatus(res.getString(9));
                p.setDescription(res.getString(10));
                p.setImportOrigin(res.getString(11));
                p.setProcessed(res.getString(12));
                p.setErrorDescription(res.getString(13));

                productList.add(p);
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
        return productList;
    }

//    public List<Product> getProductList()
//    {
//        return filterBy(PRODUCT_LIST);
//    }

    public List<Product> filterByNotProcessed(String codigoProducto)
    {
        return filterBy(FILTER_BY_NOT_PROCESSED, codigoProducto);
    }

    public List<Product> filterByProcessed(String codigoProducto)
    {
        return filterBy( FILTER_BY_PROCESSED, codigoProducto);
    }

    public List<Product> filterByError(String codigoProducto)
    {
        return filterBy(FILTER_BY_ERROR, codigoProducto);
    }

    private List<Product> filterBy(String query, String codigoProducto)
    {
        List<Product> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, codigoProducto);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Product p = new Product();

                p.setCode(res.getString(1));
                p.setEan(res.getString(2));
                p.setBrand(res.getString(3));
                p.setName(res.getString(4));
                p.setCategory(res.getString(5));
                p.setWeight(res.getString(6));
                p.setOnlineDateTime(res.getString(7));
                p.setOfflineDateTime(res.getString(8));
                p.setApprovalStatus(res.getString(9));
                p.setDescription(res.getString(10));
                p.setImportOrigin(res.getString(11));
                p.setProcessed(res.getString(12));
                p.setErrorDescription(res.getString(13));

                list.add(p);
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

//    public int getNumberTotal()
//    {
//        return getProductList().size();
//    }

    public int getNumberProducts(String codigoProducto){
        return getProducts(codigoProducto).size();
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

    public void importarProductos()
    {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(IMPORTAR_PRODUCTOS);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }
}
