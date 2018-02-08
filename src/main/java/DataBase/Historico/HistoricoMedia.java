package DataBase.Historico;

import DataBase.DBConectionManager;
import DataBase.Feed;
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
        String query = "create table historico_media (productCode VARCHAR(50), codeMedia VARCHAR(100), isDefault VARCHAR(10), importOrigin VARCHAR(100), processed VARCHAR(100), errorDescription VARCHAR(200), empresa VARCHAR(10))";
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
        Utilities.crearIndice(Feed.MEDIA);
    }

    public void eliminarTabla()
    {
        //language=SQL
        String query = "drop table historico_media";
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(query);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("Hubo un problema al eliminar la tabla");
        }

        DBConectionManager.closeConnection(c);

    }

    public void crearRegistro(Media media)
    {
        //language=SQL
        String query = "insert into historico_media (productCode, codeMedia, isDefault, importOrigin) value (?,?,?,?,?,?,?)";
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(query);
            ps.setString(1, media.getProductCode());
            ps.setString(2, media.getCodeMedia());
            ps.setString(3, media.getIsDefault());
            ps.setString(4, media.getImportOrigin());
            ps.setString(5, media.getProcessed());
            ps.setString(6, media.getErrorDescription());
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
                media.setProductCode(productCode);
                media.setCodeMedia(res.getString(2));
                media.setIsDefault(res.getString(3));
                media.setImportOrigin(res.getString(4));
                media.setProcessed(res.getString(5));
                media.setErrorDescription(res.getString(6));
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
}
