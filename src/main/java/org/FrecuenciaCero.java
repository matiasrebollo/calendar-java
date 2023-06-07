package org;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class FrecuenciaCero extends Frecuencia {
    //esta frecuencia representa una única vez

    public FrecuenciaCero(LocalDate fecha) {
        super(fecha, 1, fecha);
    }

    public LocalDate calcularFechaFin() {
        return fechaFin;
    }
    public LocalDate obtenerFechaProxima(){
        return null;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera){
        return fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin);
    }



    @JsonCreator
    private FrecuenciaCero(@JsonProperty("fechaInicio") LocalDate fechaInicio,
                           @JsonProperty("intervalo")int intervalo,
                           @JsonProperty("ocurrencias") int ocurrencias,
                           @JsonProperty("fechaFin") LocalDate fechaFin,
                           @JsonProperty("fechaProxima") LocalDate fechaProxima){
        super(fechaInicio,intervalo,fechaFin);
        this.ocurrencias = ocurrencias;
        this.fechaProxima = fechaProxima;

    }
}
