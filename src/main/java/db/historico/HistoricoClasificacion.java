package db.historico;

import Feeds.Classification;
import db.conexiones.DBConectionManager;
import utilidades.Utilities;
import utilidades.enums.Contador;
import utilidades.enums.TipoFeed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoClasificacion
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_clasificacion (codigoProducto VARCHAR(50), codigoAtributo VARCHAR(200), codigoCategoria VARCHAR(200), valorAtributo VARCHAR(200), origenImportacion VARCHAR(100), estadoProcesamiento VARCHAR(100), descripcionError VARCHAR(600), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("La tabla HISTORICO_CLASIFICACION ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndiceHistorico(TipoFeed.CLASIFICACION);
    }

//    public void eliminarTabla()
//    {
//        //language=SQL
//        String query = "drop table historico_clasificacion";
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.execute();
//            DBConectionManager.commit(c);
//        }
//        catch (SQLException e)
//        {
//            System.out.println("Hubo un problema al eliminar la tabla HISTORICO_CLASIFICACION");
//        }
//
//        DBConectionManager.closeConnection(c);
//    }
//
//    public void crearRegistro(Classification clasificacion)
//    {
//        String query = "insert into historico_clasificacion (codigoProducto, codigoAtributo, codigoCategoria, valorAtributo, origenImportacion, estadoProcesamiento, descripcionError, empresa) values (?,?,?,?,?,?,?,?)";
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.setString(1, clasificacion.getCodigoProducto());
//            ps.setString(2, clasificacion.getCodigoAtributo());
//            ps.setString(3, clasificacion.getCodigoCategoria());
//            ps.setString(4, clasificacion.getValorAtributo());
//            ps.setString(5, clasificacion.getOrigenImportacion());
//            ps.setString(6, clasificacion.getEstadoProcesamiento());
//            ps.setString(7, clasificacion.getDescripcionError());
//            ps.setString(8, clasificacion.getEmpresa());
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
//    }

    public List<Classification> getRegistros(String productCode)
    {
        List<Classification> listaClasificacion = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "select * from historico_clasificacion where codigoProducto = ?";

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Classification clasificacion = new Classification();
                clasificacion.setCodigoProducto(productCode);
                clasificacion.setCodigoAtributo(res.getString(2));
                clasificacion.setCodigoCategoria(res.getString(3));
                clasificacion.setValorAtributo(res.getString(4));
                clasificacion.setOrigenImportacion(res.getString(5));
                clasificacion.setEstadoProcesamiento(res.getString(6));
                clasificacion.setDescripcionError(res.getString(7));
                clasificacion.setEmpresa(res.getString(8));

                listaClasificacion.add(clasificacion);
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

        return listaClasificacion;
    }

    public void importarClasificacion() {
        //language=SQL
        String query = "insert into historico_clasificacion select * from clasificacion";
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
            String query = "select count(*) from historico_clasificacion where codigoProducto like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, codigoProducto);
            ResultSet res = statement.executeQuery();

            while(res.next())
                total = res.getInt(1);
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
            query = "select count(*) from historico_clasificacion where codigoProducto like ? and estadoProcesamiento like 'Procesado'";

        else if(estadoProcesado.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from historico_clasificacion where codigoProducto like ? and estadoProcesamiento like 'Procesado con Error' or estadoProcesamiento like 'Procesado con Warning'";

        else if(estadoProcesado.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_clasificacion where codigoProducto like ? and estadoProcesamiento like 'Sin Procesar'";

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
