package Reporte;

public class Reporte
{
    private String nombreArchivo;
    private Integer totalRegistros, procesadosCorrectamente, noProcesados, procesadosConError;

    public Reporte()
    {
        nombreArchivo = null;
        totalRegistros = null;
        procesadosCorrectamente = null;
        noProcesados = null;
        procesadosConError = null;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public Integer getProcesadosCorrectamente() {
        return procesadosCorrectamente;
    }

    public void setProcesadosCorrectamente(Integer procesadosCorrectamente)
    {
        this.procesadosCorrectamente = procesadosCorrectamente;
    }

    public Integer getNoProcesados() {
        return noProcesados;
    }

    public void setNoProcesados(Integer noProcesados) {
        this.noProcesados = noProcesados;
    }

    public Integer getProcesadosConError() {
        return procesadosConError;
    }

    public void setProcesadosConError(Integer procesadosConError) {
        this.procesadosConError = procesadosConError;
    }

    @Override
    public String toString()
    {
        return "Nombre del Archivo: " + nombreArchivo + ", Cantidad Total de Registros: " + totalRegistros + ", Procesados Correctamente: " + procesadosCorrectamente + ", Procesados con Error: " + procesadosConError + ", No Procesados: " + noProcesados;
    }
}
