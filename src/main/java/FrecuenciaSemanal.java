import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import static java.time.temporal.ChronoUnit.DAYS;

public class FrecuenciaSemanal implements EstrategiaFrecuencia {

    private ArrayList<DayOfWeek> diasDeLaSemana = new ArrayList<>();

    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia) {
        if (diasDeLaSemana.contains(dia)){
            diasDeLaSemana.remove(dia);
        }
        else {
            diasDeLaSemana.add(dia);
        }
    }

    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin){
        if (ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        int contador = 0;
        while (contador < ocurrencias) {
            var diaActual = fechaAux.getDayOfWeek();
            if (diasDeLaSemana.contains(diaActual)) {
                contador++;
            }
            if (contador < ocurrencias) {
                fechaAux = fechaAux.plusDays(1);
                if (fechaAux.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {//si termina la semana
                    fechaAux = fechaAux.plusWeeks(intervalo-1);
                }
            }
        }
        return fechaAux;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo,
                                         LocalDate fechaFin){
        fechaProxima = fechaProxima.plusDays(1);
        var diaSemana = fechaProxima.getDayOfWeek();
        while (!diasDeLaSemana.contains(diaSemana)) {
            fechaProxima = fechaProxima.plusDays(1);
            if (fechaProxima.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                fechaProxima = fechaProxima.plusWeeks(intervalo - 1);
            }
            diaSemana = fechaProxima.getDayOfWeek();
        }
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin, int intervalo){
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        if (fechaFin.isAfter(fechaInicio)){

        }
        //else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
        else if (fechaInicio.isBefore(fechaCualquiera)) {
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualquiera);
            DayOfWeek diaCualquiera = fechaCualquiera.getDayOfWeek();
            return (diasDeLaSemana.contains(diaCualquiera) && ((diferenciaDeDias/7) % (intervalo)) == 0);
        }
        return false;
    }
}
