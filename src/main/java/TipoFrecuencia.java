import java.time.LocalDate;

public interface TipoFrecuencia {

    LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin);

    LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, LocalDate fechaFin);

    boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin, int intervalo);
}
