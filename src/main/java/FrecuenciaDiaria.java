import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class FrecuenciaDiaria implements EstrategiaFrecuencia{

    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin,
                                      ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual){
        if (ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        for (int i = 0; i < ocurrencias-1; i++) {
            fechaAux = fechaAux.plusDays(intervalo);
        }
        return fechaAux;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, ArrayList<DayOfWeek> dias,
                                         Frecuencia.FrecuenciaMensual frecuenciaMensual, LocalDate fechaFin){
        fechaProxima = fechaProxima.plusDays(intervalo);
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin,
                                               int intervalo, ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual){
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualquiera);
            return (diferenciaDeDias % intervalo == 0);
        }
        return false;
    }
}
