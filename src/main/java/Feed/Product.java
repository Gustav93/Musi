package Feed;

public class Product
{
    private String code, ean, brand, name, category, description, importOrigin,onlineDateTime, offlineDateTime, approvalStatus, weight, processed, errorDescription;

    public Product()
    {
        this.code = null;
        this.ean = null;
        this.brand = null;
        this.name = null;
        this.description = null;
        this.weight = null;
        this.onlineDateTime = null;
        this.offlineDateTime = null;
        this.approvalStatus = null;
        this.category = null;
        this.importOrigin = null;
        this.processed = "Sin Procesar";
        this.errorDescription = "";
    }

    public String getCode() {
        return code;
    }

    public String getEan() {
        return ean;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImportOrigin() {
        return importOrigin;
    }

    public String getOnlineDateTime() {
        return onlineDateTime;
    }

    public String getOfflineDateTime() {
        return offlineDateTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getWeight() {
        return weight;
    }

    public void setCode(String code) { this.code = code; }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImportOrigin(String importOrigin) {
        this.importOrigin = importOrigin;
    }

    public void setOnlineDateTime(String onlineDateTime) {
        this.onlineDateTime = onlineDateTime;
    }

    public void setOfflineDateTime(String offlineDateTime) {
        this.offlineDateTime = offlineDateTime;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
        return "Product{" +
                "code='" + code + '\'' +
                ", ean='" + ean + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", importOrigin='" + importOrigin + '\'' +
                ", onlineDateTime='" + onlineDateTime + '\'' +
                ", offlineDateTime='" + offlineDateTime + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", weight=" + weight +
                '}';
    }
}

