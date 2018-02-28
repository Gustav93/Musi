package Feed;

public class Merchandise extends Feed
{
    private String refType, target, relacion, qualifier, preselected;

    public Merchandise()
    {
        this.refType = null;
        this.target = null;
        this.relacion = null;
        this.qualifier = null;
        this.preselected = null;
    }

    public String getSource() {
        return getCodigoProducto();
    }

    public void setSource(String source) {
        setCodigoProducto(source);
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

    @Override
    public String toString() {
        return "Merchandise{" +
                "source='" + getSource() + '\'' +
                ", refType='" + refType + '\'' +
                ", target='" + target + '\'' +
                ", relacion='" + relacion + '\'' +
                ", qualifier='" + qualifier + '\'' +
                ", preselected='" + preselected + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Merchandise m = new Merchandise();

        m.setEmpresa("musi");

        System.out.println(m.getEmpresa());
    }

}
