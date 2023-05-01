import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class FrecuenciaCero implements EstrategiaFrecuencia{
    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin,
                                      ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual) {
        return fechaFin;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, ArrayList<DayOfWeek> dias,
                                         Frecuencia.FrecuenciaMensual frecuenciaMensual, LocalDate fechaFin){
        return null;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin,
                                               int intervalo, ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual){
        return fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin);
    }
}
