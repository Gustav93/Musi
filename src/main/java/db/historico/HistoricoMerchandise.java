package db.historico;

import utilidades.enums.Contador;
import db.conexiones.DBConectionManager;
import utilidades.enums.TipoFeed;
import utilidades.Utilities;
import Feeds.Merchandise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoMerchandise
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_merchandise (source VARCHAR(50), refType VARCHAR(50), target VARCHAR(50), relacion VARCHAR(50), qualifier VARCHAR(50), preselected VARCHAR(50), origenImportacion VARCHAR(100), estadoProcesamiento VARCHAR(100), descripcionError VARCHAR(600), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("La tabla HISTORICO_MERCHANDISE ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndiceHistorico(TipoFeed.MERCHANDISE);
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

    public List<Merchandise> getRegistros(String source)
    {
        List<Merchandise> listaMerchandise = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "select * from historico_merchandise where source = ?";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, source);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Merchandise m = new Merchandise();
                m.setCodigoProducto(source);
                m.setRefType(res.getString(2));
                m.setTarget(res.getString(3));
                m.setRelacion(res.getString(4));
                m.setQualifier(res.getString(5));
                m.setPreselected(res.getString(6));
                m.setOrigenImportacion(res.getString(7));
                m.setEstadoProcesamiento(res.getString(8));
                m.setDescripcionError(res.getString(9));
                m.setEmpresa(res.getString(10));

                listaMerchandise.add(m);
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
        return listaMerchandise;
    }

    public void importarMerchandise()
    {
        //language=SQL
        String query = "insert into historico_merchandise select * from merchandise";
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

        DBConectionManager.closeConnection(c);
    }

    public int getCantidadRegistrosProcesados(String source)
    {
        return getCantidadRegistros(source, Contador.PROCESADO);
    }

    public int getCantidadRegistrosNoProcesados(String source)
    {
        return getCantidadRegistros(source, Contador.SIN_PROCESAR);
    }

    public int getCantidadRegistrosNoProcesadosCorrectamente(String source)
    {
        return getCantidadRegistros(source, Contador.NO_PROCESADO_CORRECTAMENTE);
    }

    //devuelve la cantidad de registros con el codigo de producto pasado como parametro
    public int getCantidadRegistros(String source)
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from historico_merchandise where source like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, source);
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

    private int getCantidadRegistros(String source, Contador estadoProcesamiento)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;

        String query = null;

        if(estadoProcesamiento.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from historico_merchandise where source like ? and estadoProcesamiento like 'Procesado'";

        else if(estadoProcesamiento.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from historico_merchandise where source like ? and estadoProcesamiento like 'Procesado con Error' or estadoProcesamiento like 'Procesado con Warning'";

        else if(estadoProcesamiento.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_merchandise where source like ? and estadoProcesamiento like 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, source);
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