package DataBase;

import Feed.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMedia
{
    private static final String CREATE_TABLE_MEDIA = "CREATE TABLE MEDIA (productCode VARCHAR(50), codeMedia VARCHAR(100), isDefault VARCHAR(10), importOrigin VARCHAR(100))";
    private static final String DELETE_TABLE_MEDIA = "DROP TABLE MEDIA";
    private static final String INSERT_MEDIA = "INSERT INTO MEDIA (productCode, codeMedia, isDefault, importOrigin) VALUES (?,?,?,?)";
    private static final String GET_MEDIA = "SELECT * FROM MEDIA WHERE productCode = ?";
    private static final String MEDIA_LIST = "SELECT * FROM MEDIA";

    public void createTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_MEDIA);
            ps.execute();
//            DBConectionManager.commit(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            DBConectionManager.closeConnection(c);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_MEDIA);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            DBConectionManager.closeConnection(c);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createMedia(Media media) {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(INSERT_MEDIA);
            ps.setString(1, media.getProductCode());
            ps.setString(2, media.getCodeMedia());
            ps.setString(3, media.getIsDefault());
            ps.setString(4, media.getImportOrigin());

            ps.executeUpdate();

            DBConectionManager.commit(c);
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {

            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public Media getMedia(String productCode) {

        Media media = new Media();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_MEDIA);
            ps.setString(1, productCode);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                media.setProductCode(productCode);
                media.setCodeMedia(res.getString(2));
                media.setIsDefault(res.getString(3));
                media.setImportOrigin(res.getString(4));
            }
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return media;
    }

    public List<Media> getMediaList()
    {

        ArrayList<Media> mediaList = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(MEDIA_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Media m = new Media();

                m.setProductCode(res.getString(1));
                m.setCodeMedia(res.getString(2));
                m.setIsDefault(res.getString(3));
                m.setImportOrigin(res.getString(4));

                mediaList.add(m);
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

