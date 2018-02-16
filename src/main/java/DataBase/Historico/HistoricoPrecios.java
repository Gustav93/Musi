package DataBase.Historico;

import DataBase.DBConectionManager;
import DataBase.Feed;
import Feed.Price;
import DataBase.Contador;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPrecios
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_precios (productCode VARCHAR(50), onlinePrice VARCHAR(50), currency VARCHAR(50), storePrice VARCHAR(50), hasPriority VARCHAR(50), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("La tabla HISTORICO_PRECIOS ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndiceHistorico(Feed.PRECIO);
    }

//    public void eliminarTabla()
//    {
//        //language=SQL
//        String query = "drop table historico_precios";
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.execute();
//            DBConectionManager.commit(c);
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Hubo un problema al eliminar la tabla HISTORICO_PRECIOS");
//        }
//
//        DBConectionManager.closeConnection(c);
//    }

//    public void crearRegistro(Price price)
//    {
//        String query = "insert into historico_precios (productCode, onlinePrice, currency, storePrice, hasPriority, importOrigin, processed, errorDescription, empresa) values (?,?,?,?,?,?,?,?,?)";
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.setString(1, price.getProductCode());
//            ps.setString(2, price.getOnlinePrice());
//            ps.setString(3, price.getCurrency());
//            ps.setString(4, price.getStorePrice());
//            ps.setString(5, price.getHasPriority());
//            ps.setString(6, price.getImportOrigin());
//            ps.setString(7, price.getProcessed());
//            ps.setString(8, price.getErrorDescription());
//            ps.setString(9, price.getEmpresa());
//
//            ps.executeUpdate();
//
//            DBConectionManager.commit(c);
//        }
//        catch (Exception e)
//        {
//            DBConectionManager.rollback(c);
//        }
//        finally
//        {
//            DBConectionManager.closeConnection(c);
//        }
//
//    }

    public List<Price> getRegistros(String productCode) {
        List<Price> priceList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "select * from historico_precios where productCode = ?";

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                Price price = new Price();
                price.setProductCode(productCode);
                price.setOnlinePrice(res.getString(2));
                price.setCurrency(res.getString(3));
                price.setStorePrice(res.getString(4));
                price.setHasPriority(res.getString(5));
                price.setImportOrigin(res.getString(6));
                price.setProcessed(res.getString(7));
                price.setErrorDescription(res.getString(8));
                price.setEmpresa(res.getString(9));
                priceList.add(price);
            }
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            DBConectionManager.closeConnection(c);

        }
        return priceList;
    }

    public void importarPrecios() {
        //language=SQL
        String query = "insert into historico_precios select * from price";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);
    }

    public int getCantidadRegistrosProcesados(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.PROCESADO);
    }

    public int getCantidadRegistrosNoProcesados(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.SIN_PROCESAR);
    }

    public int getCantidadRegistrosProcesadosConError(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.PROCESADO_CON_ERROR);
    }

    //devuelve la cantidad de registros con el codigo de producto pasado como parametro
    public int getCantidadRegistros(String codigoProducto)
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from historico_precios where productCode like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, codigoProducto);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                total = res.getInt(1);
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

        return total;
    }

    private int getCantidadRegistros(String codigoProducto, Contador estadoProcesado)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;

        String query = null;

        if(estadoProcesado.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from historico_precios where productCode like ? and processed like 'Procesado'";

        else if(estadoProcesado.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from historico_precios where productCode like ? and processed like 'Procesado con Error'";

        else if(estadoProcesado.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_precios where productCode like ? and processed like 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, codigoProducto);
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
}