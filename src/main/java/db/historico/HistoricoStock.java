package db.historico;

import db.conexiones.DBConectionManager;
import utilidades.enums.TipoFeed;
import Feeds.Stock;
import utilidades.enums.Contador;
import utilidades.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoStock
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_stock (productCode VARCHAR(50), stock VARCHAR(100), warehouse VARCHAR(100), status VARCHAR(100), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla HISTORICO_STOCK ya existe");
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }

        Utilities.crearIndiceHistorico(TipoFeed.STOCK);
    }

//    public void eliminarTabla()
//    {
//        //language=SQL
//        String query = "drop table historico_stock";
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.execute();
//            DBConectionManager.commit(c);
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Hubo un problema al eliminar la tabla HISTORICO_STOCK");
//        }
//    }
//

    public List<Stock> getRegistros(String productCode)
    {
        //language=SQL
        String query = "select * from historico_stock where productCode = ?";
        List<Stock> stockList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Stock stock = new Stock();
                stock.setCodigoProducto(productCode);
                stock.setStock(res.getString(2));
                stock.setWarehouse(res.getString(3));
                stock.setStatus(res.getString(4));
                stock.setOrigenImportacion(res.getString(5));
                stock.setEstadoProcesamiento(res.getString(6));
                stock.setDescripcionError(res.getString(7));
                stock.setEmpresa(res.getString(8));
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

    public void importarStock()
    {
        //language=SQL
        String query = "insert into historico_stock select * from stock";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }
    }

    public int getCantidadRegistrosProcesados(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.PROCESADO);
    }

    public int getCantidadRegistrosNoProcesados(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.SIN_PROCESAR);
    }

    public int getCantidadRegistrosNoProcesadosCorrectamente(String codigoProducto)
    {
        return getCantidadRegistros(codigoProducto, Contador.NO_PROCESADO_CORRECTAMENTE);
    }

    //devuelve la cantidad de registros con el codigo de producto pasado como parametro
    public int getCantidadRegistros(String codigoProducto)
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from historico_stock where productCode like ?";
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
            query = "select count(*) from historico_stock where productCode like ? and processed like 'Procesado'";

        else if(estadoProcesado.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from historico_stock where productCode like ? and processed in ('Procesado con Error', 'Procesado con Warning')";

        else if(estadoProcesado.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_stock where productCode like ? and processed like 'Sin Procesar'";

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