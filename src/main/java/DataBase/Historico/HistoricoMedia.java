package DataBase.Historico;

import DataBase.DBConectionManager;
import DataBase.Feed;
import DataBase.Contador;
import Utilities.Utilities;
import Feed.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoMedia
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table historico_stock (productCode VARCHAR(50), codeMedia VARCHAR(100), isDefault VARCHAR(10), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla HISTORICO_MEDIA ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndiceHistorico(Feed.MEDIA);
    }

//    public void eliminarTabla()
//    {
//        //language=SQL
//        String query = "drop table historico_media";
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
//            System.out.println("Hubo un problema al eliminar la tabla HISTORICO_MEDIA");
//        }
//
//        DBConectionManager.closeConnection(c);
//    }

//    public void crearRegistro(Media media)
//    {
//        //language=SQL
//        String query = "insert into historico_media (productCode, codeMedia, isDefault, importOrigin) value (?,?,?,?,?,?,?)";
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(query);
//            ps.setString(1, media.getProductCode());
//            ps.setString(2, media.getCodeMedia());
//            ps.setString(3, media.getIsDefault());
//            ps.setString(4, media.getImportOrigin());
//            ps.setString(5, media.getProcessed());
//            ps.setString(6, media.getErrorDescription());
//            ps.setString(7, media.getEmpresa());
//
//            ps.executeUpdate();
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

    public List<Media> getRegistros(String productCode)
    {
        //language=SQL
        String query = "select * from historico_media where productCode = ?";
        List<Media> mediaList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try {

            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                Media media = new Media();
                media.setCodigoProducto(productCode);
                media.setCodeMedia(res.getString(2));
                media.setIsDefault(res.getString(3));
                media.setOrigenImportacion(res.getString(4));
                media.setEstadoProcesamiento(res.getString(5));
                media.setDescripcionError(res.getString(6));
                media.setEmpresa(res.getString(7));
                mediaList.add(media);
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

        return mediaList;
    }

    public void importarMedia()
    {
        //language=SQL
        String query = "insert into historico_media select * from media";
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
            String query = "select count(*) from historico_media where productCode like ?";
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
            query = "select count(*) from historico_media where productCode like ? and processed like 'Procesado'";

        else if(estadoProcesado.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from historico_media where productCode like ? and processed like 'Procesado con Error'";

        else if(estadoProcesado.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from historico_media where productCode like ? and processed like 'Sin Procesar'";

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
