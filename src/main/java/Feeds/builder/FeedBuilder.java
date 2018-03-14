package Feeds.builder;

import Feeds.*;
import utilidades.Utilities;
import auditoria.RegistroAuditoria;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedBuilder
{
    //estos metodos reciben la lista de filas del archivo csv y dependiendo el metodo que sea, crea una lista de objetos
    //del fedd correspondiente. En todos los casos se correge el importOrigin ya que desde el csv llega el path absoluto
    //del archivo.
    public List<Product> crearListaProductos(List<List<String>> rows)
    {
        List<Product> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Product p = new Product();
            p.setCodigoProducto(row.get(0));
            p.setEan(row.get(1));
            p.setBrand(row.get(2));
            p.setName(row.get(3));
            p.setCategory(row.get(4));
            p.setWeight(row.get(5));
            p.setOnlineDateTime(row.get(6));
            p.setOfflineDateTime(row.get(7));
            p.setApprovalStatus(row.get(8));
            p.setDescription(row.get(9));
            p.setOrigenImportacion(Utilities.setOrigenImportacion(row.get(10)));

            corregirCodigoProducto(p);
            list.add(p);
        }
        return list;
    }

    public List<Price> crearListaPrecios(List<List<String>> rows)
    {
        List<Price> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Price price = new Price();

            price.setCodigoProducto(row.get(0));
            price.setOnlinePrice(row.get(1));
            price.setCurrency(row.get(2));
            price.setStorePrice(row.get(3));
            price.setHasPriority(row.get(4));
            price.setOrigenImportacion(Utilities.setOrigenImportacion(row.get(5)));

            list.add(price);
        }

        return list;
    }

    public List<Merchandise> crearListaMerchandise(List<List<String>> rows)
    {
        List<Merchandise> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Merchandise merch = new Merchandise();

            merch.setCodigoProducto(row.get(0));
            merch.setRefType(row.get(1));
            merch.setTarget(row.get(2));
            merch.setRelacion(row.get(3));
            merch.setQualifier(row.get(4));
            merch.setPreselected(row.get(5));
            merch.setOrigenImportacion(Utilities.setOrigenImportacion(row.get(6)));

            list.add(merch);
        }
        return list;
    }

    public List<Media> crearListaMedia(List<List<String>> rows)
    {
        List<Media> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Media media = new Media();

            media.setCodigoProducto(row.get(0));
            media.setCodeMedia(row.get(1));
            media.setIsDefault(row.get(2));
            media.setOrigenImportacion(Utilities.setOrigenImportacion(row.get(3)));

            list.add(media);
        }
        return list;
    }

    public List<Stock> crearListaStock(List<List<String>> rows)
    {
        List<Stock> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Stock stock = new Stock();

            stock.setCodigoProducto(row.get(0));
            stock.setStock(row.get(1));
            stock.setWarehouse(row.get(2));
            stock.setStatus(row.get(3));
            stock.setOrigenImportacion(Utilities.setOrigenImportacion(row.get(4)));

            list.add(stock);
        }

        return list;
    }

    public List<Classification> crearListaClasificacion(List<List<String>> filas)
    {
        List<Classification> lista = new ArrayList<>();

        for(List<String> fila : filas)
        {
            Classification classification = new Classification();

            classification.setCodigoProducto(fila.get(0));
            classification.setCodigoAtributo(fila.get(1));
            classification.setCodigoCategoria(fila.get(2));
            classification.setValorAtributo(fila.get(3));
            classification.setOrigenImportacion(Utilities.setOrigenImportacion(fila.get(4)));

            lista.add(classification);
        }
        return lista;
    }

    public List<RegistroAuditoria> crearListaRegistrosAuditoria(List<List<String>> rows)
    {
        List<RegistroAuditoria> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            RegistroAuditoria item = new RegistroAuditoria();
            String feedType = row.get(8);
            String errorCode = row.get(3);

            //los registros con error "E37" no tienen los campos correctamente, por ejemplo, no tienen el codigo de
            //producto o el tipo de feed
            if(Utilities.checkFeedType(feedType) || errorCode.equals("E37"))
            {
                item.setAuditLevel(row.get(0));
                item.setAuditType(row.get(1));
                item.setAuditDate(row.get(2));
                item.setErrorCode(errorCode);
                item.setDescription(row.get(4));
                item.setEmpresa(row.get(5));
                item.setProductCode(row.get(6));
                item.setImportOrigin(row.get(7).toLowerCase());
                item.setFeedType(feedType);

                //si el registro es un E37 se procede a corregirlo, y si no fue posible, se descarta
                if(errorCode.equals("E37"))
                {
                    corregirRegistroAuditoria(item);
                    if(item.getProductCode().equals(""))
                        continue;
                }

                //los registros que son de los feeds media y classification tienen errores en el codigo de producto
                if(feedType.equals("MEDIA") || feedType.equals("CLASSIFICATION"))
                    corregirCodigoProducto(item);

                list.add(item);
            }
        }

        return list;
    }

    private void corregirRegistroAuditoria(RegistroAuditoria item)
    {
        corregirEmpresa(item);
        corregirTipoFeed(item);
        corregirCodigoProducto(item);
    }

    //para corregir el feedType se utilizan expresiones regulares para buscar el tipo de feed en la descripcion del registro
    //ya que es unico el lugar de donde puedo sacar la informacion.
    private void corregirTipoFeed(RegistroAuditoria item)
    {
        String description = item.getDescription(); //descripcion del registro
        Pattern pattern = Pattern.compile("classification"); //patron con el que vamos a buscar en la descripcion
        Matcher matcher = pattern.matcher(description); //se compila el patron con la descripcion

        //si encuentra el patron (en este caso es "classification") en el matcher, setea el feed en el registro
        if(matcher.find())
        {
            item.setFeedType("CLASSIFICATION");
            return;
        }

        pattern = Pattern.compile("product");
        matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setFeedType("PRODUCT");
            return;
        }

        pattern = Pattern.compile("media");
        matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setFeedType("MEDIA");
            return;
        }

        pattern = Pattern.compile("price");
        matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setFeedType("PRICE");
            return;
        }

        pattern = Pattern.compile("stock");
        matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setFeedType("STOCK");
            return;
        }

    }

    //con el campo empresa es lo mismo que con el metodo anteriror
    private void corregirEmpresa(RegistroAuditoria item)
    {
        String description = item.getDescription();
        String emsaPattern = "emsa";
        String carsaPattern = "carsa";
        Pattern pattern = Pattern.compile(emsaPattern);
        Matcher matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setEmpresa("E");
            return;
        }

        pattern = Pattern.compile(carsaPattern);
        matcher = pattern.matcher(description);

        if(matcher.find())
        {
            item.setEmpresa("C");
            return;
        }
    }

    //en este caso el codigo de producto tiene diferentes errores dependiendo del feed
    private void corregirCodigoProducto(RegistroAuditoria item)
    {
        //si el registro es un "E37", el patron va a ser una barra invertida (\), comillas ("), 3 o mas digitos,
        //barra invertida (\) y comillas. Aca se presupone que los codigo de productos tienen 3 o mas digitos.
        //Por ejemplo con \"123456\" matchea
        if(item.getErrorCode().equals("E37"))
        {
            String description = item.getDescription();
            String productCodePattern = "\\\\\"\\d{3,}\\\\\"";
            Pattern pattern = Pattern.compile(productCodePattern);
            Matcher matcher = pattern.matcher(description);

            if(matcher.find())
            {
                String productCode = matcher.group();
                productCode = productCode.substring(2); //con esto me saco de encima (\")
                int index = 0;

                while(index < productCode.length()) //y con esto quito la parte final (\")
                {
                    if(productCode.charAt(index) == '\\')
                        break;

                    index++;
                }

                productCode = productCode.substring(0,index);
                item.setProductCode(productCode);
            }
        }

        //En el caso de que el registro sea del feed "media", el codigo de producto esta segido del nombre de un
        // archivo jpg. Por ejemplo: 126698-126698_01.jpg. Mientras si es classification, el codigo de producto esta
        // seguido por categorias de la siguiente manera: 127381/HELADERAS/NO FROST
        else if (item.getFeedType().equals("CLASSIFICATION") || item.getFeedType().equals("MEDIA"))
        {
            String productCode = item.getProductCode();

            for(int i = 0; i<productCode.length(); i++)
            {
                if(productCode.charAt(i) == '-' || productCode.charAt(i) == '/')
                {
                    productCode = productCode.substring(0,i);
                    break;
                }
            }
            item.setProductCode(productCode);
        }
    }

    private void corregirCodigoProducto(Product p)
    {
        if(p.getCodigoProducto().contains("\""))
        {
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(p.getCodigoProducto());

            if(matcher.find())
                p.setCodigoProducto(matcher.group());
        }
    }
}