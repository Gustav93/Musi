package Feeds;

public class Stock extends FeedPadre
{
    private String stock, warehouse, status;

    public Stock()
    {
        this.stock = null;
        this.warehouse = null;
        this.status = null;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    @Override
//    public String toString() {
//        return "Stock{" +
//                "productCode='" + codigoProducto + '\'' +
//                ", stock='" + stock + '\'' +
//                ", warehouse='" + warehouse + '\'' +
//                ", status='" + status + '\'' +
//                ", importOrigin='" + origenImportacion + '\'' +
//                ", processed='" + estadoProcesamiento + '\'' +
//                ", errorDescription='" + descripcionError + '\'' +
//                '}';
//    }
}