package CSV;

import Utilities.Utilities;
import com.csvreader.CsvReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader
{
    private CsvReader reader;
    private String tipoDeArchivo, path;
    private List<List<String>> filas;

    public Reader(String path) throws FileNotFoundException
    {
        reader = new CsvReader(path);
        filas = new ArrayList<>();
        tipoDeArchivo = Utilities.getFeedType(path);
        this.path = path;
    }

    public List<List<String>> process() throws IOException
    {
        if (Utilities.isAudit(path))
            reader.setDelimiter(';');

        while(reader.readRecord())
        {
            List<String> items = new ArrayList<>();

            if(Utilities.isProductFeed(path))
            {
                for(int i=0; i<10; i++)
                    items.add(reader.get(i));
                items.add(path);

                filas.add(items);
            }

            else if (Utilities.isPriceFeed(path))
            {
                for(int i=0; i<5; i++)
                    items.add(reader.get(i));

                items.add(path);
                filas.add(items);
            }

            else if (Utilities.isAudit(path))
            {
                for(int i=0; i<9; i++)
                {
                    if(i == 7 && path.contains("_aud") && path.contains("stock"))
                    {
                        items.add(Utilities.setImportOrigin(path));
                        continue;
                    }

                    items.add(reader.get(i));
                }

//                System.out.println(items);
                filas.add(items);

            }

            else if (Utilities.isMerchandiseFeed(path))
            {
                for(int i=0; i<6; i++)
                    items.add(reader.get(i));

                items.add(path);
                filas.add(items);
            }

            else if(Utilities.isMediaFeed(path))
            {
                for(int i=0; i<3; i++)
                    items.add(reader.get(i));

                items.add(path);
                filas.add(items);
            }

//            else if(Utilities.isClassification(path))
//            {
//                for(int i=0; i<; i++)
//                    items.add(reader.get(i));
//
//                items.add(path);
//                filas.add(items);
//            }

            else if (Utilities.isStockFeed(path))
            {
                for(int i=0; i<4; i++)
                    items.add(reader.get(i));

                items.add(path);
                filas.add(items);
            }

            else
                throw new IllegalArgumentException("Archivo invalido");
        }

//        filas.remove(0);
        reader.close();

        return filas;
    }
}
