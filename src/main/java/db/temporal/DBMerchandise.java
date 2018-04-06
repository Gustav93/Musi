package db.temporal;

import utilidades.enums.Contador;
import db.conexiones.DBConectionManager;
import utilidades.enums.Filtro;
import utilidades.enums.TipoFeed;
import Feeds.Merchandise;
import utilidades.Reporte;
import utilidades.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMerchandise
{
    public void crearTabla()
    {
        String query = "create table merchandise (source VARCHAR(50), refType VARCHAR(50), target VARCHAR(50), relacion VARCHAR(50), qualifier VARCHAR(50), preselected VARCHAR(50), origenImportacion VARCHAR(100), estadoProcesamiento VARCHAR(100), descripcionError VARCHAR(600), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            System.out.println("La tabla MERCHANDISE ya existe");
        }

        Utilities.crearIndice(TipoFeed.MERCHANDISE);
        DBConectionManager.closeConnection(c);

    }

    public void eliminarTabla()
    {
        String query = "DROP TABLE merchandise";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            System.out.println("Hubo un problema al eliminar la tabla MERCHANDISE");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(Merchandise merchandise)
    {
        String query = "insert into merchandise (source, refType, target, relacion, qualifier, preselected ,origenImportacion, estadoProcesamiento, descripcionError, empresa) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, merchandise.getCodigoProducto());
            ps.setString(2, merchandise.getRefType());
            ps.setString(3, merchandise.getTarget());
            ps.setString(4, merchandise.getRelacion());
            ps.setString(5, merchandise.getQualifier());
            ps.setString(6, merchandise.getPreselected());
            ps.setString(7, merchandise.getOrigenImportacion());
            ps.setString(8, merchandise.getEstadoProcesamiento());
            ps.setString(9, merchandise.getDescripcionError());
            ps.setString(10, merchandise.getEmpresa());

            ps.executeUpdate();
            DBConectionManager.commit(c);
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            DBConectionManager.closeConnection(c);
        }
    }

//    public Merchandise getMerchandise(String source) {
//
//        Merchandise merchandise = new Merchandise();
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(GET_MERCHANDISE);
//            ps.setString(1, source);
//            ResultSet res = ps.executeQuery();
//
//            while (res.next()) {
//                merchandise.setSource(source);
//                merchandise.setRefType(res.getString(2));
//                merchandise.setTarget(res.getString(3));
//                merchandise.setRelacion(res.getString(4));
//                merchandise.setQualifier(res.getString(5));
//                merchandise.setPreselected(res.getString(6));
//                merchandise.setOrigenImportacion(res.getString(7));
//            }
//        } catch (Exception e) {
//            DBConectionManager.rollback(c);
//        } finally {
//            try {
//                DBConectionManager.closeConnection(c);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return merchandise;
//    }

//    public List<Merchandise> getMerchandiseList()
//    {
//
//        ArrayList<Merchandise> merchandises = new ArrayList<>();
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement statement = c.prepareStatement(MERCHANDISE_LIST);
//            ResultSet res = statement.executeQuery();
//
//            while(res.next())
//            {
//                Merchandise m = new Merchandise();
//
//                m.setSource(res.getString(1));
//                m.setRefType(res.getString(2));
//                m.setTarget(res.getString(3));
//                m.setRelacion(res.getString(4));
//                m.setQualifier(res.getString(5));
//                m.setPreselected(res.getString(6));
//                m.setOrigenImportacion(res.getString(7));
//
//                merchandises.add(m);
//            }
//        }
//
//        catch (Exception e)
//        {
//            DBConectionManager.rollback(c);
//        }
//        finally
//        {
//            DBConectionManager.closeConnection(c);
//        }
//
//        return merchandises;
//    }

    public void editar(Merchandise m)
    {
        //language=SQL
        String query = "UPDATE merchandise SET estadoProcesamiento = ?, descripcionError = ?, empresa = ? WHERE source = ? AND origenImportacion = ?";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, m.getEstadoProcesamiento());
            ps.setString(2, m.getDescripcionError());
            ps.setString(3, m.getEmpresa());
            ps.setString(4, m.getCodigoProducto());
            ps.setString(5, m.getOrigenImportacion());

            ps.executeUpdate();
            DBConectionManager.commit(c);
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            DBConectionManager.closeConnection(c);
        }
    }

    public List<Merchandise> filtrarPor(Filtro filtro)
    {
        List<Merchandise> lista = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if (filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "SELECT * FROM merchandise";

        else if (filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "SELECT * FROM merchandise WHERE estadoProcesamiento = 'Procesado'";

        else if (filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "SELECT * FROM merchandise WHERE estadoProcesamiento = 'Procesado con Error'";

        else if (filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "SELECT * FROM merchandise WHERE estadoProcesamiento = 'Sin Procesar'";

        else if (filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "SELECT * FROM merchandise WHERE estadoProcesamiento = 'Procesado con Error' OR estadoProcesamiento = 'Sin Procesar' OR estadoProcesamiento = 'Procesado con Warning'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while (res.next())
            {
                Merchandise m = new Merchandise();

                m.setCodigoProducto(res.getString(1));
                m.setRefType(res.getString(2));
                m.setTarget(res.getString(3));
                m.setRelacion(res.getString(4));
                m.setQualifier(res.getString(5));
                m.setPreselected(res.getString(6));
                m.setOrigenImportacion(res.getString(7));
                m.setEstadoProcesamiento(res.getString(8));
                m.setDescripcionError(res.getString(9));
                m.setEmpresa(res.getString(10));

                lista.add(m);
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

        return lista;
    }

    public int getCandidadTotalRegistros()
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from merchandise";
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
        return getCantRegistros(Contador.NO_PROCESADO_CORRECTAMENTE);
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
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado'";

        else if(contador.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado con Error' or estadoProcesamiento like 'Procesado con Warning'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Sin Procesar'";

        else if(contador.equals(Contador.PROCESADO_CON_WARNING))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado con Warning'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado con Error'";

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
            query = "select count(*) from merchandise where estadoProcesamiento like 'Procesado'";

        else if(contador.equals(Contador.NO_PROCESADO_CORRECTAMENTE))
            //language=SQL
            query = "select count(*) from merchandise where estadoProcesamiento like 'Procesado con Error' or estadoProcesamiento like 'Procesado con Warning'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from merchandise where estadoProcesamiento like 'Sin Procesar'";

        else if(contador.equals(Contador.PROCESADO_CON_WARNING))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado con Warning'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from merchandise where origenImportacion like ? and estadoProcesamiento like 'Procesado con Error'";


        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from merchandise where empresa like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from merchandise where empresa like 'E'";

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
        List<String> nombreArchivos = Utilities.getImportOriginList(TipoFeed.MERCHANDISE);
        for(String nombreArchivo : nombreArchivos)
        {
            Reporte reporte = new Reporte();

            reporte.setNombreArchivo(nombreArchivo);
            reporte.setTotalRegistros(getCantidadTotalRegistros(nombreArchivo));
            reporte.setNoProcesados(getCantRegistros(Contador.SIN_PROCESAR, nombreArchivo));
            reporte.setProcesadosConError(getCantRegistros(Contador.PROCESADO_CON_ERROR, nombreArchivo));
            reporte.setProcesadosCorrectamente(getCantRegistros(Contador.PROCESADO, nombreArchivo));
            reporte.setProcesadosConWarning(getCantRegistros(Contador.PROCESADO_CON_WARNING, nombreArchivo));

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
            String query = "select count(*) from merchandise where origenImportacion like ?";
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