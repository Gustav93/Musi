package Feed;

public class Price
{
    private String productCode, currency, importOrigin, onlinePrice,storePrice, hasPriority, processed, errorDescription;

    public Price()
    {
        this.productCode = null;
        this.onlinePrice = null;
        this.currency = null;
        this.storePrice = null;
        this.hasPriority = null;
        this.importOrigin = null;
        this.processed = "Sin Procesar";
        this.errorDescription = "";
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOnlinePrice() {
        return onlinePrice;
    }

    public void setOnlinePrice(String onlinePrice) {
        this.onlinePrice = onlinePrice;
    }

    public String getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(String storePrice) {
        this.storePrice = storePrice;
    }

    public String getHasPriority() {
        return hasPriority;
    }

    public void setHasPriority(String hasPriority) {
        this.hasPriority = hasPriority;
    }

    public String getImportOrigin() {
        return importOrigin;
    }

    public void setImportOrigin(String importOrigin) {
        this.importOrigin = importOrigin;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Price{");
        sb.append("productCode='").append(productCode).append('\'');
        sb.append(", onlinePrice=").append(onlinePrice);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", storePrice=").append(storePrice);
        sb.append(", hasPriority=").append(hasPriority);
        sb.append(", importOrigin=").append(importOrigin);
        sb.append('}');
        return sb.toString();
    }
}
