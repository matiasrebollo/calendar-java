import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public interface EstrategiaFrecuencia {

    LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin,
                               ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual);

    LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, ArrayList<DayOfWeek> dias,
                                  Frecuencia.FrecuenciaMensual frecuenciaMensual, LocalDate fechaFin);
    boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin,
                                        int intervalo, ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual);
}
