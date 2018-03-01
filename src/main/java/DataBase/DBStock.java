package DataBase;

import Feed.Stock;
import Reporte.Reporte;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBStock
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table stock (productCode VARCHAR(50), stock VARCHAR(100), warehouse VARCHAR(100), status VARCHAR(100), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla STOCK ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndice(Feed.STOCK);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table stock";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla STOCK");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(Stock stock)
    {
        //language=SQL
        String query = "insert into stock (productCode, stock, warehouse, status, importOrigin, processed, errorDescription, empresa) values (?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, stock.getCodigoProducto());
            ps.setString(2, stock.getStock());
            ps.setString(3, stock.getWarehouse());
            ps.setString(4, stock.getStatus());
            ps.setString(5, stock.getOrigenImportacion());
            ps.setString(6, stock.getEstadoProcesamiento());
            ps.setString(7,stock.getDescripcionError());
            ps.setString(8, stock.getEmpresa());

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

//    public Stock getRegistros(String productCode) {
//
//        Stock stock = new Stock();
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(GET_STOCK);
//            ps.setString(1, productCode);
//            ResultSet res = ps.executeQuery();
//
//            while (res.next()) {
//                stock.setProductCode(productCode);
//                stock.setStock(res.getString(2));
//                stock.setWarehouse(res.getString(3));
//                stock.setStatus(res.getString(4));
//                stock.setImportOrigin(res.getString(5));
//                stock.setProcessed(res.getString(6));
//                stock.setErrorDescription(res.getString(7));
//                stock.setEmpresa(res.getString(8));
//            }
//        }
//        catch (Exception e)
//        {
//            DBConectionManager.rollback(c);
//        }
//        finally
//        {
//            DBConectionManager.closeConnection(c);
//        }
//        return stock;
//    }

//    public List<Stock> getStockList()
//    {
//        return filtrarPor(STOCK_LIST);
//    }

    public void editar(Stock stock)
    {
//        Connection c = DBConectionManager.openConnection();
//
//        try
//        {
//            PreparedStatement ps = c.prepareStatement(EDIT);
//
//            ps.setString(1, stock.getProcessed());
//            ps.setString(2, stock.getErrorDescription());
//            ps.setString(3,stock.getEmpresa());
//            ps.setString(4, stock.getProductCode());
//
//            ps.executeUpdate();
//            DBConectionManager.commit(c);
//        }
//        catch (Exception e)
//        {
//            DBConectionManager.rollback(c);
//        }
//
//        finally
//        {
//            DBConectionManager.closeConnection(c);
//        }

        //language=SQL
        String query = "update stock set processed = ?, errorDescription = ?, empresa = ? WHERE productCode = ? and importOrigin = ?";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, stock.getEstadoProcesamiento());
            ps.setString(2, stock.getDescripcionError());
            ps.setString(3,stock.getEmpresa());
            ps.setString(4, stock.getCodigoProducto());
            ps.setString(5, stock.getOrigenImportacion());

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

    public List<Stock> filtrarPor(Filtro filtro)
    {
        List<Stock> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "select * from stock";

        else if(filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from stock where processed = 'Procesado'";

        else if(filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "select * from stock where processed = 'Procesado con Error'";

        else if(filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "select * from stock where processed = 'Sin Procesar'";

        else if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from stock where processed = 'Procesado con Error' or processed = 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Stock stock = new Stock();

                stock.setCodigoProducto(res.getString(1));
                stock.setStock(res.getString(2));
                stock.setWarehouse(res.getString(3));
                stock.setStatus(res.getString(4));
                stock.setOrigenImportacion(res.getString(5));
                stock.setEstadoProcesamiento(res.getString(6));
                stock.setDescripcionError(res.getString(7));
                stock.setEmpresa(res.getString(8));

                list.add(stock);
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
            String query = "select count(*) from stock";
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

    private int getCantRegistros(Contador contador, String nombreArchivo)
    {
        Connection c = DBConectionManager.openConnection();
        int procesados = 0;
        String query = null;

        if(contador.equals(Contador.PROCESADO))
            //language=SQL
            query = "select count(*) from stock where importOrigin like ? and processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from stock where importOrigin like ? and processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from stock where importOrigin like ? and processed like 'Sin Procesar'";

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
            query = "select count(*) from stock where processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from stock where processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from stock where processed like 'Sin Procesar'";

        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from stock where empresa like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from stock where empresa like 'E'";

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
        List<String> nombreArchivos = Utilities.getImportOriginList(Feed.STOCK);
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
            String query = "select count(*) from stock where importOrigin like ?";
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