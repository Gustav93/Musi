package db.temporal;

import Feeds.Classification;
import Feeds.Media;
import db.conexiones.DBConectionManager;
import utilidades.Reporte;
import utilidades.Utilities;
import utilidades.enums.Contador;
import utilidades.enums.Filtro;
import utilidades.enums.TipoFeed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBClassification
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table classification (codigoProducto VARCHAR(50), codigoAtributo VARCHAR(200), codigoCategoria VARCHAR(200), valorAtributo VARCHAR(200), origenImportacion VARCHAR(100), estadoProcesamiento VARCHAR(100), descripcionError VARCHAR(600), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla CLASSIFICATION ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndice(TipoFeed.CLASSIFICATION);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table classification";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla CLASSIFICATION");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(Classification classification)
    {
        //language=SQL
        String query = "insert into classification (codigoProducto, codigoAtributo, codigoCategoria, valorAtributo, origenImportacion, estadoProcesamiento, descripcionError, empresa) values (?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, classification.getCodigoProducto());
            ps.setString(2, classification.getCodigoAtributo());
            ps.setString(3, classification.getCodigoCategoria());
            ps.setString(4, classification.getValorAtributo());
            ps.setString(5, classification.getOrigenImportacion());
            ps.setString(6, classification.getEstadoProcesamiento());
            ps.setString(7, classification.getDescripcionError());
            ps.setString(8, classification.getEmpresa());

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

    public void editar(Classification classification)
    {
        //language=SQL
        String query = "update classification set estadoProcesamiento = ?, descripcionError = ?, empresa = ? WHERE codigoProducto = ? and origenImportacion = ?";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, classification.getEstadoProcesamiento());
            ps.setString(2, classification.getDescripcionError());
            ps.setString(3,classification.getEmpresa());
            ps.setString(4, classification.getCodigoProducto());
            ps.setString(5, classification.getOrigenImportacion());

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
    public List<Classification> filtrarPor(Filtro filtro)
    {
        List<Classification> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "select * from classification";

        else if(filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from classification where estadoProcesamiento = 'Procesado'";

        else if(filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "select * from classification where estadoProcesamiento = 'Procesado con Error'";

        else if(filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "select * from classification where estadoProcesamiento = 'Sin Procesar'";

        else if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from classification where estadoProcesamiento = 'Procesado con Error' or estadoProcesamiento = 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Classification classification = new Classification();

                classification.setCodigoProducto(res.getString(1));
                classification.setCodigoAtributo(res.getString(2));
                classification.setCodigoCategoria(res.getString(3));
                classification.setValorAtributo(res.getString(4));
                classification.setOrigenImportacion(res.getString(5));
                classification.setEstadoProcesamiento(res.getString(6));
                classification.setDescripcionError(res.getString(7));
                classification.setEmpresa(res.getString(8));

                list.add(classification);
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
            String query = "select count(*) from classification";
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
            query = "select count(*) from classification where origenImportacion like ? and estadoProcesamiento like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from classification where origenImportacion like ? and estadoProcesamiento like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from classification where origenImportacion like ? and estadoProcesamiento like 'Sin Procesar'";

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
            query = "select count(*) from classification where estadoProcesamiento like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from classification where estadoProcesamiento like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from classification where estadoProcesamiento like 'Sin Procesar'";

        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from classification where estadoProcesamiento like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from classification where empresa like 'E'";

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
        List<String> nombreArchivos = Utilities.getImportOriginList(TipoFeed.CLASSIFICATION);
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
            String query = "select count(*) from classification where origenImportacion like ?";
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
