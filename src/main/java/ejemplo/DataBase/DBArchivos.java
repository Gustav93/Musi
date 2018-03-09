package ejemplo.DataBase;

import db.conexiones.DBConectionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBArchivos
{
    private final String EDIT = "UPDATE AUDIT SET importOrigin = ? WHERE productCode = ? ";
    private final String CREATE_TABLE_ARCHIVOS = "CREATE TABLE ARCHIVOS (nombre VARCHAR(100), archivo LONGBLOB)";
    private final String DELETE_TABLE_ARCHIVOS = "DROP TABLE ARCHIVOS";
    private final String INSERT_ARCHIVO = "INSERT INTO ARCHIVOS (nombre, archivo) VALUES (?,?)";
    private final String GET_ARCHIVO = "SELECT * FROM ARCHIVOS WHERE nombre = ?";
    private final String LISTA_ARCHIVOS = "SELECT * FROM ARCHIVOS";
    private final String ADD_INDEX = "ALTER TABLE ARCHIVOS ADD INDEX indiceArchivos (nombre)";

    public void crearTabla()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(CREATE_TABLE_ARCHIVOS);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {

            System.out.println("La tabla ARCHIVOS ya existe");
        }

        DBConectionManager.closeConnection(c);

//        Utilities.createIndex(ADD_INDEX);
    }

    public void elimiarTabla()
    {
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(DELETE_TABLE_ARCHIVOS);
            ps.execute();
            DBConectionManager.commit(c);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }


        DBConectionManager.closeConnection(c);


    }

    public void setArchivo(File archivo)
    {
        Connection c = DBConectionManager.openConnection();

        File a = archivo;
        try
        {
            PreparedStatement ps = c.prepareStatement(INSERT_ARCHIVO);

            FileInputStream inputStream = new FileInputStream(a);

            //el nombre del archivo tiene al final el prefijo "(producto)" debido a que el nombre original ya esta siendo usado
            //cuando se carga el archivo en el servidor

//            String nombreArchivo = archivo.getName().replaceAll(" \\(procesado\\)","");

            ps.setString(1, archivo.getName());
            ps.setBlob(2, inputStream);
            ps.executeUpdate();

            inputStream.close();

            DBConectionManager.commit(c);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            DBConectionManager.rollback(c);
        }

        finally
        {
            DBConectionManager.closeConnection(c);
        }
    }

    public File getArchivo(String nombreArchivo) {

        File archivo = new File(nombreArchivo);
        Connection c = DBConectionManager.openConnection();

        try {
            PreparedStatement ps = c.prepareStatement(GET_ARCHIVO);
            ps.setString(1, nombreArchivo);

            ResultSet res = ps.executeQuery();

            Blob blob = res.getBlob(2);
            InputStream is = blob.getBinaryStream();
            FileOutputStream fos = new FileOutputStream(archivo);

            int dato = is.read();

            while(dato != -1)
            {
                fos.write(dato);
                dato = is.read();
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
        return archivo;
    }

    public List<String> getNombreArchivos()
    {
        List<String> listaNombres = new ArrayList<>();

        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(LISTA_ARCHIVOS);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                listaNombres.add(rs.getString(1));
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        DBConectionManager.closeConnection(c);

        return listaNombres;
    }

    public boolean existeArchivo(String nombreArchivo)
    {
        boolean res = false;
        Connection c = DBConectionManager.openConnection();

        try
        {
            PreparedStatement ps = c.prepareStatement(GET_ARCHIVO);
            ps.setString(1, nombreArchivo);
            ResultSet rs = ps.executeQuery();

            res = rs.next();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }


        DBConectionManager.closeConnection(c);

        return res;
    }
}
