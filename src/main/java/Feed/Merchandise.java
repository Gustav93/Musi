package Feed;

public class Merchandise
{
    private String source, refType, target, relacion, qualifier, preselected, importOrigin;

    public Merchandise()
    {
        this.source = null;
        this.refType = null;
        this.target = null;
        this.relacion = null;
        this.qualifier = null;
        this.preselected = null;
        this.importOrigin = null;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getRelacion() {
        return relacion;
    }

    public void setRelacion(String relacion) {
        this.relacion = relacion;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getPreselected() {
        return preselected;
    }

    public void setPreselected(String preselected) {
        this.preselected = preselected;
    }

    public String getImportOrigin() {
        return importOrigin;
    }

    public void setImportOrigin(String importOrigin) {
        this.importOrigin = importOrigin;
    }

    @Override
    public String toString() {
        return "Merchandise{" +
                "source='" + source + '\'' +
                ", refType='" + refType + '\'' +
                ", target='" + target + '\'' +
                ", relacion='" + relacion + '\'' +
                ", qualifier='" + qualifier + '\'' +
                ", preselected='" + preselected + '\'' +
                ", importOrigin='" + importOrigin + '\'' +
                '}';
    }
}
