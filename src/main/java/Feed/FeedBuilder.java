package Feed;

import Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedBuilder
{
    private static String importOriginCarsa;
    private static String importOriginEmsa;

    public List<Product> createProductList(List<List<String>> rows)
    {
        List<Product> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Product p = new Product();
            p.setCode(row.get(0));
            p.setEan(row.get(1));
            p.setBrand(row.get(2));
            p.setName(row.get(3));
            p.setCategory(row.get(4));
            p.setWeight(row.get(5));
            p.setOnlineDateTime(row.get(6));
            p.setOfflineDateTime(row.get(7));
            p.setApprovalStatus(row.get(8));
            p.setDescription(row.get(9));
            p.setImportOrigin(Utilities.setImportOrigin(row.get(10)));

            list.add(p);
        }
        return list;
    }

    public List<Price> createPriceList(List<List<String>> rows)
    {
        List<Price> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Price price = new Price();

            price.setProductCode(row.get(0));
            price.setOnlinePrice(row.get(1));
            price.setCurrency(row.get(2));
            price.setStorePrice(row.get(3));
            price.setHasPriority(row.get(4));
            price.setImportOrigin(Utilities.setImportOrigin(row.get(5)));

            list.add(price);
        }

        return list;
    }

    public List<AuditItem> createAuditList(List<List<String>> rows)
    {
        List<AuditItem> list = new ArrayList<>();

//        buscarImportOrigin(rows);

        for(List<String> row : rows)
        {
//            System.out.println(row);
            AuditItem item = new AuditItem();
            String feedType = row.get(8);
//            System.out.println(feedType);
            String errorCode = row.get(3);
//            System.out.println(errorCode);


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

                if(errorCode.equals("E37"))
                {
                    fixAuditItem(item);
                    if(item.getProductCode().equals(""))
                        continue;
                }

                if(feedType.equals("MEDIA") || feedType.equals("CLASSIFICATION"))
                    fixProductCode(item);

//                if(item.getFeedType().equals("STOCK") && !item.getErrorCode().contains("E"))
//                {
//                    fixImportOrigin(item);
//                }

                list.add(item);
            }
        }

        return list;
    }

//    private void fixImportOrigin(AuditItem item)
//    {
//
//        if(importOriginCarsa != null && item.getEmpresa().equals("C"))
//        {
//            item.setImportOrigin(importOriginCarsa);
//            System.out.println("asdfv");
//        }
//
//        else if (importOriginEmsa != null && item.getEmpresa().equals("E"))
//        {
//            item.setImportOrigin(importOriginEmsa);
//            System.out.println("assss");
//        }
//
//    }

    public List<Merchandise> createMerchandiseList(List<List<String>> rows)
    {
        List<Merchandise> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Merchandise merch = new Merchandise();

            merch.setSource(row.get(0));
            merch.setRefType(row.get(1));
            merch.setTarget(row.get(2));
            merch.setRelacion(row.get(3));
            merch.setQualifier(row.get(4));
            merch.setPreselected(row.get(5));
            merch.setImportOrigin(Utilities.setImportOrigin(row.get(6)));

            list.add(merch);
        }
        return list;
    }

    public List<Media> createMediaList(List<List<String>> rows)
    {
        List<Media> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Media media = new Media();

            media.setProductCode(row.get(0));
            media.setCodeMedia(row.get(1));
            media.setIsDefault(row.get(2));
            media.setImportOrigin(Utilities.setImportOrigin(row.get(3)));

            list.add(media);
        }
        return list;
    }

    public List<Stock> createStockList(List<List<String>> rows)
    {
        List<Stock> list = new ArrayList<>();

        for(List<String> row : rows)
        {
            Stock stock = new Stock();

            stock.setProductCode(row.get(0));
            stock.setStock(row.get(1));
            stock.setWarehouse(row.get(2));
            stock.setStatus(row.get(3));
            stock.setImportOrigin(Utilities.setImportOrigin(row.get(4)));

            list.add(stock);
        }

        return list;
    }

    private void fixAuditItem(AuditItem item)
    {
        fixEmpresa(item);
        fixFeedType(item);
        fixProductCode(item);
    }

    private void fixFeedType(AuditItem item)
    {
        String description = item.getDescription();
        Pattern pattern = Pattern.compile("classification");
        Matcher matcher = pattern.matcher(description);

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

    private void fixEmpresa(AuditItem item)
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

    private void fixProductCode(AuditItem item)
    {
        if(item.getErrorCode().equals("E37"))
        {
            String description = item.getDescription();
            String productCodePattern = "\\\\\"\\d{3,}\\\\\""; //matchea por ejemplo con \"123456\"
            Pattern pattern = Pattern.compile(productCodePattern);
            Matcher matcher = pattern.matcher(description);

            if(matcher.find())
            {
                String productCode = matcher.group();
                productCode = productCode.substring(2);
                int index = 0;

                while(index < productCode.length())
                {
                    if(productCode.charAt(index) == '\\')
                        break;

                    index++;
                }

                productCode = productCode.substring(0,index);
                item.setProductCode(productCode);
            }
        }

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

    public List<Classification> createClassificationList(List<List<String>> rows)
    {
        List<Classification> list = new ArrayList<>();

        return list;
    }

//    public static void buscarImportOrigin(List<List<String>> filas)
//    {
//        importOriginEmsa = null;
//        importOriginCarsa = null;
//
//        for (List<String> fila : filas)
//        {
//            String errorCode = fila.get(3);
//            String feedType = fila.get(8);
//            String empresa = fila.get(5);
//            String importOrigin = fila.get(7).toLowerCase();
//
//            if(importOriginCarsa != null && importOriginEmsa != null)
//                break;
//
//            if(feedType.equals("STOCK"))
//            {
//                if(importOriginCarsa == null && errorCode.contains("E") && empresa.equals("C"))
//                    importOriginCarsa = importOrigin;
//
//                else if(importOriginEmsa == null && errorCode.contains("E") && empresa.equals("E"))
//                    importOriginEmsa = importOrigin;
//            }
//        }
//        System.out.println("carsa: " + importOriginCarsa);
//        System.out.println("emsa: " + importOriginEmsa);
//    }
}
