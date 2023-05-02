import java.time.LocalDate;

public class FrecuenciaCero implements TipoFrecuencia {
    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin) {
        return fechaFin;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, LocalDate fechaFin){
        return null;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin, int intervalo){
        return fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin);
    }
}
