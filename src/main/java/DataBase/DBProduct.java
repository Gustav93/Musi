package DataBase;

import Feed.Product;
import Reporte.Reporte;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBProduct
{
    private final String CREATE_TABLE_PRODUCT = "CREATE TABLE PRODUCT (code VARCHAR(50), ean VARCHAR(50), brand VARCHAR(50), name VARCHAR(100), category VARCHAR(100), weight VARCHAR(50) , onlineDateTime VARCHAR(50), offlineDateTime VARCHAR(50), approvalStatus VARCHAR(50), description VARCHAR(500), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(500), empresa VARCHAR(10))";
    private final String DELETE_TABLE_PRODUCT = "DROP TABLE PRODUCT";
    private final String INSERT_PRODUCT = "INSERT INTO PRODUCT (code, ean, brand, name, category, weight, onlineDateTime, offlineDateTime, approvalStatus, Description, importOrigin, processed, errorDescription, empresa) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String GET_PRODUCT = "SELECT * FROM PRODUCT WHERE code = ?";
    private final String PRODUCT_LIST = "SELECT * FROM PRODUCT";
    private final String EDIT = "UPDATE PRODUCT SET processed = ?, errorDescription = ?, empresa = ? WHERE code = ? ";
    private final String FILTER_BY_NOT_PROCESSED = "SELECT * FROM PRODUCT WHERE processed = 'Sin Procesar'";
    private final String FILTER_BY_PROCESSED = "SELECT * FROM PRODUCT WHERE processed = 'Procesado'";
    private final String FILTER_BY_ERROR = "SELECT * FROM PRODUCT WHERE processed = 'Procesado con error'";
    private final String ADD_INDEX = "ALTER TABLE PRODUCT ADD INDEX indiceProduct (code, importOrigin)";
    private final String FILTER_BY_NOT_PROCESSED_OK = "SELECT * FROM PRODUCT WHERE processed = 'Procesado con error' OR processed = 'Sin Procesar'";
    private final String GET_IMPORT_ORIGIN = "SELECT importOrigin FROM PRODUCT GROUP BY importOrigin";

    public void createTable()
    {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_PRODUCT);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("La tabla PRODUCT ya existe");
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
            ps.setString(14, p.getEmpresa());

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

    public Product getProduct(String code) {

        Product p = new Product();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_PRODUCT);
            ps.setString(1, code);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
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
                p.setEmpresa(res.getString(14));
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
        return p;
    }

    public List<Product> getProductList()
    {
        return filterBy(PRODUCT_LIST);
    }

    public void edit(Product p)
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(EDIT);

            ps.setString(1, p.getProcessed());
            ps.setString(2, p.getErrorDescription());
            ps.setString(3, p.getEmpresa());
            ps.setString(4, p.getCode());

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

    public List<Product> filterByNotProcessed()
    {
        return filterBy(FILTER_BY_NOT_PROCESSED);
    }

    public List<Product> filterByProcessed()
    {
        return filterBy(FILTER_BY_PROCESSED);
    }

    public List<Product> filterByError()
    {
        return filterBy(FILTER_BY_ERROR);
    }

    public List<Product> filterByNotProcessedOk(){ return  filterBy(FILTER_BY_NOT_PROCESSED_OK);}

    private List<Product> filterBy(String query)
    {
        List<Product> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
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
                p.setEmpresa(res.getString(14));

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

    public int getNumberTotal()
    {
        return getProductList().size();
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
            String query = "select count(*) from product where importOrigin like ? and processed like ?";
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
            String query = "select count(*) from product where importOrigin like ?";
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
