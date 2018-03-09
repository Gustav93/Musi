package db.temporal;
import utilidades.enums.Contador;
import db.conexiones.DBConectionManager;
import utilidades.enums.Filtro;
import utilidades.enums.TipoFeed;
import Feeds.*;
import utilidades.Reporte;
import utilidades.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMedia
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table media (productCode VARCHAR(50), codeMedia VARCHAR(100), isDefault VARCHAR(10), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(600), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla MEDIA ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndice(TipoFeed.MEDIA);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table media";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla MEDIA");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(Media media)
    {
        //language=SQL
        String query = "insert into media (productCode, codeMedia, isDefault, importOrigin, processed, errorDescription, empresa) values (?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, media.getCodigoProducto());
            ps.setString(2, media.getCodeMedia());
            ps.setString(3, media.getIsDefault());
            ps.setString(4, media.getOrigenImportacion());
            ps.setString(5, media.getEstadoProcesamiento());
            ps.setString(6, media.getDescripcionError());
            ps.setString(7, media.getEmpresa());

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

//    public Media getMedia(String productCode) {
//
//        Media media = new Media();
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(GET_MEDIA);
//            ps.setString(1, productCode);
//            ResultSet res = ps.executeQuery();
//
//            while (res.next()) {
//                media.setProductCode(productCode);
//                media.setCodeMedia(res.getString(2));
//                media.setIsDefault(res.getString(3));
//                media.setImportOrigin(res.getString(4));
//            }
//        } catch (Exception e) {
//            DBConectionManager.rollback(c);
//        } finally {
//            try {
//                DBConectionManager.closeConnection(c);
//            } catch (Exception e) {
//
//                e.printStackTrace();
//            }
//        }
//        return media;
//    }

    public void editar(Media media)
    {
        //language=SQL
        String query = "update media set processed = ?, errorDescription = ?, empresa = ? WHERE productCode = ? and importOrigin = ?";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, media.getEstadoProcesamiento());
            ps.setString(2, media.getDescripcionError());
            ps.setString(3,media.getEmpresa());
            ps.setString(4, media.getCodigoProducto());
            ps.setString(5, media.getOrigenImportacion());

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

    //devuelve una lista de registros filtrados por: sin filtrar, procesados correctamente, procesados con errores,
    //sin procesar y no procesados correctamente.
    public List<Media> filtrarPor(Filtro filtro)
    {
        List<Media> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "select * from media";

        else if(filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from media where processed = 'Procesado'";

        else if(filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "select * from media where processed = 'Procesado con Error'";

        else if(filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "select * from media where processed = 'Sin Procesar'";

        else if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from media where processed = 'Procesado con Error' or processed = 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Media media = new Media();

                media.setCodigoProducto(res.getString(1));
                media.setCodeMedia(res.getString(2));
                media.setIsDefault(res.getString(3));
                media.setOrigenImportacion(res.getString(4));
                media.setEstadoProcesamiento(res.getString(5));
                media.setDescripcionError(res.getString(6));
                media.setEmpresa(res.getString(7));

                list.add(media);
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

    public int getCantidadTotalRegistros()
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from media";
            PreparedStatement statement = c.prepareStatement(query);
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

    public int getCantidadRegistrosProcesados()
    {
        return getCantRegistros(Contador.PROCESADO);
    }

    public int getCantidadRegistrosNoProcesados()
    {
        return getCantRegistros(Contador.SIN_PROCESAR);
    }

    public int getCantidadRegistrosProcesadosConError()
    {
        return getCantRegistros(Contador.PROCESADO_CON_ERROR);
    }

    public int getCantidadRegistrosCARSA()
    {
        return getCantRegistros(Contador.CARSA);
    }

    public int getCantidadRegistrosEMSA()
    {
        return getCantRegistros(Contador.EMSA);
    }

    //devuelve la cantidad de registros filtrados (procesado, sin procesar y procesado con error) del nombre del archivo
    //pasado como parametro (importOrigin)
    private int getCantRegistros(Contador contador, String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;
        String query = null;

        if(contador.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from media where importOrigin like ? and processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from media where importOrigin like ? and processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from media where importOrigin like ? and processed like 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, nombreArchivo);
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

    //devuelve la cantidad de registros totales filtrados por: procesado, sin procesar, procesado con error, carsa
    // y emsa
    private int getCantRegistros(Contador contador)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;

        String query = null;

        if(contador.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from media where processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from media where processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from media where processed like 'Sin Procesar'";

        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from media where empresa like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from media where empresa like 'E'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
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

    //devuleve una lista de reportes, cada reporte generado es por cada archivo procesado
    public List<Reporte> getReportes()
    {
        List<Reporte> reportes = new ArrayList<>();
        List<String> nombreArchivos = Utilities.getImportOriginList(TipoFeed.MEDIA);
        for(String nombreArchivo : nombreArchivos)
        {
            Reporte reporte = new Reporte();

            reporte.setNombreArchivo(nombreArchivo);
            reporte.setTotalRegistros(getCantidadTotalRegistros(nombreArchivo));
            reporte.setNoProcesados(getCantRegistros(Contador.SIN_PROCESAR, nombreArchivo));
            reporte.setProcesadosConError(getCantRegistros(Contador.PROCESADO_CON_ERROR, nombreArchivo));
            reporte.setProcesadosCorrectamente(getCantRegistros(Contador.PROCESADO, nombreArchivo));

            reportes.add(reporte);
        }

        return reportes;
    }

    //Devuelve la cantidad de registros que tienen el nombre de archivo (pasado como parametro) en importOrigin
    private int getCantidadTotalRegistros(String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from media where importOrigin like ?";
            PreparedStatement statement = c.prepareStatement(query);
            statement.setString(1, nombreArchivo);
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
}