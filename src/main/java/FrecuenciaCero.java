import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class FrecuenciaCero implements EstrategiaFrecuencia{
    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin) {
        return fechaFin;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo,
                                         LocalDate fechaFin){
        return null;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin,
                                               int intervalo){
        return fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin);
    }
}
