package Utilities;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario
{
    private Calendar calendar;

    public Calendario()
    {
        calendar = new GregorianCalendar();
    }

    public String getYear()
    {
        return String.valueOf(calendar.get(calendar.YEAR));
    }

    public String getMes()
    {
        String mes = String.valueOf(calendar.get(calendar.MONTH)+1);

        if (mes.length() == 1)
            mes = 0 + mes;

        return mes;
    }

    public String getDia()
    {
        String dia = String.valueOf((calendar.get(calendar.DAY_OF_MONTH)));

        if(dia.length() == 1)
            dia = 0 + dia;

        return dia;
    }

    public String getHora()
    {
        String hora = String.valueOf(calendar.get(calendar.HOUR_OF_DAY));

        if(hora.length() == 1)
            hora = 0+ hora;

        return hora;
    }

    public String getMinutos()
    {
        String min = String.valueOf(calendar.get(calendar.MINUTE));

        if(min.length() == 1)
            min = 0 + min;

        return min;
    }

    public String getFechaYHora()
    {
        return getYear() + getMes() + getDia() + getHora() + getMinutos();
    }
}
