package DataBase;

import Feed.Price;
import Reporte.Reporte;
import Utilities.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBPrice
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table price (productCode VARCHAR(50), onlinePrice VARCHAR(50), currency VARCHAR(50), storePrice VARCHAR(50), hasPriority VARCHAR(50), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
        }
        catch (SQLException e)
        {
            System.out.println("La tabla PRECIO ya existe");
        }

        DBConectionManager.closeConnection(c);
        Utilities.crearIndice(Feed.PRECIO);

    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table price";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla PRICE");
        }

        DBConectionManager.closeConnection(c);

    }

    public void crearRegistro(Price price)
    {
        //language=SQL
        String query = "insert into price (productCode, onlinePrice, currency, storePrice, hasPriority, importOrigin, processed, errorDescription, empresa) values (?,?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, price.getProductCode());
            ps.setString(2, price.getOnlinePrice());
            ps.setString(3, price.getCurrency());
            ps.setString(4, price.getStorePrice());
            ps.setString(5, price.getHasPriority());
            ps.setString(6, price.getImportOrigin());
            ps.setString(7, price.getProcessed());
            ps.setString(8, price.getErrorDescription());
            ps.setString(9, price.getEmpresa());

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

//    public Price getRegistros(String productCode) {
//
//        Price price = new Price();
//        Connection c = DBConectionManager.openConnection();
//
//        try {
//            PreparedStatement ps = c.prepareStatement(GET_PRICE);
//            ps.setString(1, productCode);
//            ResultSet res = ps.executeQuery();
//
//            while (res.next()) {
//                price.setProductCode(productCode);
//                price.setOnlinePrice(res.getString(2));
//                price.setCurrency(res.getString(3));
//                price.setStorePrice(res.getString(4));
//                price.setHasPriority(res.getString(5));
//                price.setImportOrigin(res.getString(6));
//                price.setProcessed(res.getString(7));
//                price.setErrorDescription(res.getString(8));
//                price.setEmpresa(res.getString(9));
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
//        return price;
//    }

//    public List<Price> getPriceList()
//    {
//        return filtrarPor(PRICE_LIST);
//    }

    public void editar(Price p)
    {
        //language=SQL
        String query = "update price set processed = ?, errorDescription = ?, empresa = ? WHERE productCode = ? and importOrigin = ?";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, p.getProcessed());
            ps.setString(2, p.getErrorDescription());
            ps.setString(3, p.getEmpresa());
            ps.setString(4, p.getProductCode());
            ps.setString(5, p.getImportOrigin());

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
    public List<Price> filtrarPor(Filtro filtro)
    {
        List<Price> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        String query = null;

        if(filtro.equals(Filtro.SIN_FILTRAR))
            //language=SQL
            query = "select * from price";

        else if(filtro.equals(Filtro.PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from price where processed = 'Procesado'";

        else if(filtro.equals(Filtro.PROCESADOS_CON_ERRORES))
            //language=SQL
            query = "select * from price where processed = 'Procesado con Error'";

        else if(filtro.equals(Filtro.SIN_PROCESAR))
            //language=SQL
            query = "select * from price where processed = 'Sin Procesar'";

        else if(filtro.equals(Filtro.NO_PROCESADOS_CORRECTAMENTE))
            //language=SQL
            query = "select * from price where processed = 'Procesado con Error' or processed = 'Sin Procesar'";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Price p = new Price();

                p.setProductCode(res.getString(1));
                p.setOnlinePrice(res.getString(2));
                p.setCurrency(res.getString(3));
                p.setStorePrice(res.getString(4));
                p.setHasPriority(res.getString(5));
                p.setImportOrigin(res.getString(6));
                p.setProcessed(res.getString(7));
                p.setErrorDescription(res.getString(8));
                p.setEmpresa(res.getString(9));

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

    public int getCandidadTotalRegistros()
    {
        Connection c = DBConectionManager.openConnection();
        int total = 0;

        try
        {
            //language=SQL
            String query = "select count(*) from price";
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
            query = "select count(*) from price where importOrigin like ? and processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from price where importOrigin like ? and processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from price where importOrigin like ? and processed like 'Sin Procesar'";

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
            query = "select count(*) from price where processed like 'Procesado'";

        else if(contador.equals(Contador.PROCESADO_CON_ERROR))
            //language=SQL
            query = "select count(*) from price where processed like 'Procesado con Error'";

        else if(contador.equals(Contador.SIN_PROCESAR))
            //language=SQL
            query = "select count(*) from price where processed like 'Sin Procesar'";

        else if(contador.equals(Contador.CARSA))
            //language=SQL
            query = "select count(*) from price where empresa like 'C'";

        else if(contador.equals(Contador.EMSA))
            //language=SQL
            query = "select count(*) from price where empresa like 'E'";

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
        List<String> nombreArchivos = Utilities.getImportOriginList(Feed.PRECIO);
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
            String query = "select count(*) from price where importOrigin like ?";
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