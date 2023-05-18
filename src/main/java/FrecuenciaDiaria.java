import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class FrecuenciaDiaria extends Frecuencia {

    public FrecuenciaDiaria(LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        super(fechaInicio, intervalo, fechaFin);
    }
    public FrecuenciaDiaria(LocalDate fechaInicio, int intervalo, int ocurrencias) {
        super(fechaInicio, intervalo, ocurrencias);
        super.fechaFin = calcularFechaFin();
    }

    public LocalDate calcularFechaFin(){
        if (fechaFin != null && ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        for (int i = 0; i < ocurrencias-1; i++) {
            fechaAux = fechaAux.plusDays(intervalo);
        }
        return fechaAux;
    }

    public LocalDate obtenerFechaProxima(){
        fechaProxima = fechaProxima.plusDays(intervalo);
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }

    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera){
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualquiera);
            return (diferenciaDeDias % intervalo == 0);
        }
        return false;
    }

    @JsonCreator
    private FrecuenciaDiaria(@JsonProperty("fechaInicio") LocalDate fechaInicio,
                           @JsonProperty("intervalo")int intervalo,
                           @JsonProperty("ocurrencias") int ocurrencias,
                           @JsonProperty("fechaFin") LocalDate fechaFin,
                           @JsonProperty("fechaProxima") LocalDate fechaProxima){
        super(fechaInicio,intervalo,fechaFin);
        this.ocurrencias = ocurrencias;
        this.fechaProxima = fechaProxima;

    }
}
