package Feed;

public class Feed
{
    private String codigoProducto, origenImportacion, estadoProcesamiento, descripcionError, empresa;

    public Feed()
    {
        codigoProducto = null;
        origenImportacion = null;
        estadoProcesamiento = "Sin Procesar";
        descripcionError = "";
        empresa = "";
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getOrigenImportacion() {
        return origenImportacion;
    }

    public void setOrigenImportacion(String origenImportacion) {
        this.origenImportacion = origenImportacion;
    }

    public String getEstadoProcesamiento() {
        return estadoProcesamiento;
    }

    public void setEstadoProcesamiento(String estadoProcesamiento) {
        this.estadoProcesamiento = estadoProcesamiento;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
}
