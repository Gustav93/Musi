package db.temporal;

import db.conexiones.DBConectionManager;
import utilidades.enums.ErrorType;
import utilidades.enums.TipoFeed;
import auditoria.RegistroAuditoria;
import utilidades.Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBAudit
{
    public void crearTabla()
    {
        //language=SQL
        String query = "create table audit (auditLevel VARCHAR(50), auditType VARCHAR(50), auditDate VARCHAR(50), errorCode VARCHAR(50), description VARCHAR(500), empresa VARCHAR(50), productCode VARCHAR(50), importOrigin VARCHAR(100), feedType VARCHAR(50))";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            System.out.println("La tabla AUDIT ya existe");
        }

        DBConectionManager.closeConnection(c);

        Utilities.crearIndice(TipoFeed.AUDITORIA);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table audit";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            System.out.println("No se pudo eliminar la tabla AUDIT");
        }

        DBConectionManager.closeConnection(c);
    }

    public void crearRegistro(RegistroAuditoria item)
    {
        String query = "insert into audit (auditLevel, auditType, auditDate, errorCode, description, empresa, productCode, importOrigin, feedType) values (?,?,?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, item.getAuditLevel());
            ps.setString(2, item.getAuditType());
            ps.setString(3, item.getAuditDate());
            ps.setString(4, item.getErrorCode());
            ps.setString(5, item.getDescription());
            ps.setString(6, item.getEmpresa());
            ps.setString(7, item.getProductCode());
            ps.setString(8, item.getImportOrigin());
            ps.setString(9, item.getFeedType());
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

    public List<RegistroAuditoria> getRegistro(String productCode, String importOrigin)
    {
        //language=SQL
        String query = "select * from audit where productCode = ? and importOrigin = ?";
        List<RegistroAuditoria> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ps.setString(2, importOrigin);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                RegistroAuditoria item = new RegistroAuditoria();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                list.add(item);
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

    public List<RegistroAuditoria> getRegistro(String productCode, String importOrigin, ErrorType errorType)
    {
        //language=SQL
        String query;

        if(errorType.equals(ErrorType.E))
            query = "select * from audit where productCode = ? and importOrigin = ? and errorCode like '%E%'";

        else if(errorType.equals(ErrorType.I))
            query = "select * from audit where productCode = ? and importOrigin = ? and errorCode like '%I%'";

        else
            query = "select * from audit where productCode = ? and importOrigin = ? and errorCode like '%W%'";

        List<RegistroAuditoria> list = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, productCode);
            ps.setString(2, importOrigin);
            ResultSet res = ps.executeQuery();

            while (res.next())
            {
                RegistroAuditoria item = new RegistroAuditoria();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                list.add(item);
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

    public List<RegistroAuditoria> getListaAuditoria()
    {
        ArrayList<RegistroAuditoria> auditItemList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "select * from audit";

        try
        {
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                RegistroAuditoria item = new RegistroAuditoria();

                item.setAuditLevel(res.getString(1));
                item.setAuditType(res.getString(2));
                item.setAuditDate(res.getString(3));
                item.setErrorCode(res.getString(4));
                item.setDescription(res.getString(5));
                item.setEmpresa(res.getString(6));
                item.setProductCode(res.getString(7));
                item.setImportOrigin(res.getString(8));
                item.setFeedType(res.getString(9));

                auditItemList.add(item);
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

        return auditItemList;
    }

    public void editar(RegistroAuditoria item)
    {
        Connection c = DBConectionManager.openConnection();
        //language=SQL
        String query = "update audit set importOrigin = ? where productCode = ? ";
        try
        {
            PreparedStatement ps = c.prepareStatement(query);

            ps.setString(1, item.getImportOrigin());
            ps.setString(2, item.getProductCode());

            ps.executeUpdate();
            DBConectionManager.commit(c);
        }
        catch (Exception e)
        {
            DBConectionManager.rollback(c);
            e.printStackTrace();
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }
    }
}
