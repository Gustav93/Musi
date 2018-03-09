package Feeds;

import Feeds.FeedPadre;

public class Product extends FeedPadre
{
    private String ean, brand, name, category, description, onlineDateTime, offlineDateTime, approvalStatus, weight;

    public Product()
    {
        this.ean = null;
        this.brand = null;
        this.name = null;
        this.description = null;
        this.weight = null;
        this.onlineDateTime = null;
        this.offlineDateTime = null;
        this.approvalStatus = null;
        this.category = null;
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

    @Override
    public String toString() {
        return "Product{" +
                "productCode='" + super.getCodigoProducto() + '\'' +
                ", ean='" + ean + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", importOrigin='" + getOrigenImportacion() + '\'' +
                ", onlineDateTime='" + onlineDateTime + '\'' +
                ", offlineDateTime='" + offlineDateTime + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                ", weight=" + weight +
                '}';
    }
}

