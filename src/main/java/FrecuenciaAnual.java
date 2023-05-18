import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.Month;

import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaAnual extends Frecuencia {

    public FrecuenciaAnual(LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        super(fechaInicio, intervalo, fechaFin);
    }
    public FrecuenciaAnual(LocalDate fechaInicio, int intervalo, int ocurrencias) {
        super(fechaInicio, intervalo, ocurrencias);
        super.fechaFin = calcularFechaFin();
    }

    public LocalDate calcularFechaFin(){
        if (fechaFin != null && ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        for (int i = 0; i < ocurrencias-1; i++) {
            fechaAux = fechaAux.plusYears(intervalo);
        }
        return fechaAux;
    }

    public LocalDate obtenerFechaProxima(){
        fechaProxima = fechaProxima.plusYears(intervalo);
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
            int diferenciaDeMeses = (int)MONTHS.between(fechaInicio, fechaCualquiera);
            int diaDelMes = fechaInicio.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualquiera.getDayOfMonth();

            if ((diferenciaDeMeses/12) % intervalo != 0) {
                return false;
            }
            Month mes = fechaInicio.getMonth();
            Month mesCualquiera = fechaCualquiera.getMonth();
            return (diaDelMes == diaDelMesCualquiera && mes.equals(mesCualquiera));
        }
        return false;
    }

    @JsonCreator
    private FrecuenciaAnual(@JsonProperty("fechaInicio") LocalDate fechaInicio,
                            @JsonProperty("intervalo")int intervalo,
                            @JsonProperty("ocurrencias") int ocurrencias,
                            @JsonProperty("fechaFin") LocalDate fechaFin,
                            @JsonProperty("fechaProxima") LocalDate fechaProxima){
        super(fechaInicio,intervalo,ocurrencias);
        this.fechaFin = fechaFin;
        this.fechaProxima = fechaProxima;
    }
}
