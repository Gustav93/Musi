package db.historico;

import db.conexiones.DBConectionManager;
import utilidades.enums.TipoFeed;
import Feeds.Product;
import utilidades.enums.Contador;
import utilidades.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoProductos
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_productos (code VARCHAR(50), ean VARCHAR(50), brand VARCHAR(50), name VARCHAR(100), category VARCHAR(100), weight VARCHAR(50) , onlineDateTime VARCHAR(50), offlineDateTime VARCHAR(50), approvalStatus VARCHAR(50), description VARCHAR(500), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(500), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla HISTORICO_PRODUCTOS ya existe");
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }

        Utilities.crearIndiceHistorico(TipoFeed.PRODUCTO);
    }

//    public void eliminarTabla()
//    {
//        //language=SQL
//        String query = "drop table historico_productos";
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
//            System.out.println("Hubo un problema al eliminar la tabla HISTORICO_PRODUCTOS");
//        }
//
//        DBConectionManager.closeConnection(c);
//    }

    public List<Product> getRegistros(String code)
    {
        List<Product> productList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = "select * from historico_productos where code = ?";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, code);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Product p = new Product();
                p.setCodigoProducto(code);
                p.setEan(res.getString(2));
                p.setBrand(res.getString(3));
                p.setName(res.getString(4));
                p.setCategory(res.getString(5));
                p.setWeight(res.getString(6));
                p.setOnlineDateTime(res.getString(7));
                p.setOfflineDateTime(res.getString(8));
                p.setApprovalStatus(res.getString(9));
                p.setDescription(res.getString(10));
                p.setOrigenImportacion(res.getString(11));
                p.setEstadoProcesamiento(res.getString(12));
                p.setDescripcionError(res.getString(13));
                p.setEmpresa(res.getString(14));

                productList.add(p);
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

        return productList;
    }

    public void importarProductos()
    {
        //language=SQL
        String query = "insert into historico_productos select * from product";
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
            String query = "select count(*) from historico_productos where code like ?";
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
            query = "select count(*) from historico_productos where code like ? and processed like 'Procesado'";

        else if(estadoProcesado.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from historico_productos where code like ? and processed like 'Procesado con Error' or processed like 'Procesado con Warning'";

        else if(estadoProcesado.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_productos where code like ? and processed like 'Sin Procesar'";

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
