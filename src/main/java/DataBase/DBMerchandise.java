package DataBase;

import Feed.Merchandise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMerchandise
{
    private static final String CREATE_TABLE_MERCHANDISE = "CREATE TABLE MERCHANDISE (source VARCHAR(50), refType VARCHAR(50), target VARCHAR(50), relacion VARCHAR(50), qualifier VARCHAR(50), preselected VARCHAR(50), importOrigin VARCHAR(100))";
    private static final String DELETE_TABLE_MERCHANDISE = "DROP TABLE MERCHANDISE";
    private static final String INSERT_MERCHANDISE = "INSERT INTO MERCHANDISE (source, refType, target, relacion, qualifier, preselected ,importOrigin) VALUES (?,?,?,?,?,?,?)";
    private static final String GET_MERCHANDISE = "SELECT * FROM MERCHANDISE WHERE source = ?";
    private static final String MERCHANDISE_LIST = "SELECT * FROM MERCHANDISE";

    public void createTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_MERCHANDISE);
            ps.execute();
            DBConectionManager.commit(c);
        }
        catch (SQLException e)
        {
            System.out.println("La tabla ya existe");
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);

    }

    public void deleteTable() {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_MERCHANDISE);
            ps.execute();
            DBConectionManager.commit(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DBConectionManager.closeConnection(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMerchandise(Merchandise merchandise) {
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(INSERT_MERCHANDISE);
            ps.setString(1, merchandise.getSource());
            ps.setString(2, merchandise.getRefType());
            ps.setString(3, merchandise.getTarget());
            ps.setString(4, merchandise.getRelacion());
            ps.setString(5, merchandise.getQualifier());
            ps.setString(6, merchandise.getPreselected());
            ps.setString(7, merchandise.getImportOrigin());


            ps.executeUpdate();

            DBConectionManager.commit(c);
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {

            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Merchandise getMerchandise(String source) {

        Merchandise merchandise = new Merchandise();
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_MERCHANDISE);
            ps.setString(1, source);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                merchandise.setSource(source);
                merchandise.setRefType(res.getString(2));
                merchandise.setTarget(res.getString(3));
                merchandise.setRelacion(res.getString(4));
                merchandise.setQualifier(res.getString(5));
                merchandise.setPreselected(res.getString(6));
                merchandise.setImportOrigin(res.getString(7));
            }
        } catch (Exception e) {
            DBConectionManager.rollback(c);
        } finally {
            try {
                DBConectionManager.closeConnection(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return merchandise;
    }

    public List<Merchandise> getMerchandiseList()
    {

        ArrayList<Merchandise> merchandises = new ArrayList<>();
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement statement = c.prepareStatement(MERCHANDISE_LIST);
            ResultSet res = statement.executeQuery();

            while(res.next())
            {
                Merchandise m = new Merchandise();

                m.setSource(res.getString(1));
                m.setRefType(res.getString(2));
                m.setTarget(res.getString(3));
                m.setRelacion(res.getString(4));
                m.setQualifier(res.getString(5));
                m.setPreselected(res.getString(6));
                m.setImportOrigin(res.getString(7));

                merchandises.add(m);
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

        return merchandises;
    }
}
