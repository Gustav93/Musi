package csv;

import utilidades.Utilities;
import com.csvreader.CsvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reader
{
    private CsvReader reader;
    private String path;
    private List<List<String>> filas;

    public Reader(String path) throws FileNotFoundException
    {
        reader = new CsvReader(path);
        filas = new ArrayList<>();
        this.path = path;
    }

    //Devuelve una lista con todas las filas del csv procesado. En la ultima columna se almacena el path del archivo
    //para que luego sea utilizado para obtener el nombre del archivo.
    public List<List<String>> procesar() throws IOException
    {

        if (Utilities.isAudit(path))
            reader.setDelimiter(';');

        while(reader.readRecord())
        {
            List<String> celda = new ArrayList<>();

            if(Utilities.isProductFeed(path))
            {
                for(int i=0; i<10; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            else if (Utilities.isPriceFeed(path))
            {
                for(int i=0; i<5; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            else if (Utilities.isAudit(path))
            {
                for(int i=0; i<9; i++)
                {
                    //la auditoria de stock tiene un problema, cuando un registro se procesó correctamente, no almacena
                    // el nombre del archivo de donde fue importado, por esto cuando se exporta la auditoria desde
                    //hybris se debe poner el nombre del archivo del feed procesado en el nombre del archivo de la
                    //auditoria seguido de _aud. Con esto me aseguro de tener el nombre del feed procesado.
                    if(i == 7 && path.contains("_aud"))
                    {
                        if(path.contains("stock"))
                        {
                            celda.add(Utilities.setOrigenImportacion(path));
                            continue;
                        }

                        //el origen de importacion almacena el archivo con nombre "classification", debe ser cambiado
                        // porque los archivos que generan las empresas se llaman "clasificacion".
                        else if(reader.get(i).contains("classification"))
                        {
                            String origenImportacion = reader.get(i).replaceAll("classification", "clasificacion");
                            celda.add(origenImportacion);
                            continue;
                        }
                    }

                    celda.add(reader.get(i));
                }

                filas.add(celda);

            }

            else if (Utilities.isMerchandiseFeed(path))
            {
                for(int i=0; i<6; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            else if(Utilities.isMediaFeed(path))
            {
                for(int i=0; i<3; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            else if (Utilities.isStockFeed(path))
            {
                for(int i=0; i<4; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            //si se llego a hasta este punto es porque el feed de clasificacion pertenece a EMSA y este se puede
            // procesar como los demas feeds.
            else if (Utilities.isClassificationFeed(path))
            {
                for(int i=0; i<4; i++)
                    celda.add(reader.get(i));

                celda.add(path);
                filas.add(celda);
            }

            else
                throw new IllegalArgumentException("Archivo invalido");

        }

        reader.close();
        return filas;
    }
}