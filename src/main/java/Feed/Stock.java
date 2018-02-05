package Feed;

public class Stock
{
    private String productCode, stock, warehouse, status, importOrigin, processed, errorDescription, empresa;

    public Stock()
    {
        this.productCode = null;
        this.stock = null;
        this.warehouse = null;
        this.status = null;
        this.importOrigin = null;
        this.processed = "Sin Procesar";
        this.errorDescription = "";
        this.empresa = null;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "productCode='" + productCode + '\'' +
                ", stock='" + stock + '\'' +
                ", warehouse='" + warehouse + '\'' +
                ", status='" + status + '\'' +
                ", importOrigin='" + importOrigin + '\'' +
                ", processed='" + processed + '\'' +
                ", errorDescription='" + errorDescription + '\'' +
                '}';
    }
}