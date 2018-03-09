package db.temporal;

import utilidades.enums.Contador;
import db.conexiones.DBConectionManager;
import utilidades.enums.Filtro;
import utilidades.enums.TipoFeed;
import utilidades.Reporte;
import utilidades.Utilities;
import Feeds.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBProduct
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table product (code VARCHAR(50), ean VARCHAR(50), brand VARCHAR(50), name VARCHAR(100), category VARCHAR(100), weight VARCHAR(50) , onlineDateTime VARCHAR(50), offlineDateTime VARCHAR(50), approvalStatus VARCHAR(50), description VARCHAR(500), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(500), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla PRODUCT ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndice(TipoFeed.PRODUCTO);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table product";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla PRODUCT");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(Product p)
    {
        //language=SQL
        String query = "insert into product (code, ean, brand, name, category, weight, onlineDateTime, offlineDateTime, approvalStatus, Description, importOrigin, processed, errorDescription, empresa) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, p.getCodigoProducto());
            ps.setString(2, p.getEan());
            ps.setString(3, p.getBrand());
            ps.setString(4, p.getName());
            ps.setString(5, p.getCategory());
            ps.setString(6, p.getWeight());
            ps.setString(7, p.getOnlineDateTime());
            ps.setString(8, p.getOfflineDateTime());
            ps.setString(9, p.getApprovalStatus());
            ps.setString(10, p.getDescription());
            ps.setString(11, p.getOrigenImportacion());
            ps.setString(12, p.getEstadoProcesamiento());
            ps.setString(13, p.getDescripcionError());
            ps.setString(14, p.getEmpresa());
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

    public void editar(Product p)
    {
        //language=SQL
        String query = "update product set processed = ?, errorDescription = ?, empresa = ? WHERE code = ? and importOrigin = ?";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, p.getEstadoProcesamiento());
            ps.setString(2, p.getDescripcionError());
            ps.setString(3, p.getEmpresa());
            ps.setString(4, p.getCodigoProducto());
            ps.setString(5, p.getOrigenImportacion());

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

    public List<Product> filtrarPor(Filtro filtro)
    {
        List<Product> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "select * from product";

        else if(filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from product where processed = 'Procesado'";

        else if(filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "select * from product where processed = 'Procesado con Error'";

        else if(filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "select * from product where processed = 'Sin Procesar'";

        else if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from product where processed = 'Procesado con Error' or processed = 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Product p = new Product();

                p.setCodigoProducto(res.getString(1));
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

    public int getCantidadTotalRegistros()
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from product";
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

//    private List<String> getImportOriginList()
//    {
//        List<String> importOriginList = new ArrayList<>();
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement statement = c.prepareStatement(GET_IMPORT_ORIGIN);
//            ResultSet res = statement.executeQuery();
//
//            while(res.next())
//            {
//                importOriginList.add(res.getString(1));
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
//        return importOriginList;
//    }

    private int getCantRegistros(Contador contador, String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;
        String query = null;

        if(contador.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from product where importOrigin like ? and processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from product where importOrigin like ? and processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from product where importOrigin like ? and processed like 'Sin Procesar'";

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

    private int getCantRegistros(Contador contador)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;

        String query = null;

        if(contador.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from product where processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from product where processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from product where processed like 'Sin Procesar'";

        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from product where empresa like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from product where empresa like 'E'";

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

    public List<Reporte> getReportes()
    {
        List<Reporte> reportes = new ArrayList<>();
        List<String> nombreArchivos = Utilities.getImportOriginList(TipoFeed.PRODUCTO);
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

    private int getCantidadTotalRegistros(String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from product where importOrigin like ?";
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