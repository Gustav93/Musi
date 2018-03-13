package Feeds;

public class Classification extends FeedPadre
{
    private String codigoAtributo, codigoCategoria, valorAtributo;

    public Classification()
    {
        codigoAtributo = null;
        codigoCategoria = null;
        valorAtributo = null;
    }

    public String getCodigoAtributo() {
        return codigoAtributo;
    }

    public void setCodigoAtributo(String codigoAtributo) {
        this.codigoAtributo = codigoAtributo;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getValorAtributo() {
        return valorAtributo;
    }

    public void setValorAtributo(String valorAtributo) {
        this.valorAtributo = valorAtributo;
    }
}
