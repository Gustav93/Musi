package utilidades;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Excel
{
    public static List<String> leerLinksExcel(File archivoExcel)
    {
        List<String> links = new ArrayList<>();

        InputStream excelStream;

        try
        {
            excelStream = new FileInputStream(archivoExcel);

            XSSFWorkbook excel = new XSSFWorkbook(excelStream); //representacion del archivo excel
            XSSFSheet hojaExcel = excel.getSheetAt(0); //selecciono la 1ra hoja del archivo

            XSSFRow fila;
            XSSFCell celda;

            int cantFilas = hojaExcel.getLastRowNum();
            String link;

            for(int i=0; i<=cantFilas; i++)
            {
                fila = hojaExcel.getRow(i);

                if(fila == null)
                    break;

                celda = hojaExcel.getRow(i).getCell(0);
                link = celda.toString();

                links.add(link);
            }
        }

        catch (FileNotFoundException e)
        {
            System.out.println("No se encontro el archivo");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return links;
    }

    public static void main(String[] args) {

        File file = new File("D:\\links.xlsx");
        Excel excel = new Excel();

        List<String> list =excel.leerLinksExcel(file);

        System.out.println(list);
    }
}
