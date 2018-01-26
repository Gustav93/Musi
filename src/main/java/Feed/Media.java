package Feed;

public class Media
{
    private String productCode, codeMedia, isDefault, importOrigin;

    public Media()
    {
        this.productCode = null;
        this.codeMedia = null;
        this.isDefault = null;
        this.importOrigin = null;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCodeMedia() {
        return codeMedia;
    }

    public void setCodeMedia(String codeMedia) {
        this.codeMedia = codeMedia;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getImportOrigin() {
        return importOrigin;
    }

    public void setImportOrigin(String importOrigin) {
        this.importOrigin = importOrigin;
    }

    @Override
    public String toString() {
        return "Media{" +
                "productCode='" + productCode + '\'' +
                ", codeMedia='" + codeMedia + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", importOrigin='" + importOrigin + '\'' +
                '}';
    }
}

