package utilidades;

import Utilities.Mail;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TestPaginas
{
    private static WebDriver driver = null;

    public void chequear404()
    {

        System.setProperty("webdriver.chrome.driver","D:\\chromedriver.exe");
        driver = new ChromeDriver();
        List<String> linksRotos = new ArrayList<>();

//        String link = "https://www.musimundo.com/musimundo/es/c/?utm_source=Sengrid&utm_medium=email&utm_campaign=2018-03-02-ZC-VIERNES-SG-";
        List<String> links = Excel.leerLinksExcel(new File("D:\\links.xlsx"));

        System.out.println(links);
        for(String link : links)
        {
            driver.get(link);

            if(driver.getPageSource().contains("ERROR 404"))
                linksRotos.add(link);
        }

        System.out.println(linksRotos);
        driver.quit();

        Mail mail = new Mail();
        mail.enviarInformeLinksRotos(linksRotos);
    }

    public static void main(String[] args) {
        TestPaginas testPaginas = new TestPaginas();

        testPaginas.chequear404();
    }

}
