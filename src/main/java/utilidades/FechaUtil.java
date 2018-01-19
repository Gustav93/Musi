
package utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class FechaUtil {
    
    public static java.util.Date getFecha(java.sql.Date fechaSql) {
        return new java.util.Date(fechaSql.getTime());
    }
    
    public static java.sql.Date getFecha(java.util.Date fechaComun) {
        return new java.sql.Date(fechaComun.getTime());
    }
    
    public static java.util.Date crearFecha(int dia, int mes, int anio) {
        Calendar c = new GregorianCalendar();
        c.set(anio, mes-1, dia);
        return c.getTime();
    }
    
    public static java.util.Date crearFecha(String fecha) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date laFecha = sdf.parse(fecha);
        return laFecha;
    }
    
    public static String formatear(java.util.Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(fecha);
        return fechaFormateada;
    }
}
