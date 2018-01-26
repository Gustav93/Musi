package Feed;

public class AuditItem
{
    private String auditLevel, auditType, auditDate, errorCode, description, empresa, productCode, importOrigin, feedType;

    public AuditItem()
    {
        this.auditLevel = null;
        this.auditType = null;
        this.auditDate = null;
        this.errorCode = null;
        this.description = null;
        this.empresa = null;
        this.productCode = null;
        this.importOrigin = null;
        this.feedType = null;
    }

    public String getAuditLevel() {
        return auditLevel;
    }

    public void setAuditLevel(String auditLevel) {
        this.auditLevel = auditLevel;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String prodcuctCode) {
        this.productCode = prodcuctCode;
    }

    public String getImportOrigin() {
        return importOrigin;
    }

    public void setImportOrigin(String importOrigin) {
        this.importOrigin = importOrigin;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    @Override
    public String toString() {
        return "AuditItem{" +
                "auditLevel='" + auditLevel + '\'' +
                ", auditType='" + auditType + '\'' +
                ", auditDate='" + auditDate + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", description='" + description + '\'' +
                ", empresa='" + empresa + '\'' +
                ", productCode='" + productCode + '\'' +
                ", setImportOrigin='" + importOrigin + '\'' +
                ", feedType='" + feedType + '\'' +
                '}';
    }
}
