import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class FrecuenciaDiaria implements TipoFrecuencia {

    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin){
        if (ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        for (int i = 0; i < ocurrencias-1; i++) {
            fechaAux = fechaAux.plusDays(intervalo);
        }
        return fechaAux;
    }

    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, LocalDate fechaFin){
        fechaProxima = fechaProxima.plusDays(intervalo);
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }

    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin, int intervalo){
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
