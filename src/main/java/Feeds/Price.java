package Feeds;

public class Price extends FeedPadre
{
    private String currency, onlinePrice,storePrice, hasPriority;

    public Price()
    {
        this.onlinePrice = null;
        this.currency = null;
        this.storePrice = null;
        this.hasPriority = null;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Price{");
        sb.append("productCode='").append(super.getCodigoProducto()).append('\'');
        sb.append(", onlinePrice=").append(onlinePrice);
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", storePrice=").append(storePrice);
        sb.append(", hasPriority=").append(hasPriority);
        sb.append(", importOrigin=").append(super.getOrigenImportacion());
        sb.append('}');
        return sb.toString();
    }
}
