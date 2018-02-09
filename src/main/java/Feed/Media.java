package Feed;

public class Media
{
    private String productCode, codeMedia, isDefault, importOrigin, processed, errorDescription, empresa;

    public Media()
    {
        this.productCode = null;
        this.codeMedia = null;
        this.isDefault = null;
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
        return "Media{" +
                "productCode='" + productCode + '\'' +
                ", codeMedia='" + codeMedia + '\'' +
                ", isDefault='" + isDefault + '\'' +
                ", importOrigin='" + importOrigin + '\'' +
                '}';
    }
}